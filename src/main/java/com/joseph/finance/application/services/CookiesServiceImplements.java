package com.joseph.finance.application.services;

import com.joseph.finance.application.ports.in.CookiesServicePort;
import com.joseph.finance.infrastructure.security.JwtProperties;
import com.joseph.finance.shared.utils.CookiesKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookiesServiceImplements implements CookiesServicePort {

    private final JwtProperties jwtProperties;

    @Autowired
    public CookiesServiceImplements(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public ResponseCookie createTokenCookie(String token) {
        return ResponseCookie.from(CookiesKeys.ACCESS_TOKEN, token)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(jwtProperties.getJwtExpiration())
            .build();
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(CookiesKeys.REFRESH_TOKEN, refreshToken)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(jwtProperties.getJwtLongExpiration())
            .build();
    }
}
