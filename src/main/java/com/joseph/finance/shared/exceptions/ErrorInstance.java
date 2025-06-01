package com.joseph.finance.shared.exceptions;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Setter
@Getter
public class ErrorInstance extends RuntimeException {
    private final int statusCode;

    private String type = "Internal Server Error";

    private Map<String, String[]> errors;

    public ErrorInstance(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
