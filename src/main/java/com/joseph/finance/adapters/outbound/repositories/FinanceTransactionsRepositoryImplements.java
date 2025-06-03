package com.joseph.finance.adapters.outbound.repositories;

import com.joseph.finance.adapters.outbound.entities.JpaFinanceTransaction;
import com.joseph.finance.adapters.outbound.entities.JpaIncomeTransaction;
import com.joseph.finance.adapters.outbound.jpa.JpaFinanceTransactionsRepository;
import com.joseph.finance.adapters.outbound.mappers.FinanceTransactionMapper;
import com.joseph.finance.adapters.outbound.mappers.IncomeMapper;
import com.joseph.finance.application.ports.out.FinanceTransactionsRepositoryPort;
import com.joseph.finance.domain.models.FinanceTransaction;
import com.joseph.finance.domain.models.IncomeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<FinanceTransaction> findAllByWorkspace(String workspaceId) {
        List<JpaFinanceTransaction> jpaWorkspaceEntities = this.jpaFinanceTransactionsRepository.findAllByWorkspace(workspaceId);
        return jpaWorkspaceEntities.stream().map(FinanceTransactionMapper::toDomain).toList();
    }
}
