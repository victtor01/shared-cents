package com.joseph.finance.application.ports.out;

import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FinanceTransactionsRepositoryPort {
    IncomeTransaction saveIncome(IncomeTransaction income);
    List<FinanceTransaction> findAllByWorkspace(String workspaceId, LocalDateTime startAt, LocalDateTime endAt);
    ExpenseTransaction saveExpense(ExpenseTransaction expenseTransaction);
}
