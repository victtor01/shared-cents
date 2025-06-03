package com.joseph.finance.domain.models;

import com.joseph.finance.shared.exceptions.BadRequestException;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class IncomeTransaction extends FinanceTransaction {
    @Override
    public void setAmount(int amount) {
        if (amount < 0) throw new BadRequestException("amount is invalid!");
        super.setAmount(amount);
    }
}
