package com.joseph.finance.application.ports.in;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtServicePort {
    String generate(Map<String, String> claims, String username, long expireInterval);

    String generate(Map<String, String> claims, String username);

    String generateLong(Map<String, String> claims, String username);

    boolean isTokenExpired(String token);

    Claims getAllClaims(String token);
}
