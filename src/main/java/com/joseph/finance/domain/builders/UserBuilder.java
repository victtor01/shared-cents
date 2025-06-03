package com.joseph.finance.domain.builders;


import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.domain.models.Workspace;

import java.util.List;
import java.util.UUID;

public class UserBuilder {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Workspace> workspaces = List.of();
    private List<FinanceTransaction> financeEntries = List.of();

    public UserBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder workspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
        return this;
    }

    public UserBuilder getFinanceEntries(List<FinanceTransaction> financialRecordResponsibles) {
        this.financeEntries = financialRecordResponsibles;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public List<FinanceTransaction> financeEntries() {
        return financeEntries;
    }

    public User build() {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalStateException("Username is required");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalStateException("Email is required");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalStateException("Password is required");
        }

        return new User(this);
    }
}