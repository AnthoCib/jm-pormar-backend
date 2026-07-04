package com.jmpormar.certificacion.controller;

import com.jmpormar.archivo.service.ArchivoService;
import com.jmpormar.certificacion.dto.CertificacionResponse;
import com.jmpormar.certificacion.service.CertificacionService;
import com.jmpormar.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/certificaciones")
@RequiredArgsConstructor
public class CertificacionPublicController {
    private static final String CERTIFICATION_PREFIX = "/api/files/certificaciones/";

    private final CertificacionService service;
    private final ArchivoService archivoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificacionResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPublico()));
    }

    @GetMapping("/{id}/ver")
    public ResponseEntity<Resource> ver(@PathVariable UUID id) {
        var cert = service.getEntity(id);
        if (!cert.isActivo() || cert.getArchivoUrl() == null
                || !cert.getArchivoUrl().startsWith(CERTIFICATION_PREFIX)) {
            return ResponseEntity.notFound().build();
        }

        String filename = cert.getArchivoUrl().substring(CERTIFICATION_PREFIX.length());
        if (!filename.matches("^[A-Za-z0-9._-]+$")) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = archivoService.cargar("certificaciones", filename);
        String contentType = archivoService.contentType(resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .cacheControl(CacheControl.noStore())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "SAMEORIGIN")
                .header("Content-Security-Policy", "default-src 'self'; frame-ancestors 'self'")
                .body(resource);
    }
}
