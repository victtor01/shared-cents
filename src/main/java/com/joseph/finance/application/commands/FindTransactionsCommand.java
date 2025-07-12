package com.joseph.finance.application.commands;

import java.time.LocalDate;

public record FindTransactionsCommand(
    LocalDate startAt,
    LocalDate endAt
) {
}
