package com.jmpormar.producto.dto;

import java.util.UUID;

public record ProductoImagenResponse(UUID idImagen, String urlImagen, int orden, boolean activo) {
}
