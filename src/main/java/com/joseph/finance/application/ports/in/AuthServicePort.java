package com.joseph.finance.application.ports.in;

import com.joseph.finance.application.commands.AuthenticatedCommand;

public interface AuthServicePort {
    AuthenticatedCommand auth(String email, String password);
}
