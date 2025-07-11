package com.joseph.finance.adapters.outbound.jpa;

import com.joseph.finance.adapters.outbound.entities.JpaUserEntity;
import com.joseph.finance.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUsersRepository extends JpaRepository<JpaUserEntity, UUID> {
    Optional<JpaUserEntity> findByEmail(String email);

    @Query("SELECT u FROM users u WHERE u.username = :username")
    Optional<JpaUserEntity> findByUsername(@Param("username") String username);
}
