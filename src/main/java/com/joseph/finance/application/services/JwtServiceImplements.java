package com.joseph.finance.application.services;

import com.joseph.finance.application.ports.in.JwtServicePort;
import com.joseph.finance.infrastructure.security.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImplements implements JwtServicePort {
    private final JwtProperties jwtProperties;

    @Autowired
    public JwtServiceImplements(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        System.out.println("Secret Key: " + jwtProperties.getSecretKey());
        System.out.println("JWT Expiration: " + jwtProperties.getJwtExpiration());
        System.out.println("JWT Long Expiration: " + jwtProperties.getJwtLongExpiration());
    }

    @Override
    public String generate(Map<String, String> claims, String username) {
        return generate(claims, username, jwtProperties.getJwtExpiration());
    }

    @Override
    public String generateLong(Map<String, String> claims, String username) {
        return generate(claims, username, jwtProperties.getJwtLongExpiration());
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = this.getAllClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired: " + e.getMessage());
            return true;
        } catch (Exception e) {
            System.out.println("Error while checking token expiration: " + e.getMessage());
            return true;
        }
    }

    @Override
    public Claims getAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    @Override
    public String generate(Map<String, String> claims, String subject, long expireInterval) {
        return Jwts.builder()
            .claims().add(claims).and()
            .subject(subject)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expireInterval))
            .signWith(getSignInKey())
            .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = jwtProperties.getSecretKey().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
