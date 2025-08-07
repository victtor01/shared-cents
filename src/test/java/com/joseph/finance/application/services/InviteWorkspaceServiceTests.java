package com.joseph.finance.application.services;

import com.joseph.finance.application.commands.CreateInviteWorkspaceCommand;
import com.joseph.finance.application.ports.out.InviteWorkspaceRepositoryPort;
import com.joseph.finance.application.ports.out.UsersRepositoryPort;
import com.joseph.finance.application.ports.out.WorkspacesRepositoryPort;
import com.joseph.finance.domain.enums.InviteWorkspaceStatus;
import com.joseph.finance.domain.models.InviteWorkspace;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.domain.models.Workspace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InviteWorkspaceServiceTests {
    @Mock
    private InviteWorkspaceRepositoryPort inviteWorkspaceRepositoryPort;
    @Mock
    private UsersRepositoryPort usersRepositoryPort;
    @Mock
    private WorkspacesRepositoryPort workspacesRepositoryPort;
    @InjectMocks
    private InviteWorkspaceServiceImplements inviteWorkspaceServiceImplements;

    @Test
    public void shouldSaveInvite() {
        String username = "EXISTS_USERNAME";
        String workspaceId = "EXAMPLE_ID_WORKSPACE";
        UUID senderId = UUID.randomUUID();

        CreateInviteWorkspaceCommand command = new CreateInviteWorkspaceCommand(senderId, username, workspaceId);

        User sender = new User();
        sender.setId(senderId);

        User recipient = new User();
        recipient.setId(UUID.randomUUID());
        recipient.setUsername(username);

        Workspace workspace = new Workspace();
        workspace.setId(workspaceId);
        workspace.setUser(sender);
        recipient.setUsername(username);

        InviteWorkspace savedInvite = new InviteWorkspace();
        savedInvite.setId(UUID.randomUUID());
        savedInvite.setSender(sender);
        savedInvite.setRecipient(recipient);
        savedInvite.setWorkspace(workspace);
        savedInvite.setInviteWorkspaceStatus(InviteWorkspaceStatus.PENDING);

        when(workspacesRepositoryPort.findById(workspaceId)).thenReturn(Optional.of(workspace));
        when(usersRepositoryPort.findById(senderId)).thenReturn(Optional.of(sender));
        when(usersRepositoryPort.findByUsername(username)).thenReturn(Optional.of(recipient));
        when(inviteWorkspaceRepositoryPort.findBySenderAndReceiver(sender.getId(), recipient.getId())).thenReturn(Optional.empty()); // Nenhum convite existente
        when(inviteWorkspaceRepositoryPort.save(any(InviteWorkspace.class))).thenReturn(savedInvite);

        InviteWorkspace result = inviteWorkspaceServiceImplements.save(command);

        assertNotNull(result);
        assertEquals(senderId, result.getSender().getId());
        assertEquals(username, result.getRecipient().getUsername());
        assertEquals(workspaceId, result.getWorkspace().getId());
        assertEquals(InviteWorkspaceStatus.PENDING, result.getInviteWorkspaceStatus());

        verify(workspacesRepositoryPort, times(1)).findById(workspaceId);
        verify(usersRepositoryPort, times(1)).findById(senderId);
        verify(usersRepositoryPort, times(1)).findByUsername(username);
        verify(inviteWorkspaceRepositoryPort, times(1)).save(any(InviteWorkspace.class));
    }
}
