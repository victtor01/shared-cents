package com.joseph.finance.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDueSchedule {
    private UUID id;
    private Integer amount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
}
