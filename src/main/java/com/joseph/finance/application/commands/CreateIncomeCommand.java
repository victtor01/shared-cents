package com.joseph.finance.application.commands;

import com.joseph.finance.domain.enums.PaymentMethod;
import com.joseph.finance.domain.models.User;

public record CreateIncomeCommand(
    String name,
    User user,
    String description,
    String workspaceId,
    PaymentMethod paymentMethod,
    int amount
) {
}
