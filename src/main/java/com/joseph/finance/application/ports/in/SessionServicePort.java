package com.joseph.finance.application.ports.in;

import com.joseph.finance.domain.models.User;

import java.util.UUID;

public interface SessionServicePort {
    UUID getId();
    User getUser();
}
