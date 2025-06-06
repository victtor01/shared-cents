package com.joseph.finance.application.ports.in;

import com.joseph.finance.application.commands.CreateWorkspaceCommand;
import com.joseph.finance.domain.models.Workspace;

import java.util.List;
import java.util.UUID;

public interface WorkspacesServicePort {
    Workspace save(CreateWorkspaceCommand createWorkspaceCommand);
    List<Workspace> findAllByUserOrWhereIsMember(UUID userId);
    Workspace findByUserAndId(UUID userId, String workspaceId);
}
