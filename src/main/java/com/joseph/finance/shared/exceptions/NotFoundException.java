package com.joseph.finance.shared.exceptions;

import java.util.Map;

public class NotFoundException extends ErrorInstance {
    private static final int statusCode = 404;

    public NotFoundException(String message) {
        this(message, Map.of());
    }

    public NotFoundException(String message, Map<String, String[]> errors) {
        super(message, statusCode);
        setType("Not Found");
        setErrors(errors != null ? errors : Map.of());
    }
}
