package com.jmpormar.servicio.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ServicioResponse(
        UUID idServicio,
        String nombre,
        String proyectoRelacionado,
        String descripcionBreve,
        String descripcionCompleta,
        String imagenPrincipalUrl,
        boolean activo,
        List<ServicioImagenResponse> imagenes,
        OffsetDateTime fechaCreacion,
        OffsetDateTime fechaActualizacion
) {}
