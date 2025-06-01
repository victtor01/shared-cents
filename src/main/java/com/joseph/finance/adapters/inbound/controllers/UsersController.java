package com.joseph.finance.adapters.inbound.controllers;

import com.joseph.finance.adapters.inbound.dtos.request.CreateUserRequest;
import com.joseph.finance.application.commands.CreateUserCommand;
import com.joseph.finance.application.ports.in.UsersServicePort;
import com.joseph.finance.domain.models.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersServicePort usersServicePort;

    public UsersController(UsersServicePort usersServicePort) {
        this.usersServicePort = usersServicePort;
    }

    @PostMapping
    public ResponseEntity<User> UsersController(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User created = this.usersServicePort.save(new CreateUserCommand(
            createUserRequest.username(),
            createUserRequest.firstName(),
            createUserRequest.lastName(),
            createUserRequest.email(),
            createUserRequest.password()
        ));

        return ResponseEntity.status(HttpStatus.OK).body(created);
    }
}
