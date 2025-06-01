package com.joseph.finance.adapters.inbound.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CreateWorkspaceRequest(
    @NotBlank
    String name,
    String icon
){
}
