package com.jmpormar.auth.controller;

import com.jmpormar.auth.dto.AdminSessionResponse;
import com.jmpormar.auth.dto.LoginRequest;
import com.jmpormar.auth.dto.LoginResponse;
import com.jmpormar.auth.security.AdminPrincipal;
import com.jmpormar.auth.service.AuthService;
import com.jmpormar.shared.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Inicio de sesión correcto", authService.login(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdminSessionResponse>> me(@AuthenticationPrincipal AdminPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(authService.current(principal)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok(ApiResponse.ok("Sesión cerrada. El frontend debe eliminar el token JWT."));
    }
}
