package com.joseph.finance.application.services;

import com.joseph.finance.application.commands.CreateExpenseCommand;
import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.application.ports.in.FinanceTransactionServicePort;
import com.joseph.finance.application.ports.out.FinanceTransactionsRepositoryPort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import com.joseph.finance.domain.models.*;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.exceptions.NotFoundException;
import com.joseph.finance.shared.utils.RandomIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public List<FinanceTransaction> findAll(String workspaceId, UUID userId) {
        Workspace workspace = this.workspacesRepositoryPort.findById(workspaceId).orElseThrow(
            () -> new NotFoundException("Workspace not found!")
        );

        User owner = workspace.getUser();
        List<User> members = workspace.getMembers();

        boolean isOwner = owner.getId().equals(userId);

        boolean isMember = members.stream()
            .anyMatch(member -> member.getId().equals(userId));

        if (!isOwner && !isMember) {
            throw new BadRequestException("Você não é o dono nem membro deste workspace!");
        }

        return this.financeTransactionsRepositoryPort.findAllByWorkspace(workspaceId);
    }
}
