package com.joseph.finance.domain.models;

import java.util.List;

public class Workspace {
    private String id;
    private String name;
    private String icon;
    private List<FinanceEntry> financeEntry;
    private List<User> members;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<FinanceEntry> getFinanceEntry() {
        return financeEntry;
    }

    public void setFinanceEntry(List<FinanceEntry> financeEntry) {
        this.financeEntry = financeEntry;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
