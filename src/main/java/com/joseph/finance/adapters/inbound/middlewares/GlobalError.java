package com.joseph.finance.adapters.inbound.middlewares;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.exceptions.ErrorResponse;
import com.joseph.finance.shared.exceptions.NotFoundException;
import com.joseph.finance.shared.exceptions.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalError {

    private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("errorMessage", "INVALID_ARGUMENT");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Token Expired");
        response.put("message", "Your session has expired. Please login again.");

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "format of date is invalid", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Have error in server, try again", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleResourceBad(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse(ex.getType(), ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFound(NotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getType(), ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ErrorResponse> handleResourceUnauthorized(UnauthorizedException ex) {
        ErrorResponse error = new ErrorResponse(ex.getType(), ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
