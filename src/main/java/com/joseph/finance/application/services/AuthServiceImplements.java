package com.joseph.finance.application.services;

import com.joseph.finance.application.commands.AuthenticatedCommand;
import com.joseph.finance.application.ports.in.AuthServicePort;
import com.joseph.finance.application.ports.in.JwtServicePort;
import com.joseph.finance.shared.exceptions.BadRequestException;
import com.joseph.finance.shared.utils.AuthenticationClaims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImplements implements AuthServicePort {
    private final AuthenticationManager authenticationManager;
    private final JwtServicePort jwtServicePort;

    public AuthServiceImplements(AuthenticationManager authenticationManager, JwtServicePort jwtServicePort) {
        this.authenticationManager = authenticationManager;
        this.jwtServicePort = jwtServicePort;
    }

    @Override
    public AuthenticatedCommand auth(String email, String password) throws BadRequestException {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            var auth = authenticationManager.authenticate(authenticationToken);

            Map<String, String> claims = new HashMap<>();

            claims.put(AuthenticationClaims.role, auth.getAuthorities()
                .stream()
                .map(data -> data.getAuthority())
                .collect(Collectors.joining(",")));

            claims.put(AuthenticationClaims.email, auth.getName());

            String accessToken = this.jwtServicePort.generate(claims, auth.getName());
            String refreshToken = this.jwtServicePort.generateLong(claims, auth.getName());

            return new AuthenticatedCommand(accessToken, refreshToken);
        } catch (Exception e) {
            throw new BadRequestException("email or password incorrect");
        }

    }
}
