package com.jmpormar.shared.dto;

import jakarta.validation.constraints.NotNull;

public record EstadoRequest(
        @NotNull(message = "El estado activo es obligatorio")
        Boolean activo
) {
}
