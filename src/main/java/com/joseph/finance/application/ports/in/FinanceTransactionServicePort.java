package com.joseph.finance.application.ports.in;

import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;

import java.util.List;
import java.util.UUID;

public interface FinanceTransactionServicePort {
    IncomeTransaction createIncome(CreateIncomeCommand createIncomeCommand);
    List<FinanceTransaction> findAll(String workspaceId, UUID userId);
}
