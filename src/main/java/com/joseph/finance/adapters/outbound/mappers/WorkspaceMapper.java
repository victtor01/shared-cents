package com.joseph.finance.adapters.outbound.mappers;

import com.joseph.finance.adapters.outbound.entities.JpaWorkspaceEntity;
import com.joseph.finance.domain.models.Workspace;

import java.util.List;
import java.util.Optional;

public class WorkspaceMapper {
    public static Workspace toDomain(JpaWorkspaceEntity jpaWorkspaceEntity) {
        if (jpaWorkspaceEntity == null) return null;

        Workspace workspace = new Workspace();
        workspace.setId(jpaWorkspaceEntity.getId());
        workspace.setName(jpaWorkspaceEntity.getName());
        workspace.setIcon(jpaWorkspaceEntity.getIcon());
        workspace.setUser(UserMapper.toDomain(jpaWorkspaceEntity.getOwner()));
        workspace.setFinanceEntry(List.of());
        workspace.setMembers(Optional.ofNullable(jpaWorkspaceEntity.getMembers())
            .stream()
            .flatMap(List::stream)
            .map(UserMapper::toDomain)
            .toList());

        return workspace;
    }

    public static JpaWorkspaceEntity toEntity(Workspace workspace) {
        if (workspace == null) return null;

        return JpaWorkspaceEntity.builder()
            .id(workspace.getId())
            .owner(UserMapper.toEntity(workspace.getUser()))
            .icon(workspace.getIcon())
            .name(workspace.getName())
            .members(Optional.ofNullable(workspace.getMembers())
                .stream()
                .flatMap(List::stream)
                .map(UserMapper::toEntity)
                .toList())
            .financeEntries(List.of())
            .build();
    }
}
