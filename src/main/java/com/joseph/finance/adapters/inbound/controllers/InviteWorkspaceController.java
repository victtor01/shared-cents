package com.joseph.finance.adapters.inbound.controllers;

import com.joseph.finance.adapters.inbound.dtos.request.CreateInviteWorkspaceRequest;
import com.joseph.finance.application.commands.CreateInviteWorkspaceCommand;
import com.joseph.finance.application.ports.in.InviteWorkspaceServicePort;
import com.joseph.finance.application.ports.in.SessionServicePort;
import com.joseph.finance.domain.models.InviteWorkspace;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/workspaces-invites")
public class InviteWorkspaceController {
    private final InviteWorkspaceServicePort inviteWorkspaceServicePort;
    private final SessionServicePort sessionServicePort;

    @Autowired
    public InviteWorkspaceController(InviteWorkspaceServicePort inviteWorkspaceRepositoryPort, SessionServicePort sessionServicePort) {
        this.inviteWorkspaceServicePort = inviteWorkspaceRepositoryPort;
        this.sessionServicePort = sessionServicePort;
    }

    @PostMapping
    public ResponseEntity<InviteWorkspace>
    save(@Valid @RequestBody CreateInviteWorkspaceRequest createInviteWorkspaceRequest) {

        UUID userId = this.sessionServicePort.getId();

        InviteWorkspace inviteWorkspace = this.inviteWorkspaceServicePort.save(
            new CreateInviteWorkspaceCommand(
                userId,
                createInviteWorkspaceRequest.receiver(),
                createInviteWorkspaceRequest.workspaceId()
            )
        );

        return ResponseEntity.status(HttpStatus.OK).body(inviteWorkspace);
    }
}
