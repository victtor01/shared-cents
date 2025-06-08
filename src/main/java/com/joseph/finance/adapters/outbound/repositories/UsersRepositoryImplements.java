package com.joseph.finance.adapters.outbound.repositories;

import com.joseph.finance.adapters.outbound.entities.JpaUserEntity;
import com.joseph.finance.adapters.outbound.jpa.JpaUsersRepository;
import com.joseph.finance.adapters.outbound.mappers.UserMapper;
import com.joseph.finance.application.ports.out.UsersRepositoryPort;
import com.joseph.finance.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UsersRepositoryImplements implements UsersRepositoryPort {
    private final JpaUsersRepository jpaUsersRepository;

    @Autowired
    public UsersRepositoryImplements(JpaUsersRepository jpaUsersRepository) {
        this.jpaUsersRepository = jpaUsersRepository;
    }

    @Override
    public Optional<User> save(User user) {
        JpaUserEntity userEntity = this.jpaUsersRepository.save(UserMapper.toEntity(user));
        return Optional.of(UserMapper.toDomain(userEntity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.jpaUsersRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return this.jpaUsersRepository.findById(userId).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.jpaUsersRepository.findByUsername(username).map(UserMapper::toDomain);
    }
}
