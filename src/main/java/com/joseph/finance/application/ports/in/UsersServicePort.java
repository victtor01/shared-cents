package com.joseph.finance.application.ports.in;

import com.joseph.finance.application.commands.CreateUserCommand;
import com.joseph.finance.domain.models.User;

public interface UsersServicePort {
    User save(CreateUserCommand createUserCommand);
}
