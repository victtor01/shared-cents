package com.joseph.finance.adapters.outbound.jpa;

import com.joseph.finance.adapters.outbound.entities.JpaWorkspaceEntity;
import com.joseph.finance.domain.models.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaWorkspacesRepository extends JpaRepository<JpaWorkspaceEntity, String> {
    @Query("SELECT DISTINCT w FROM workspaces w " + "LEFT JOIN w.members m " + "WHERE w.owner.id = :userId OR m.id = :userId")
    List<JpaWorkspaceEntity> findByOwnerIdOrMemberId(@Param("userId") UUID userId);

    JpaWorkspaceEntity save(Workspace workspace);
}