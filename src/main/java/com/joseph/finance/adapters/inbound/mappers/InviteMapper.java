package com.joseph.finance.adapters.inbound.mappers;

import com.joseph.finance.adapters.inbound.dtos.response.InviteWorkspaceResponse;
import com.joseph.finance.domain.models.InviteWorkspace;

public class InviteMapper {
    public static InviteWorkspaceResponse toResponse(InviteWorkspace inviteWorkspace) {
        if(inviteWorkspace == null) return null;

        return InviteWorkspaceResponse.builder()
            .id(inviteWorkspace.getId())
            .workspace(WorkspaceMapper.toResponse(inviteWorkspace.getWorkspace()))
            .recipient(UserMapper.toResponse(inviteWorkspace.getRecipient()))
            .sender(UserMapper.toResponse(inviteWorkspace.getSender()))
            .status(inviteWorkspace.getInviteWorkspaceStatus())
            .build();
    }
}
