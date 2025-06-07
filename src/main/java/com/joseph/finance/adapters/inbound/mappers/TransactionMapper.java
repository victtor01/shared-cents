package com.joseph.finance.adapters.inbound.mappers;

import com.joseph.finance.adapters.inbound.dtos.response.TransactionResponse;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;

public class TransactionMapper {
    public static TransactionResponse toResponse(FinanceTransaction financeTransaction) {
        if (financeTransaction instanceof ExpenseTransaction) {
            return TransactionResponse.builder()
                .id(financeTransaction.getId())
                .name(financeTransaction.getName())
                .description(financeTransaction.getDescription())
                .updatedAt(financeTransaction.getUpdatedAt())
                .amount(financeTransaction.getAmount())
                .status(((ExpenseTransaction) financeTransaction).getStatus())
                .createdAt(financeTransaction.getCreatedAt())
                .user(UserMapper.toResponse(financeTransaction.getUser()))
                .build();
        }

        return TransactionResponse.builder()
            .id(financeTransaction.getId())
            .name(financeTransaction.getName())
            .description(financeTransaction.getDescription())
            .updatedAt(financeTransaction.getUpdatedAt())
            .amount(financeTransaction.getAmount())
            .createdAt(financeTransaction.getCreatedAt())
            .user(UserMapper.toResponse(financeTransaction.getUser()))
            .build();
    }

}
