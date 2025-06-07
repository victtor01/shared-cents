package com.joseph.finance.adapters.inbound.controllers;

import com.joseph.finance.adapters.inbound.dtos.request.CreateExpenseRequest;
import com.joseph.finance.adapters.inbound.dtos.request.CreateIncomeRequest;
import com.joseph.finance.adapters.inbound.dtos.response.TransactionResponse;
import com.joseph.finance.adapters.inbound.mappers.TransactionMapper;
import com.joseph.finance.application.commands.CreateExpenseCommand;
import com.joseph.finance.application.commands.CreateIncomeCommand;
import com.joseph.finance.application.ports.in.FinanceTransactionServicePort;
import com.joseph.finance.application.ports.in.SessionServicePort;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{workspaceId}")
    public ResponseEntity<List<TransactionResponse>> findAll(@PathVariable String workspaceId) {
        List<FinanceTransaction> transactions = this.financeTransactionServicePort
            .findAll(workspaceId, sessionServicePort.getId());


        return ResponseEntity.status(HttpStatus.OK)
            .body(transactions.stream()
            .map(TransactionMapper::toResponse)
                .toList());
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
