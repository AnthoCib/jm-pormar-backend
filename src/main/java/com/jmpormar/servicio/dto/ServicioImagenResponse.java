package com.jmpormar.servicio.dto;

import java.util.UUID;

public record ServicioImagenResponse(UUID idImagen, String urlImagen, int orden, boolean activo) {}
