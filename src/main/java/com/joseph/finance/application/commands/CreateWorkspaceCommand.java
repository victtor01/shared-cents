package com.joseph.finance.application.commands;

import com.joseph.finance.domain.models.User;

public record CreateWorkspaceCommand(String name, String icon, User user) {
}
