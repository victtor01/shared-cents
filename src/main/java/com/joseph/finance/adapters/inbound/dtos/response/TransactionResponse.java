package com.joseph.finance.adapters.inbound.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import com.joseph.finance.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private String id;
    private String name;
    private String description;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer amount;
    private ExpenseTransactionStatus status;
    private UserResponse user;
}
