package com.joseph.finance.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "workspaces")
public class JpaWorkspaceEntity {
    @Id()
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<JpaFinanceTransaction> financeEntries = new ArrayList<>();

    @Column(name = "amount")
    private int amount = 0;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
        name = "workspace_members",
        joinColumns = @JoinColumn(name = "workspace_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<JpaUserEntity> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private JpaUserEntity owner;
}
