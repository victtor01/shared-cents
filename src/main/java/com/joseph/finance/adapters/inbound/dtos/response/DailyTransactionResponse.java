package com.joseph.finance.adapters.inbound.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class DailyTransactionResponse {
    private LocalDate date;

    private List<TransactionResponse> transactions;
}
