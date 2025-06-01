package com.joseph.finance.adapters.inbound.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
    @Email
    String email,

    @NotBlank
    @NotNull
    String password
) {
}
