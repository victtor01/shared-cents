package com.joseph.finance.application.ports.in;

import com.joseph.finance.application.commands.CreateExpenseCommand;
import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.application.commands.FindTransactionsCommand;
import com.joseph.finance.application.records.DailyTransactionsDTO;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FinanceTransactionServicePort {
    IncomeTransaction createIncome(CreateIncomeCommand createIncomeCommand);
    ExpenseTransaction createExpense(CreateExpenseCommand createExpenseCommand);
    List<DailyTransactionsDTO> findAll(String workspaceId, UUID userId, FindTransactionsCommand findTransactionsCommand);
    List<FinanceTransaction> findAllByDay(String workspaceId, LocalDate day, UUID userId);
    FinanceTransaction findById(String transactionId, UUID userId);
    ExpenseTransaction payExpense(String transactionId, UUID userId);
}
