package com.jmpormar.auth.service;

import com.jmpormar.auth.dto.AdminSessionResponse;
import com.jmpormar.auth.dto.LoginRequest;
import com.jmpormar.auth.dto.LoginResponse;
import com.jmpormar.auth.security.AdminPrincipal;
import com.jmpormar.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.identificador().trim(), request.password()));
        AdminPrincipal principal = (AdminPrincipal) authentication.getPrincipal();
        return new LoginResponse(
                jwtService.generateToken(principal),
                "Bearer",
                jwtService.getExpirationMs() / 1000,
                principal.getIdAdmin(),
                principal.getUsuario(),
                principal.getCorreo(),
                "ADMIN"
        );
    }

    public AdminSessionResponse current(AdminPrincipal principal) {
        return new AdminSessionResponse(principal.getIdAdmin(), principal.getUsuario(), principal.getCorreo(), "ADMIN");
    }
}
