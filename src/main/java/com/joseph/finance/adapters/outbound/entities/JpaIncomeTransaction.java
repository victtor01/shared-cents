package com.joseph.finance.adapters.outbound.entities;

import com.joseph.finance.shared.exceptions.BadRequestException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("INCOME")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class JpaIncomeTransaction extends JpaFinanceTransaction {

    @Override
    public void setAmount(int amount) {
        if (amount < 0) throw new BadRequestException("amount is invalid!");
        super.setAmount(amount);
    }
}
