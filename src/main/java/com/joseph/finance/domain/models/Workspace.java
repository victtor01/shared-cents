package com.joseph.finance.domain.models;

import com.joseph.finance.shared.exceptions.BadRequestException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Workspace {
    private String id;
    private String name;
    private String icon;
    private List<FinanceTransaction> financeEntry;
    private List<User> members;
    private User user;
    private int amount = 0;

    public void subtractAmount(int sub) {
        if(this.amount < sub) throw new BadRequestException("Saldo insuficiente");
        this.amount -= sub;
    }

}
