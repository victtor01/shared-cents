package com.joseph.finance.adapters.inbound.mappers;

import com.joseph.finance.adapters.inbound.dtos.response.TransactionResponse;
import com.joseph.finance.domain.models.FinanceTransaction;

public class TransactionMapper {
    public static TransactionResponse toResponse(FinanceTransaction financeTransaction) {
        return TransactionResponse.builder()
            .id(financeTransaction.getId())
            .name(financeTransaction.getName())
            .description(financeTransaction.getDescription())
            .updatedAt(financeTransaction.getUpdatedAt())
            .createdAt(financeTransaction.getCreatedAt())
            .build();
    }

}
