package com.jmpormar.certificacion.dto;

import com.jmpormar.certificacion.entity.TipoArchivoCertificacion;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CertificacionResponse(
        UUID idCertificacion,
        String nombre,
        String tipo,
        String descripcion,
        String archivoUrl,
        TipoArchivoCertificacion tipoArchivo,
        int orden,
        boolean activo,
        OffsetDateTime fechaCreacion,
        OffsetDateTime fechaActualizacion
) {}
