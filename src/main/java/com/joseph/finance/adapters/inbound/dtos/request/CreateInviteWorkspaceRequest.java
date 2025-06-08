package com.joseph.finance.adapters.inbound.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateInviteWorkspaceRequest(
    @NotBlank
    String receiver,

    @NotNull
    @NotBlank
    String workspaceId
) {
}
