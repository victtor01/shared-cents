package com.joseph.finance.application.ports.in;

import com.joseph.finance.application.commands.CreateInviteWorkspaceCommand;
import com.joseph.finance.domain.models.InviteWorkspace;

import java.util.List;
import java.util.UUID;

public interface InviteWorkspaceServicePort {
    InviteWorkspace save(CreateInviteWorkspaceCommand createInviteWorkspaceCommand);
    List<InviteWorkspace> findAll(UUID userId);
    void accept(UUID inviteId, UUID receiverId);
}
