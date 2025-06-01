package com.joseph.finance.adapters.inbound.mappers;

import com.joseph.finance.adapters.inbound.dtos.response.UserResponse;
import com.joseph.finance.domain.models.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .build();
    }

}
