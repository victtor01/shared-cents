package com.joseph.finance.adapters.outbound.mappers;

import com.joseph.finance.adapters.outbound.entities.JpaWorkspaceEntity;
import com.joseph.finance.domain.models.Workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkspaceMapper {
    public static Workspace toDomain(JpaWorkspaceEntity jpaWorkspaceEntity) {
        if (jpaWorkspaceEntity == null) return null;

        Workspace workspace = new Workspace();
        workspace.setId(jpaWorkspaceEntity.getId());
        workspace.setName(jpaWorkspaceEntity.getName());
        workspace.setIcon(jpaWorkspaceEntity.getIcon());
        workspace.setUser(UserMapper.toDomain(jpaWorkspaceEntity.getOwner()));
        workspace.setAmount(jpaWorkspaceEntity.getAmount());
        workspace.setFinanceEntry(List.of());
        workspace.setMembers(Optional.ofNullable(jpaWorkspaceEntity.getMembers())
            .stream()
            .flatMap(List::stream)
            .map(UserMapper::toDomain)
            .toList());
//        workspace.setFinanceEntry(jpaWorkspaceEntity.getFinanceEntries()
//            .stream()
//            .map(FinanceTransactionMapper::toDomain)
//            .collect(Collectors.toCollection(ArrayList::new)));

        return workspace;
    }

    public static JpaWorkspaceEntity toEntity(Workspace workspace) {
        if (workspace == null) return null;

        return JpaWorkspaceEntity.builder()
            .id(workspace.getId())
            .owner(UserMapper.toEntity(workspace.getUser()))
            .icon(workspace.getIcon())
            .name(workspace.getName())
            .amount(workspace.getAmount())
            .members(Optional.ofNullable(workspace.getMembers())
                .stream()
                .flatMap(List::stream)
                .map(UserMapper::toEntity)
                .toList())
            .financeEntries(List.of())
            .build();
    }
}
