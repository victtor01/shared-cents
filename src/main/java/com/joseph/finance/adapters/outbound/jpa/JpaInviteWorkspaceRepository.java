package com.joseph.finance.adapters.outbound.jpa;

import com.joseph.finance.adapters.outbound.entities.JpaInviteWorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaInviteWorkspaceRepository extends JpaRepository<JpaInviteWorkspaceEntity, UUID> {
    @Query("SELECT i FROM JpaInviteWorkspaceEntity i WHERE i.sender.id = :senderId AND i.recipient.id = :recipientId")
    Optional<JpaInviteWorkspaceEntity> findBySenderAndReceiver(
        @Param("senderId") UUID senderId,
        @Param("recipientId") UUID recipientId
    );
}
