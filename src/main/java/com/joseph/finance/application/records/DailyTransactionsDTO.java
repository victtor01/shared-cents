package com.joseph.finance.application.records;

import com.joseph.finance.domain.models.FinanceTransaction;

import java.time.LocalDate;
import java.util.List;

public record DailyTransactionsDTO(LocalDate date, List<FinanceTransaction> transactions) {
}
