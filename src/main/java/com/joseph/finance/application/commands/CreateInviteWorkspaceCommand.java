package com.joseph.finance.application.commands;

import java.util.UUID;

public record CreateInviteWorkspaceCommand(
    UUID senderId,
    String recipientUsername,
    String workspaceId
) {
}
