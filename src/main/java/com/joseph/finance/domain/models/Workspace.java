package com.joseph.finance.domain.models;

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
}
