package com.joseph.finance.adapters.outbound.repositories;

import com.joseph.finance.adapters.outbound.entities.JpaExpenseTransaction;
import com.joseph.finance.adapters.outbound.entities.JpaFinanceTransaction;
import com.joseph.finance.adapters.outbound.entities.JpaIncomeTransaction;
import com.joseph.finance.adapters.outbound.jpa.JpaFinanceTransactionsRepository;
import com.joseph.finance.adapters.outbound.mappers.ExpenseMapper;
import com.joseph.finance.adapters.outbound.mappers.FinanceTransactionMapper;
import com.joseph.finance.adapters.outbound.mappers.IncomeMapper;
import com.joseph.finance.application.ports.out.FinanceTransactionsRepositoryPort;
import com.joseph.finance.domain.models.ExpenseTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FinanceTransactionsRepositoryImplements implements FinanceTransactionsRepositoryPort {
    private final JpaFinanceTransactionsRepository jpaFinanceTransactionsRepository;

    @Autowired
    public FinanceTransactionsRepositoryImplements(JpaFinanceTransactionsRepository jpaFinanceTransactionsRepository) {
        this.jpaFinanceTransactionsRepository = jpaFinanceTransactionsRepository;
    }

    @Override
    public IncomeTransaction saveIncome(IncomeTransaction incomeTransaction) {
        JpaIncomeTransaction saved = this.jpaFinanceTransactionsRepository.save(IncomeMapper.toEntity(incomeTransaction));
        return IncomeMapper.toDomain(saved);
    }

    @Override
    public List<FinanceTransaction> findAllByWorkspace(String workspaceId, LocalDateTime startAt, LocalDateTime endAt) {
        List<JpaFinanceTransaction> jpaWorkspaceEntities = this.jpaFinanceTransactionsRepository.findAllByWorkspace(workspaceId, startAt, endAt);
        return jpaWorkspaceEntities.stream().map(FinanceTransactionMapper::toDomain).toList();
    }

    @Override
    public ExpenseTransaction saveExpense(ExpenseTransaction expenseTransaction) {
        JpaExpenseTransaction saved = this.jpaFinanceTransactionsRepository.save(ExpenseMapper.toEntity(expenseTransaction));
        return ExpenseMapper.toDomain(saved);
    }
}
