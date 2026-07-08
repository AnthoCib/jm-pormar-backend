package com.jmpormar.archivo.dto;



public record ArchivoResponse(
        String nombreOriginal,
        String nombreAlmacenado,
        String url,
        String publicId,
        String resourceType,
        String contentType,
        long size
) {
}