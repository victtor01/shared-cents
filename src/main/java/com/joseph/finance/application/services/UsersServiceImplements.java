package com.joseph.finance.application.services;

import com.joseph.finance.application.commands.CreateUserCommand;
import com.joseph.finance.application.ports.in.UsersServicePort;
import com.joseph.finance.application.ports.out.UsersRepositoryPort;
import com.joseph.finance.domain.models.User;
import com.joseph.finance.shared.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImplements implements UsersServicePort {

    private final UsersRepositoryPort usersRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImplements(UsersRepositoryPort usersRepositoryPort, PasswordEncoder passwordEncoder) {
        this.usersRepositoryPort = usersRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(CreateUserCommand createUserCommand) {
        String passHash = passwordEncoder.encode(createUserCommand.password());

        User userToCreate = User.builder()
            .firstName(createUserCommand.firstName())
            .lastName(createUserCommand.lastName())
            .username(createUserCommand.username())
            .password(passHash)
            .email(createUserCommand.email())
            .build();

        User created = this.usersRepositoryPort.save(userToCreate)
            .orElseThrow(() -> new BadRequestException("Houve um erro ao tentar criar usuário!"));

        return created;
    }
}
