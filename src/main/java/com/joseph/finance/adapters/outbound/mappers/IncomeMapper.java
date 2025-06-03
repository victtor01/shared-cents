package com.joseph.finance.adapters.outbound.mappers;

import com.joseph.finance.adapters.outbound.entities.JpaFinanceTransaction;
import com.joseph.finance.adapters.outbound.entities.JpaIncomeTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;

public class IncomeMapper {
    public static JpaIncomeTransaction toEntity(IncomeTransaction incomeTransaction) {
        if (incomeTransaction == null) return null;
        return JpaIncomeTransaction.builder()
            .id(incomeTransaction.getId())
            .amount(incomeTransaction.getAmount())
            .name(incomeTransaction.getName())
            .description(incomeTransaction.getDescription())
            .paymentMethod(incomeTransaction.getPaymentMethod())
            .user(UserMapper.toEntity(incomeTransaction.getUser()))
            .workspace(WorkspaceMapper.toEntity(incomeTransaction.getWorkspace()))
            .build();
    }

    public static IncomeTransaction toDomain(JpaIncomeTransaction incomeTransaction) {
        if (incomeTransaction == null) return null;
        return IncomeTransaction.builder()
            .id(incomeTransaction.getId())
            .name(incomeTransaction.getName())
            .amount(incomeTransaction.getAmount())
            .description(incomeTransaction.getDescription())
            .paymentMethod(incomeTransaction.getPaymentMethod())
            .user(UserMapper.toDomain(incomeTransaction.getUser()))
            .createdAt(incomeTransaction.getCreatedAt())
            .updatedAt(incomeTransaction.getUpdatedAt())
            .workspace(WorkspaceMapper.toDomain(incomeTransaction.getWorkspace()))
            .build();
    }

    public static IncomeTransaction toDomain(JpaFinanceTransaction incomeTransaction) {
        if (incomeTransaction == null) return null;
        return IncomeTransaction.builder()
            .id(incomeTransaction.getId())
            .name(incomeTransaction.getName())
            .amount(incomeTransaction.getAmount())
            .description(incomeTransaction.getDescription())
            .createdAt(incomeTransaction.getCreatedAt())
            .updatedAt(incomeTransaction.getUpdatedAt())
            .paymentMethod(incomeTransaction.getPaymentMethod())
            .user(UserMapper.toDomain(incomeTransaction.getUser()))
            .workspace(WorkspaceMapper.toDomain(incomeTransaction.getWorkspace()))
            .build();
    }
}
