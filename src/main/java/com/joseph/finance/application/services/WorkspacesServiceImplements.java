package com.joseph.finance.application.services;


import com.joseph.finance.application.commands.CreateWorkspaceCommand;
import com.joseph.finance.application.ports.in.WorkspacesServicePort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.domain.models.Workspace;
import com.joseph.finance.shared.utils.RandomIdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkspacesServiceImplements implements WorkspacesServicePort {

    private final WorkspacesRepositoryPort workspacesRepositoryPort;

    public WorkspacesServiceImplements(WorkspacesRepositoryPort workspacesRepositoryPort) {
        this.workspacesRepositoryPort = workspacesRepositoryPort;
    }

    @Override
    public Workspace save(CreateWorkspaceCommand createWorkspaceCommand) {
        Workspace workspace = new Workspace();
        workspace.setId(RandomIdGenerator.generateRandomId());
        workspace.setUser(createWorkspaceCommand.user());
        workspace.setIcon(createWorkspaceCommand.icon());
        workspace.setName(createWorkspaceCommand.name());
        workspace.setMembers(List.of(createWorkspaceCommand.user()));

        return this.workspacesRepositoryPort.save(workspace);
    }

    @Override
    public List<Workspace> findAllByUserOrWhereIsMember(UUID userId) {
        return this.workspacesRepositoryPort.findByOwnerIdOrMemberId(userId);
    }
}
