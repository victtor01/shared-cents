package com.joseph.finance.application.ports.in;

import org.springframework.http.ResponseCookie;

public interface CookiesServicePort {
    ResponseCookie createTokenCookie(String token);
    ResponseCookie createRefreshTokenCookie(String refreshToken);
}
