// CustomAuthenticationEntryPoint.java
package com.joseph.finance.infrastructure.config.SecurityConfig; // Seu pacote

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Para LocalDateTime
import com.joseph.finance.shared.exceptions.ErrorInstance;
import com.joseph.finance.shared.exceptions.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ErrorResponse errorResponse = new ErrorResponse("UNAUTHORIZED", "Você não tem permissão para acessar", 401);


        OutputStream out = response.getOutputStream();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(out, errorResponse);
        out.flush();
    }
}