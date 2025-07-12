package com.joseph.finance.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainInstallmentPlan {
    private UUID id;
    private Integer totalAmount;
    private Integer paidAmount;
    private String description;
    private List<PaymentDueSchedule> paymentDueSchedules;
    private LocalDate startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}