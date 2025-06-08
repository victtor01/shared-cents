package com.joseph.finance.application.ports.out;

import com.joseph.finance.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepositoryPort {
    Optional<User> save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID userId);
    Optional<User> findByUsername(String username);
}
