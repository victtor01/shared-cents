package com.joseph.finance.domain.models;

import com.joseph.finance.domain.enums.PaymentMethod;

import java.time.LocalDateTime;

public class FinanceEntry {
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
    private Workspace workspace;
    private PaymentMethod paymentMethod;
    private int amount;
    private int userId;
    private int workspaceId;
}
