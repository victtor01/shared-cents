package com.joseph.finance.adapters.inbound.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
    @NotBlank
    String username,

    @NotBlank
    String firstName,

    @NotBlank
    String lastName,

    @NotBlank
    String email,

    @NotBlank
    String password
) {
}
