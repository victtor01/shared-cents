package com.joseph.finance.adapters.inbound.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkspaceResponse {
    private String id;
    private String name;
    private String icon;
    private int amount = 0;
    private UserResponse user;
    private List<UserResponse> members = List.of();
}
