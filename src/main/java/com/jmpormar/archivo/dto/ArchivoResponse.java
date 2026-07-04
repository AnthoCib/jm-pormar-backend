package com.jmpormar.archivo.dto;

public record ArchivoResponse(
        String nombreOriginal,
        String nombreAlmacenado,
        String url,
        String contentType,
        long size
) {}
