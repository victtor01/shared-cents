package com.joseph.finance.application.services;

import com.joseph.finance.application.commands.CreateExpenseCommand;
import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.application.ports.in.FinanceTransactionServicePort;
import com.joseph.finance.application.ports.out.FinanceTransactionsRepositoryPort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;
import com.joseph.finance.domain.models.Workspace;
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
    public IncomeTransaction createIncome(CreateIncomeCommand createIncomeCommand) {
        Workspace workspace = this.findWorkspaceOrThrow(createIncomeCommand.workspaceId());

        IncomeTransaction income = IncomeTransaction.builder()
            .id(RandomIdGenerator.generateRandomId())
            .user(createIncomeCommand.user())
            .name(createIncomeCommand.name())
            .description(createIncomeCommand.description())
            .amount(createIncomeCommand.amount())
            .paymentMethod(createIncomeCommand.paymentMethod())
            .workspace(workspace)
            .build();

        return this.financeTransactionsRepositoryPort.saveIncome(income);
    }

    @Override
    @Transactional
    public ExpenseTransaction createExpense(CreateExpenseCommand createExpenseCommand) {
        Workspace workspace = this.findWorkspaceOrThrow(createExpenseCommand.workspaceId());

        if (workspace.getAmount() < createExpenseCommand.amount()) {
            throw new BadRequestException("Saldo do workspace insuficiente!");
        }

        workspace.subtractAmount(createExpenseCommand.amount());

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

        this.workspacesRepositoryPort.save(workspace);

        return this.financeTransactionsRepositoryPort.saveExpense(expense);
    }

    @Override
    public List<FinanceTransaction> findAll(String workspaceId, UUID userId) {
        Workspace workspace = this.workspacesRepositoryPort.findById(workspaceId).orElseThrow(
            () -> new NotFoundException("Workspace not found!")
        );

        if (!workspace.getUser().getId().equals(userId)) {
            throw new BadRequestException("this workspace not belongs to you!");
        }

        return this.financeTransactionsRepositoryPort.findAllByWorkspace(workspaceId);
    }
}
