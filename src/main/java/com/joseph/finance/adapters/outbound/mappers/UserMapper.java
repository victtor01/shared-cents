package com.joseph.finance.adapters.outbound.mappers;

import com.joseph.finance.adapters.outbound.entities.JpaUserEntity;
import com.joseph.finance.domain.models.User;

public class UserMapper {
    public static User toDomain(JpaUserEntity entity) {
        if(entity == null) return null;
        return User.builder()
            .id(entity.getId())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .username(entity.getUsername())
            .build();
    }

    public static JpaUserEntity toEntity(User entity) {
        if(entity == null) return null;

        return JpaUserEntity.builder()
            .id(entity.getId())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .username(entity.getUsername())
            .build();
    }

}
