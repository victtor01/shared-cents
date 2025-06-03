package com.joseph.finance.adapters.outbound.mappers;

import com.joseph.finance.adapters.outbound.entities.JpaExpenseTransaction;
import com.joseph.finance.adapters.outbound.entities.JpaFinanceTransaction;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;

public class ExpenseMapper {
    public JpaExpenseTransaction toEntity(ExpenseTransaction expenseTransaction) {
        return JpaExpenseTransaction.builder()
            .id(expenseTransaction.getId())
            .amount(expenseTransaction.getAmount())
            .description(expenseTransaction.getDescription())
            .paymentMethod(expenseTransaction.getPaymentMethod())
            .user(UserMapper.toEntity(expenseTransaction.getUser()))
            .workspace(WorkspaceMapper.toEntity(expenseTransaction.getWorkspace()))
            .status(expenseTransaction.getStatus())
            .build();
    }

    public ExpenseTransaction toDomain(JpaExpenseTransaction expenseTransaction) {
        return ExpenseTransaction.builder()
            .id(expenseTransaction.getId())
            .amount(expenseTransaction.getAmount())
            .description(expenseTransaction.getDescription())
            .paymentMethod(expenseTransaction.getPaymentMethod())
            .status(expenseTransaction.getStatus())
            .user(UserMapper.toDomain(expenseTransaction.getUser()))
            .workspace(WorkspaceMapper.toDomain(expenseTransaction.getWorkspace()))
            .build();
    }

    public static IncomeTransaction toDomain(JpaFinanceTransaction expenseTransaction) {
        if (expenseTransaction == null) return null;
        return IncomeTransaction.builder()
            .id(expenseTransaction.getId())
            .name(expenseTransaction.getName())
            .amount(expenseTransaction.getAmount())
            .description(expenseTransaction.getDescription())
            .paymentMethod(expenseTransaction.getPaymentMethod())
            .user(UserMapper.toDomain(expenseTransaction.getUser()))
            .workspace(WorkspaceMapper.toDomain(expenseTransaction.getWorkspace()))
            .build();
    }
}
