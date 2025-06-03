package com.joseph.finance.domain.models;

import com.joseph.finance.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class FinanceTransaction {
    private String id;
    private String name;
    private String description;
    private Workspace workspace;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
    private int amount;
}
