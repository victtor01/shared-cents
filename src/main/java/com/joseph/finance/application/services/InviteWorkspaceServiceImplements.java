package com.joseph.finance.application.services;

import com.joseph.finance.application.commands.CreateInviteWorkspaceCommand;
import com.joseph.finance.application.ports.in.InviteWorkspaceServicePort;
import com.joseph.finance.application.ports.out.InviteWorkspaceRepositoryPort;
import com.joseph.finance.application.ports.out.UsersRepositoryPort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.enums.InviteWorkspaceStatus;
import com.joseph.finance.domain.models.InviteWorkspace;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.domain.models.Workspace;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InviteWorkspaceServiceImplements implements InviteWorkspaceServicePort {
    private final InviteWorkspaceRepositoryPort inviteWorkspaceRepositoryPort;
    private final UsersRepositoryPort usersRepositoryPort;
    private final WorkspacesRepositoryPort workspacesRepositoryPort;

    @Autowired
    public InviteWorkspaceServiceImplements(InviteWorkspaceRepositoryPort inviteWorkspaceRepositoryPort, UsersRepositoryPort usersRepositoryPort, WorkspacesRepositoryPort workspacesServicePort) {
        this.inviteWorkspaceRepositoryPort = inviteWorkspaceRepositoryPort;
        this.workspacesRepositoryPort = workspacesServicePort;
        this.usersRepositoryPort = usersRepositoryPort;
    }

    @Override
    @Transactional
    public InviteWorkspace save(CreateInviteWorkspaceCommand createInviteWorkspaceCommand) {
        Workspace workspace = this.workspacesRepositoryPort.findById(createInviteWorkspaceCommand.workspaceId())
            .orElseThrow(() -> new BadRequestException("workspace not found"));

        User sender = this.usersRepositoryPort.findById(createInviteWorkspaceCommand.senderId())
            .orElseThrow(() -> new NotFoundException("Sender not found!"));

        if (!sender.getId().equals(workspace.getUser().getId())) {
            throw new BadRequestException("Workspace not belongs to you!");
        }

        User receiver = this.usersRepositoryPort.findByUsername(createInviteWorkspaceCommand.recipientUsername())
            .orElseThrow(() -> new BadRequestException("recipient not found!"));

        this.inviteWorkspaceRepositoryPort.findBySenderAndReceiver(sender.getId(), receiver.getId())
            .ifPresent(existingInvite -> {
                if (existingInvite.getInviteWorkspaceStatus() == InviteWorkspaceStatus.PENDING) {
                    throw new BadRequestException("Um convite para este usuário já foi enviado.");
                }
            });

        InviteWorkspace inviteWorkspace = new InviteWorkspace();

        inviteWorkspace.setSender(sender);
        inviteWorkspace.setRecipient(receiver);
        inviteWorkspace.setInviteWorkspaceStatus(InviteWorkspaceStatus.PENDING);
        inviteWorkspace.setWorkspace(workspace);

        return this.inviteWorkspaceRepositoryPort.save(inviteWorkspace);
    }

    @Override
    public List<InviteWorkspace> findAll(UUID receiverId) {
        return this.inviteWorkspaceRepositoryPort.findAllByReceiver(receiverId);
    }

    @Override
    @Transactional
    public void accept(UUID inviteId, UUID receiverId) {
        InviteWorkspace invite = this.inviteWorkspaceRepositoryPort.findById(inviteId)
            .orElseThrow(() -> new BadRequestException("Invite not exists"));

        User receiver = invite.getRecipient();
        if(!receiver.getId().equals(receiverId)) {
            throw new BadRequestException("this invite not belongs to you!");
        }

        Workspace workspace = this.workspacesRepositoryPort
            .findById(invite.getWorkspace().getId())
            .orElseThrow(() -> new BadRequestException("workspace not found!"));

        workspace.getMembers().add(receiver);
        invite.setInviteWorkspaceStatus(InviteWorkspaceStatus.ACCEPTED);

        this.workspacesRepositoryPort.save(workspace);
        this.inviteWorkspaceRepositoryPort.save(invite);
    }
}
