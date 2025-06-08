package com.joseph.finance.application.ports.in;

import com.joseph.finance.application.commands.CreateInviteWorkspaceCommand;
import com.joseph.finance.domain.models.InviteWorkspace;

public interface InviteWorkspaceServicePort {
    InviteWorkspace save(CreateInviteWorkspaceCommand createInviteWorkspaceCommand);
}
