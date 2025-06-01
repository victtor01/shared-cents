package com.joseph.finance.application.commands;

public record CreateUserCommand(
    String username,
    String firstName,
    String lastName,
    String email,
    String password
) {
}
