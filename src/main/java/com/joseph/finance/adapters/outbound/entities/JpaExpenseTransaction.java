package com.joseph.finance.adapters.outbound.entities;

import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import com.joseph.finance.shared.exceptions.BadRequestException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("EXPENSE")
@SuperBuilder
@Getter
@Setter
public class JpaExpenseTransaction extends JpaFinanceTransaction {
    @Enumerated(EnumType.STRING)
    private ExpenseTransactionStatus status;

    @Override
    public void setAmount(int amount) {
        if (amount > 0) throw new BadRequestException("Expense amount must be negative");
        super.setAmount(amount);
    }
}