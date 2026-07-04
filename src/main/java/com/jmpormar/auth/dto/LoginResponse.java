package com.jmpormar.auth.dto;

import java.util.UUID;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UUID idAdmin,
        String usuario,
        String correo,
        String rol
) {
}
