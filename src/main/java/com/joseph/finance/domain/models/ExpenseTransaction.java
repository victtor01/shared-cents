package com.joseph.finance.domain.models;

import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ExpenseTransaction extends FinanceTransaction {
    private ExpenseTransactionStatus status;

    public ExpenseTransactionStatus getStatus() { return status; }

    public void setStatus(ExpenseTransactionStatus status) {
        this.status = status;
    }
}
