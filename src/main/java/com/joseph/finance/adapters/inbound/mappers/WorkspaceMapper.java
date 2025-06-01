package com.joseph.finance.adapters.inbound.mappers;

import com.joseph.finance.adapters.inbound.dtos.response.WorkspaceResponse;
import com.joseph.finance.domain.models.Workspace;

public class WorkspaceMapper {
    public static WorkspaceResponse toResponse(Workspace workspace) {
        return WorkspaceResponse.builder()
            .id(workspace.getId())
            .icon(workspace.getIcon())
            .name(workspace.getName())
            .members(workspace.getMembers().stream().map(UserMapper::toResponse).toList())
            .build();
    }
}
