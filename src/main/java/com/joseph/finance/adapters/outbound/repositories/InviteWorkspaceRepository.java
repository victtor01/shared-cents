package com.joseph.finance.adapters.outbound.repositories;

import com.joseph.finance.adapters.outbound.entities.JpaInviteWorkspaceEntity;
import com.joseph.finance.adapters.outbound.jpa.JpaInviteWorkspaceRepository;
import com.joseph.finance.adapters.outbound.mappers.InviteWorkspaceMapper;
import com.joseph.finance.application.ports.out.InviteWorkspaceRepositoryPort;
import com.joseph.finance.domain.models.InviteWorkspace;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InviteWorkspaceRepository implements InviteWorkspaceRepositoryPort {
    private final JpaInviteWorkspaceRepository jpaInviteWorkspaceRepository;

    public InviteWorkspaceRepository(JpaInviteWorkspaceRepository jpaInviteWorkspaceRepository) {
        this.jpaInviteWorkspaceRepository = jpaInviteWorkspaceRepository;
    }

    @Override
    public InviteWorkspace save(InviteWorkspace workspace) {
        JpaInviteWorkspaceEntity jpaInviteWorkspace = this.jpaInviteWorkspaceRepository.save(InviteWorkspaceMapper.toEntity(workspace));
        return InviteWorkspaceMapper.toDomain(jpaInviteWorkspace);
    }

    @Override
    public Optional<InviteWorkspace> findBySenderAndReceiver(UUID sender, UUID receiver) {
        Optional<JpaInviteWorkspaceEntity> jpaInviteWorkspace = this.jpaInviteWorkspaceRepository.findByReceiver(sender, receiver);
        return jpaInviteWorkspace.map(InviteWorkspaceMapper::toDomain);
    }

    @Override
    public List<InviteWorkspace> findAllByReceiver(UUID receiver) {
        return this.jpaInviteWorkspaceRepository.findByReceiver(receiver)
            .stream()
            .map(InviteWorkspaceMapper::toDomain)
            .toList();
    }

    @Override
    public Optional<InviteWorkspace> findById(UUID inviteId) {
        return this.jpaInviteWorkspaceRepository.findById(inviteId)
            .map(InviteWorkspaceMapper::toDomain);
    }
}