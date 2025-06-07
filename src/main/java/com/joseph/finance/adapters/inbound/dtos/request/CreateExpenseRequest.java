package com.joseph.finance.adapters.inbound.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import com.joseph.finance.domain.enums.PaymentMethod;
import jakarta.validation.constraints.*;

public record CreateExpenseRequest(
    @NotNull
    String name,

    @NotNull
    @JsonProperty("payment_method")
    PaymentMethod paymentMethod,

    @Negative
    @NotNull
    Integer amount,

    @NotNull
    @NotBlank
    String workspaceId,

    @NotNull
    ExpenseTransactionStatus status,

    String description

) {

}
