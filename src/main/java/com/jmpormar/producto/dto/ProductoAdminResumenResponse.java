package com.jmpormar.producto.dto;

import com.jmpormar.producto.entity.DisponibilidadProducto;

import java.util.UUID;


public record ProductoAdminResumenResponse(
        UUID idProducto,
        UUID idCategoria,
        String categoria,
        String codigoSku,
        String nombre,
        DisponibilidadProducto disponibilidad,
        String descripcionBreve,
        String imagenPrincipalUrl,
        boolean activo
) {
}