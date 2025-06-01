package com.joseph.finance.adapters.inbound.controllers;

import com.joseph.finance.adapters.inbound.dtos.request.CreateWorkspaceRequest;
import com.joseph.finance.adapters.inbound.dtos.response.WorkspaceResponse;
import com.joseph.finance.adapters.inbound.mappers.WorkspaceMapper;
import com.joseph.finance.application.commands.CreateWorkspaceCommand;
import com.joseph.finance.application.ports.in.SessionServicePort;
import com.joseph.finance.application.ports.in.WorkspacesServicePort;
import com.joseph.finance.domain.models.Workspace;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workspaces")
public class WorkspacesController {

    private final WorkspacesServicePort workspacesServicePort;
    private final SessionServicePort sessionServicePort;

    @Autowired
    public WorkspacesController(WorkspacesServicePort workspacesServicePort, SessionServicePort sessionServicePort) {
        this.workspacesServicePort = workspacesServicePort;
        this.sessionServicePort = sessionServicePort;
    }

    @PostMapping
    public ResponseEntity<Workspace> create(@Valid @RequestBody CreateWorkspaceRequest createWorkspaceRequest) {
        Workspace workspace = this.workspacesServicePort.save(new CreateWorkspaceCommand(
            createWorkspaceRequest.name(),
            createWorkspaceRequest.icon(),
            sessionServicePort.getUser()
        ));

        return ResponseEntity.status(HttpStatus.OK).body(workspace);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponse>> findAll() {
        UUID userId = sessionServicePort.getId();
        List<Workspace> workspaces = this.workspacesServicePort.findAllByUserOrWhereIsMember(userId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(workspaces.stream()
                .map(WorkspaceMapper::toResponse)
                .toList());
    }
}
