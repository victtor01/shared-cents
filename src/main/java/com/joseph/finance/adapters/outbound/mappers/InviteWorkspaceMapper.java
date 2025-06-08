package com.joseph.finance.adapters.outbound.mappers;

import com.joseph.finance.adapters.outbound.entities.JpaInviteWorkspaceEntity;
import com.joseph.finance.domain.models.InviteWorkspace;

public class InviteWorkspaceMapper {
    public static InviteWorkspace toDomain(JpaInviteWorkspaceEntity jpaInviteWorkspace) {
        if(jpaInviteWorkspace == null) return null;

        InviteWorkspace invite = new InviteWorkspace();
        invite.setId(jpaInviteWorkspace.getId());
        invite.setSender(UserMapper.toDomain(jpaInviteWorkspace.getSender()));
        invite.setRecipient(UserMapper.toDomain(jpaInviteWorkspace.getRecipient()));
        invite.setWorkspace(WorkspaceMapper.toDomain(jpaInviteWorkspace.getWorkspace()));
        invite.setInviteWorkspaceStatus(jpaInviteWorkspace.getInviteWorkspaceStatus());
        return invite;
    }

    public static JpaInviteWorkspaceEntity toEntity(InviteWorkspace inviteWorkspace) {
        if(inviteWorkspace == null) return null;

        JpaInviteWorkspaceEntity invite = JpaInviteWorkspaceEntity.builder()
            .id(inviteWorkspace.getId())
            .sender(UserMapper.toEntity(inviteWorkspace.getSender()))
            .recipient(UserMapper.toEntity(inviteWorkspace.getRecipient()))
            .workspace(WorkspaceMapper.toEntity(inviteWorkspace.getWorkspace()))
            .inviteWorkspaceStatus(inviteWorkspace.getInviteWorkspaceStatus())
            .build();

        return invite;
    }
}
