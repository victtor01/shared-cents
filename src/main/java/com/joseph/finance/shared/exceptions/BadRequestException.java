package com.joseph.finance.shared.exceptions;

import java.util.Map;

public class BadRequestException extends ErrorInstance {
    private static final int statusCode = 400;

    public BadRequestException(String message) {
        this(message, Map.of());
    }

    public BadRequestException(String message, Map<String, String[]> errors) {
        super(message, statusCode);
        setType("Bad Request");
        setErrors(errors != null ? errors : Map.of());
    }
}
