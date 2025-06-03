package com.joseph.finance.adapters.outbound.repositories;

import com.joseph.finance.adapters.outbound.entities.JpaWorkspaceEntity;
import com.joseph.finance.adapters.outbound.jpa.JpaWorkspacesRepository;
import com.joseph.finance.adapters.outbound.mappers.UserMapper;
import com.joseph.finance.adapters.outbound.mappers.WorkspaceMapper;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.models.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkspacesRepositoryImplements implements WorkspacesRepositoryPort {

    private final JpaWorkspacesRepository jpaWorkspacesRepository;

    @Autowired
    public WorkspacesRepositoryImplements(JpaWorkspacesRepository jpaWorkspacesRepository) {
        this.jpaWorkspacesRepository = jpaWorkspacesRepository;
    }

    @Override
    public Workspace save(Workspace workspace) {
        JpaWorkspaceEntity jpaWorkspaceEntity = this.jpaWorkspacesRepository.save(WorkspaceMapper.toEntity(workspace));
        return WorkspaceMapper.toDomain(jpaWorkspaceEntity);
    }

    @Override
    public List<Workspace> findByOwnerIdOrMemberId(UUID userId) {
        List<JpaWorkspaceEntity> jpaWorkspaceEntities = this.jpaWorkspacesRepository.findByOwnerIdOrMemberId(userId);
        return jpaWorkspaceEntities.stream().map(WorkspaceMapper::toDomain).toList();
    }

    @Override
    public Optional<Workspace> findById(String workspaceId) {
        return jpaWorkspacesRepository.findById(workspaceId)
            .map(WorkspaceMapper::toDomain);
    }
}
