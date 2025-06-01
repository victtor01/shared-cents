package com.joseph.finance.shared.exceptions;

import java.util.Map;

public class UnauthorizedException extends ErrorInstance {
    private static final int statusCode = 403;

    public UnauthorizedException(String message) {
        this(message, Map.of());
    }

    public UnauthorizedException(String message, Map<String, String[]> errors) {
        super(message, statusCode);
        setType("Unauthorized");
        setErrors(errors != null ? errors : Map.of());
    }
}