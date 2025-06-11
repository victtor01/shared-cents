package com.joseph.finance.adapters.inbound.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joseph.finance.domain.enums.InviteWorkspaceStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InviteWorkspaceResponse {
    private UUID id;
    private UserResponse sender;
    private UserResponse recipient;
    private WorkspaceResponse workspace;
    private InviteWorkspaceStatus status;
}
