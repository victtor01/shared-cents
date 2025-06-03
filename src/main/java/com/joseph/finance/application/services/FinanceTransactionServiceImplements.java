package com.joseph.finance.application.services;

import com.joseph.finance.adapters.outbound.repositories.FinanceTransactionsRepositoryImplements;
import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.application.ports.in.FinanceTransactionServicePort;
import com.joseph.finance.application.ports.out.FinanceTransactionsRepositoryPort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;
import com.joseph.finance.domain.models.Workspace;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.exceptions.NotFoundException;
import com.joseph.finance.shared.utils.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FinanceTransactionServiceImplements implements FinanceTransactionServicePort {

    private final FinanceTransactionsRepositoryPort financeTransactionsRepositoryPort;
    private final WorkspacesRepositoryPort workspacesRepositoryPort;

    @Autowired
    public FinanceTransactionServiceImplements(FinanceTransactionsRepositoryPort financeTransactionsRepositoryPort, WorkspacesRepositoryPort workspacesRepositoryPort) {
        this.financeTransactionsRepositoryPort = financeTransactionsRepositoryPort;
        this.workspacesRepositoryPort = workspacesRepositoryPort;
    }

    @Override
    public IncomeTransaction createIncome(CreateIncomeCommand createIncomeCommand) {
        Workspace workspace = this.workspacesRepositoryPort.findById(createIncomeCommand.workspaceId()).orElseThrow(
            () -> new BadRequestException("workspace not exists!")
        );

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
