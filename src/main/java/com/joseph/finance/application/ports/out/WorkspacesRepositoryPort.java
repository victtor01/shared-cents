package com.joseph.finance.application.ports.out;

import com.joseph.finance.domain.models.Workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspacesRepositoryPort {
    Workspace save(Workspace workspace);
    List<Workspace> findByOwnerIdOrMemberId(UUID userId);
    Optional<Workspace> findById(String workspaceId);
}
