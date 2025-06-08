package com.joseph.finance.adapters.outbound.entities;

import com.joseph.finance.domain.enums.InviteWorkspaceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "invite_workspaces")
public class JpaInviteWorkspaceEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private JpaUserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private JpaUserEntity recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private JpaWorkspaceEntity workspace;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InviteWorkspaceStatus inviteWorkspaceStatus;
}



