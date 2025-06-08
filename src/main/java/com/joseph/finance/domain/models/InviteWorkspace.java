package com.joseph.finance.domain.models;

import com.joseph.finance.domain.enums.InviteWorkspaceStatus;

import java.util.UUID;

public class InviteWorkspace {
    private UUID id;
    private User sender;
    private User recipient;
    private Workspace workspace;
    private InviteWorkspaceStatus inviteWorkspaceStatus;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public InviteWorkspaceStatus getInviteWorkspaceStatus() {
        return inviteWorkspaceStatus;
    }

    public void setInviteWorkspaceStatus(InviteWorkspaceStatus inviteWorkspaceStatus) {
        this.inviteWorkspaceStatus = inviteWorkspaceStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
