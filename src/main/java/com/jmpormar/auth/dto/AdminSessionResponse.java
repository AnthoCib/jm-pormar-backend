package com.jmpormar.auth.dto;

import java.util.UUID;

public record AdminSessionResponse(
        UUID idAdmin,
        String usuario,
        String correo,
        String rol
) {
}
