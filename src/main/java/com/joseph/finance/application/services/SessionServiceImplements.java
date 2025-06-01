package com.joseph.finance.application.services;

import com.joseph.finance.adapters.outbound.entities.JpaUserEntity;
import com.joseph.finance.adapters.outbound.mappers.UserMapper;
import com.joseph.finance.application.ports.in.SessionServicePort;
import com.joseph.finance.domain.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionServiceImplements implements SessionServicePort {
    @Override
    public UUID getId() {
        User user = getUser();
        return user.getId();
    }

    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JpaUserEntity user = (JpaUserEntity) authentication.getPrincipal();
        return UserMapper.toDomain(user);
    }
}
