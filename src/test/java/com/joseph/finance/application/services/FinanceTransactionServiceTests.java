package com.joseph.finance.application.services;

import com.joseph.finance.application.commands.CreateExpenseCommand;
import com.joseph.finance.application.ports.out.FinanceTransactionsRepositoryPort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.enums.ExpenseTransactionStatus;
import com.joseph.finance.domain.enums.PaymentMethod;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.domain.models.Workspace;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.utils.RandomIdGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FinanceTransactionServiceTests {
    @InjectMocks
    private FinanceTransactionServiceImplements financeTransactionServiceImplements;

    @Mock
    private WorkspacesRepositoryPort workspacesRepositoryPort;

    @Mock
    private FinanceTransactionsRepositoryPort financeTransactionsRepositoryPort;

    @Test
    public void itShouldErrorInCreateExpenseWhenAmountIsPositive() {
        User user = new User();
        String workspaceId = RandomIdGenerator.generateRandomId();
        int invalidAmount = 10;

        Workspace workspace = new Workspace();
        workspace.setId(workspaceId);

        CreateExpenseCommand createExpenseCommand = new CreateExpenseCommand(
            "example",
            user,
            "example_description",
            workspaceId,
            PaymentMethod.CREDIT_CARD,
            invalidAmount,
            ExpenseTransactionStatus.PAID
        );

        when(workspacesRepositoryPort.findById(workspaceId)).thenReturn(Optional.of(workspace));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            financeTransactionServiceImplements.createExpense(createExpenseCommand);
        });

        assertEquals("Amount deve ser negativo!", exception.getMessage());

        verify(financeTransactionsRepositoryPort, times(0)).saveExpense(any(ExpenseTransaction.class));
        verify(workspacesRepositoryPort, times(1)).findById(workspaceId);
    }

    @Test
    public void itShouldThrowErrorWhenWorkspaceNotFound() {
        User user = new User();
        String workspaceId =  RandomIdGenerator.generateRandomId();
        int validAmount = -10;

        CreateExpenseCommand createExpenseCommand = new CreateExpenseCommand(
            "example",
            user,
            "example_description",
            workspaceId,
            PaymentMethod.CREDIT_CARD,
            validAmount,
            ExpenseTransactionStatus.PAID
        );

        when(workspacesRepositoryPort.findById(workspaceId)).thenReturn(Optional.ofNullable(null));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            financeTransactionServiceImplements.createExpense(createExpenseCommand);
        });

        assertEquals("workspace not exists!", exception.getMessage());

        verify(workspacesRepositoryPort, times(1)).findById(workspaceId);
        verify(financeTransactionsRepositoryPort, times(0)).saveExpense(any(ExpenseTransaction.class));
    }
}
