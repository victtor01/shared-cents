package com.joseph.finance.adapters.inbound.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.joseph.finance.adapters.inbound.dtos.response.DailyTransactionResponse;
import com.joseph.finance.adapters.inbound.dtos.response.TransactionResponse;
import com.joseph.finance.adapters.outbound.mappers.FinanceTransactionMapper;
import com.joseph.finance.application.commands.FindTransactionsCommand;
import com.joseph.finance.application.records.DailyTransactionsDTO;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.joseph.finance.adapters.inbound.dtos.request.CreateExpenseRequest;
import com.joseph.finance.adapters.inbound.dtos.request.CreateIncomeRequest;
import com.joseph.finance.adapters.inbound.mappers.TransactionMapper;
import com.joseph.finance.application.commands.CreateExpenseCommand;
import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.application.ports.in.FinanceTransactionServicePort;
import com.joseph.finance.application.ports.in.SessionServicePort;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class FinanceTransactionsController {

    private final FinanceTransactionServicePort financeTransactionServicePort;
    private final SessionServicePort sessionServicePort;

    @Autowired
    public FinanceTransactionsController(FinanceTransactionServicePort financeTransactionsRepositoryPort, SessionServicePort sessionServicePort) {
        this.financeTransactionServicePort = financeTransactionsRepositoryPort;
        this.sessionServicePort = sessionServicePort;
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<TransactionResponse> findById(@Valid @NotEmpty @PathVariable String transactionId) {
        UUID userId = this.sessionServicePort.getId();

        FinanceTransaction financeTransaction = this.financeTransactionServicePort.findById(transactionId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(TransactionMapper.toResponse(financeTransaction));
    }

    @GetMapping("/workspace/{workspaceId}/{day}")
    public ResponseEntity<List<TransactionResponse>> findByDay(@PathVariable String workspaceId, @PathVariable LocalDate day) {
        List<FinanceTransaction> transactions = this.financeTransactionServicePort.findAllByDay(workspaceId, day, this.sessionServicePort.getId());

        return ResponseEntity.status(HttpStatus.OK).body(transactions.stream().map(TransactionMapper::toResponse).toList());
    }

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<List<DailyTransactionResponse>> findAll(
        @PathVariable String workspaceId,
        @RequestParam(required = false) LocalDate startAt,
        @RequestParam(required = false) LocalDate endAt
    ) {
        List<DailyTransactionsDTO> transactions = this.financeTransactionServicePort
            .findAll(workspaceId, sessionServicePort.getId(), new FindTransactionsCommand(startAt, endAt));

        List<DailyTransactionResponse> responseMap = transactions.stream().map(dailyTransactionsDTO -> DailyTransactionResponse.builder()
            .date(dailyTransactionsDTO.date())
            .transactions(dailyTransactionsDTO.transactions().stream().map(TransactionMapper::toResponse).toList())
            .build()).toList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(responseMap);
    }

    @PostMapping("/income")
    public ResponseEntity<IncomeTransaction> saveIncome(@Valid @RequestBody CreateIncomeRequest createIncomeTransactionRequest) {
        IncomeTransaction income = this.financeTransactionServicePort.createIncome(
            new CreateIncomeCommand(
                createIncomeTransactionRequest.name(),
                sessionServicePort.getUser(),
                createIncomeTransactionRequest.description(),
                createIncomeTransactionRequest.workspaceId(),
                createIncomeTransactionRequest.paymentMethod(),
                createIncomeTransactionRequest.amount()
            )
        );

        return ResponseEntity.status(HttpStatus.OK).body(income);
    }

    @PostMapping("/expense")
    public ResponseEntity<ExpenseTransaction> saveExpense(@Valid @RequestBody CreateExpenseRequest createExpenseRequest) {
        ExpenseTransaction income = this.financeTransactionServicePort.createExpense(
            new CreateExpenseCommand(
                createExpenseRequest.name(),
                sessionServicePort.getUser(),
                createExpenseRequest.description(),
                createExpenseRequest.workspaceId(),
                createExpenseRequest.paymentMethod(),
                createExpenseRequest.amount(),
                createExpenseRequest.status()
            )
        );

        return ResponseEntity.status(HttpStatus.OK).body(income);
    }
}
