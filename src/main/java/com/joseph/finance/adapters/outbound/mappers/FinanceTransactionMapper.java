package com.joseph.finance.adapters.outbound.mappers;

import com.joseph.finance.adapters.outbound.entities.JpaExpenseTransaction;
import com.joseph.finance.adapters.outbound.entities.JpaFinanceTransaction;
import com.joseph.finance.adapters.outbound.entities.JpaIncomeTransaction;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;

public class FinanceTransactionMapper {
    public static FinanceTransaction toDomain(JpaFinanceTransaction jpaFinanceTransaction) {
        if (jpaFinanceTransaction instanceof JpaIncomeTransaction) {
            return IncomeMapper.toDomain(jpaFinanceTransaction);
        }

        if (jpaFinanceTransaction instanceof JpaExpenseTransaction) {
            return ExpenseMapper.toDomain(jpaFinanceTransaction);
        }

        return null;
    }

    public static JpaFinanceTransaction toEntity(FinanceTransaction jpaFinanceTransaction) {
        if (jpaFinanceTransaction instanceof IncomeTransaction) {
            return IncomeMapper.toEntity((IncomeTransaction) jpaFinanceTransaction);
        }

        if (jpaFinanceTransaction instanceof ExpenseTransaction) {
            return ExpenseMapper.toEntity((ExpenseTransaction) jpaFinanceTransaction);
        }

        return null;
    }
}
