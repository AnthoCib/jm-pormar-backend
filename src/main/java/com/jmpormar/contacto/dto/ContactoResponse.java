package com.jmpormar.contacto.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ContactoResponse(
        UUID idConfiguracion,
        String whatsapp,
        String correo,
        String direccion,
        String horarioAtencion,
        String ruc,
        OffsetDateTime fechaActualizacion
) {}
