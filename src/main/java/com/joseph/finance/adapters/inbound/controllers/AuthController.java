package com.joseph.finance.adapters.inbound.controllers;

import com.joseph.finance.adapters.inbound.dtos.request.AuthRequest;
import com.joseph.finance.adapters.inbound.dtos.response.UserResponse;
import com.joseph.finance.adapters.inbound.mappers.UserMapper;
import com.joseph.finance.application.commands.AuthenticatedCommand;
import com.joseph.finance.application.ports.in.AuthServicePort;
import com.joseph.finance.application.ports.in.CookiesServicePort;
import com.joseph.finance.application.ports.in.SessionServicePort;
import com.joseph.finance.domain.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthServicePort authServicePort;
    private final CookiesServicePort cookiesServicePort;
    private final SessionServicePort sessionServicePort;

    @Autowired
    public AuthController(AuthServicePort authServicePort, CookiesServicePort cookiesServicePort, SessionServicePort sessionServicePort) {
        this.authServicePort = authServicePort;
        this.cookiesServicePort = cookiesServicePort;
        this.sessionServicePort = sessionServicePort;
    }

    @PostMapping
    public ResponseEntity<AuthenticatedCommand> auth(@Valid @RequestBody AuthRequest authRequest) {
        AuthenticatedCommand auth = authServicePort.auth(authRequest.email(), authRequest.password());

        ResponseCookie accessTokenCookie = cookiesServicePort.createTokenCookie(auth.accessToken());
        ResponseCookie refreshTokenCookie = cookiesServicePort.createRefreshTokenCookie(auth.refreshToken());

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
            .body(auth);
    }

    @GetMapping("/session")
    public ResponseEntity<UserResponse> I() {
        User user = sessionServicePort.getUser();

        return ResponseEntity.status(HttpStatus.OK)
            .body(UserMapper.toResponse(user));
    }

    @PostMapping("/clear-cookies")
    public String clearAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0); // Expira imediatamente
                response.addCookie(cookie); // Adiciona à resposta para invalidar no cliente
            }
        }

        return "Todos os cookies foram limpos com sucesso!";
    }
}
