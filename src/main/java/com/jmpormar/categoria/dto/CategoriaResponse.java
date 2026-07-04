package com.jmpormar.categoria.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CategoriaResponse(
        UUID idCategoria,
        String nombre,
        String descripcion,
        boolean activo,
        OffsetDateTime fechaCreacion,
        OffsetDateTime fechaActualizacion
) {
}
