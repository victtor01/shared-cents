package com.joseph.finance.application.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.joseph.finance.application.commands.FindTransactionsCommand;
import com.joseph.finance.application.records.DailyTransactionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joseph.finance.application.commands.CreateExpenseCommand;
import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.application.ports.in.FinanceTransactionServicePort;
import com.joseph.finance.application.ports.out.FinanceTransactionsRepositoryPort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.domain.models.Workspace;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.exceptions.NotFoundException;
import com.joseph.finance.shared.utils.RandomIdGenerator;

import jakarta.transaction.Transactional;

@Service
public class FinanceTransactionServiceImplements implements FinanceTransactionServicePort {

    private final FinanceTransactionsRepositoryPort financeTransactionsRepositoryPort;
    private final WorkspacesRepositoryPort workspacesRepositoryPort;

    private Workspace findWorkspaceOrThrow(String workspaceId) {
        return this.workspacesRepositoryPort.findById(workspaceId).orElseThrow(
            () -> new BadRequestException("workspace not exists!")
        );
    }

    @Autowired
    public FinanceTransactionServiceImplements(FinanceTransactionsRepositoryPort financeTransactionsRepositoryPort, WorkspacesRepositoryPort workspacesRepositoryPort) {
        this.financeTransactionsRepositoryPort = financeTransactionsRepositoryPort;
        this.workspacesRepositoryPort = workspacesRepositoryPort;
    }

    @Override
    @Transactional
    public IncomeTransaction createIncome(CreateIncomeCommand createIncomeCommand) {
        Workspace workspace = this.findWorkspaceOrThrow(createIncomeCommand.workspaceId());
        workspace.incrementAmount(createIncomeCommand.amount());

        IncomeTransaction income = IncomeTransaction.builder()
            .id(RandomIdGenerator.generateRandomId())
            .user(createIncomeCommand.user())
            .name(createIncomeCommand.name())
            .description(createIncomeCommand.description())
            .amount(createIncomeCommand.amount())
            .paymentMethod(createIncomeCommand.paymentMethod())
            .workspace(workspace)
            .build();

        var saved = this.financeTransactionsRepositoryPort.saveIncome(income);

        this.workspacesRepositoryPort.save(workspace);

        return saved;
    }

    @Override
    @Transactional
    public ExpenseTransaction createExpense(CreateExpenseCommand createExpenseCommand) {
        Workspace workspace = this.findWorkspaceOrThrow(createExpenseCommand.workspaceId());

        if (createExpenseCommand.amount() >= 0) {
            throw new BadRequestException("Amount deve ser negativo!");
        }

        if (createExpenseCommand.expenseTransactionStatus() == ExpenseTransactionStatus.PAID) {
            if (workspace.getAmount() < Math.abs(createExpenseCommand.amount())) {
                throw new BadRequestException("Saldo do workspace insuficiente!");
            }

            workspace.subtractAmount(createExpenseCommand.amount());
            this.workspacesRepositoryPort.save(workspace);
        }

        ExpenseTransaction expense = ExpenseTransaction.builder()
            .id(RandomIdGenerator.generateRandomId())
            .user(createExpenseCommand.user())
            .name(createExpenseCommand.name())
            .description(createExpenseCommand.description())
            .amount(createExpenseCommand.amount())
            .paymentMethod(createExpenseCommand.paymentMethod())
            .workspace(workspace)
            .status(createExpenseCommand.expenseTransactionStatus())
            .build();

        return this.financeTransactionsRepositoryPort.saveExpense(expense);
    }

    @Override
    public List<DailyTransactionsDTO> findAll(String workspaceId, UUID userId, FindTransactionsCommand findTransactionsCommand) {
        Workspace workspace = this.workspacesRepositoryPort.findById(workspaceId).orElseThrow(
            () -> new NotFoundException("Workspace not found!")
        );

        // ... (lógica de validação de usuário continua a mesma) ...
        User owner = workspace.getUser();
        boolean isOwner = owner.getId().equals(userId);
        boolean isMember = workspace.getMembers().stream()
            .anyMatch(member -> member.getId().equals(userId));

        if (!isOwner && !isMember) {
            throw new BadRequestException("Você não é o dono nem membro deste workspace!");
        }

        LocalDate startDate;
        LocalDate endDate;

        if (findTransactionsCommand.startAt() != null && findTransactionsCommand.endAt() != null) {
            startDate = findTransactionsCommand.startAt();
            endDate = findTransactionsCommand.endAt();
        } else {
            LocalDate today = LocalDate.now();
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);
            LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
            startDate = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            endDate = lastDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Map<LocalDate, List<FinanceTransaction>> transactionsMap =
            Stream.iterate(startDate, date -> !date.isAfter(endDate), date -> date.plusDays(1))
                .collect(Collectors.toMap(
                    date -> date,
                    date -> new ArrayList<>(),
                    (v1, v2) -> v1,
                    LinkedHashMap::new
                ));

        List<FinanceTransaction> foundTransactions = this.financeTransactionsRepositoryPort
            .findAllByWorkspace(workspaceId, startDateTime, endDateTime);

        foundTransactions.forEach(transaction -> {
            LocalDate transactionDate = transaction.getCreatedAt().toLocalDate();
            if (transactionsMap.containsKey(transactionDate)) {
                transactionsMap.get(transactionDate).add(transaction);
            }
        });

        return transactionsMap.entrySet().stream()
            .map(entry -> new DailyTransactionsDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

}
