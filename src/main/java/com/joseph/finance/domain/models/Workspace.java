package com.joseph.finance.domain.models;

import com.joseph.finance.shared.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workspace {
    private String id;
    private String name;
    private String icon;
    private List<FinanceTransaction> financeEntry = new ArrayList<>();
    private List<User> members;
    private User user;
    private int amount = 0;

    public Workspace(String id, String name, String icon, List<FinanceTransaction> financeEntry, List<User> members, User user, int amount) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.financeEntry = financeEntry;
        this.members = members;
        this.user = user;
        this.amount = amount;
    }

    public Workspace() {

    }

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

    public List<FinanceTransaction> getFinanceEntry() {
        return financeEntry;
    }

    public void setFinanceEntry(List<FinanceTransaction> financeEntry) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void incrementAmount(int sub) {
        this.amount += sub;
    }

    public void subtractAmount(int sub) {
        int amountToSubtract = Math.abs(sub);

        if (this.amount < amountToSubtract) {
            throw new BadRequestException("Saldo insuficiente");
        }

        this.amount -= amountToSubtract;
    }

    public boolean isOwnerOrMember(UUID userId) {
        User owner = this.getUser();
        boolean isOwner = owner.getId().equals(userId);

        boolean isMember = this.members.stream()
            .anyMatch(member -> member.getId().equals(userId));

        return isMember || isOwner;
    }

    public void isOwnerOrMemberOrThrow(UUID userId) {
        boolean isValidMember = this.isOwnerOrMember(userId);

        if(!isValidMember) {
            throw new BadRequestException("Membro não válido!");
        }
    }

}
