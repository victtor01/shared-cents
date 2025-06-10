package com.joseph.finance.application.ports.out;

import com.joseph.finance.application.commands.CreateInviteWorkspaceCommand;
import com.joseph.finance.domain.models.InviteWorkspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InviteWorkspaceRepositoryPort {
    InviteWorkspace save(InviteWorkspace workspace);
    Optional<InviteWorkspace> findBySenderAndReceiver(UUID sender, UUID receiver);
    List<InviteWorkspace> findAllByReceiver(UUID receiver);
    Optional<InviteWorkspace> findById(UUID inviteId);
}
