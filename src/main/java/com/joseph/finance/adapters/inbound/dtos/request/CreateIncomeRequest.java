package com.joseph.finance.adapters.inbound.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joseph.finance.domain.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIncomeRequest(
    @NotNull
    String name,
    @NotNull
    @JsonProperty("payment_method")
    PaymentMethod paymentMethod,
    @Min(1)
    @NotNull
    Integer amount,
    @NotNull
    @NotBlank
    String workspaceId,
    String description
) {
}
