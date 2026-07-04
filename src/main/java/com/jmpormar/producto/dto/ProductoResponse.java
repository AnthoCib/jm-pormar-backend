package com.jmpormar.producto.dto;

import com.jmpormar.producto.entity.DisponibilidadProducto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ProductoResponse(
        UUID idProducto,
        UUID idCategoria,
        String categoria,
        String codigoSku,
        String nombre,
        DisponibilidadProducto disponibilidad,
        String descripcionBreve,
        String descripcionCompleta,
        String caracteristicas,
        String especificacionesTecnicas,
        String imagenPrincipalUrl,
        boolean activo,
        List<ProductoImagenResponse> imagenes,
        OffsetDateTime fechaCreacion,
        OffsetDateTime fechaActualizacion
) {
}
