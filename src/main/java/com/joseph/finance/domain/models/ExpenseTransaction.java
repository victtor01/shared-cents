package com.joseph.finance.domain.models;

import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import com.joseph.finance.shared.exceptions.BadRequestException;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ExpenseTransaction extends FinanceTransaction {
    private ExpenseTransactionStatus status;

    public ExpenseTransactionStatus getStatus() { return status; }

    public void setStatus(ExpenseTransactionStatus status) {
        this.status = status;
    }

    @Override
    public void setAmount(int amount) {
        if(amount >= 0) throw new BadRequestException("Deve ser negativo!");
        super.setAmount(amount);
    }
}
