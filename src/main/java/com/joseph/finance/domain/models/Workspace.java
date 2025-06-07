package com.joseph.finance.domain.models;

import com.joseph.finance.shared.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Workspace {
    private String id;
    private String name;
    private String icon;
    private List<FinanceTransaction> financeEntry = new ArrayList<>();
    private List<User> members;
    private User user;
    private int amount = 0;

    public void subtractAmount(int sub) {
        int amountToSubtract = Math.abs(sub);

        if (this.amount < amountToSubtract) {
            throw new BadRequestException("Saldo insuficiente");
        }

        this.amount -= amountToSubtract;

    }

    public void incrementAmount(int sub) {
        this.amount += sub;
    }
}
