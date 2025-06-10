package com.joseph.finance.application.services;


import com.joseph.finance.application.commands.CreateWorkspaceCommand;
import com.joseph.finance.application.ports.in.WorkspacesServicePort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.domain.models.Workspace;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.exceptions.NotFoundException;
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

    @Override
    public Workspace findByUserAndId(UUID userId, String workspaceId) {
        Workspace workspace = this.workspacesRepositoryPort.findById(workspaceId).orElseThrow(
            () -> new NotFoundException("workspace not found!")
        );

        User owner = workspace.getUser();
        List<User> members = workspace.getMembers();

        boolean isOwner = owner.getId().equals(userId);

        boolean isMember = members.stream()
            .anyMatch(member -> member.getId().equals(userId));

        if (!isOwner && !isMember) {
            throw new BadRequestException("Você não é o dono nem membro deste workspace!");
        }



        return workspace;
    }
}
