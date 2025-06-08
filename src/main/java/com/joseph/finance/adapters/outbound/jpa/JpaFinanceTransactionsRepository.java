package com.joseph.finance.adapters.outbound.jpa;

import com.joseph.finance.adapters.outbound.entities.JpaFinanceTransaction;
import com.joseph.finance.domain.models.FinanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFinanceTransactionsRepository extends JpaRepository<JpaFinanceTransaction, String> {
    @Query("SELECT ft FROM JpaFinanceTransaction ft WHERE ft.workspace.id = :workspaceId")
    List<JpaFinanceTransaction> findAllByWorkspace(@Param("workspaceId") String workspaceId);

    JpaFinanceTransaction save(FinanceTransaction finance);
}
