package com.jmpormar.archivo.controller;

import com.jmpormar.archivo.service.ArchivoService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Validated
public class ArchivoPublicController {
    private final ArchivoService service;

    @GetMapping("/{carpeta}/{filename:.+}")
    public ResponseEntity<Resource> ver(
            @PathVariable
            @Pattern(regexp = "productos|servicios|certificaciones", message = "La carpeta de archivos no es válida")
            String carpeta,
            @PathVariable
            @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "El nombre de archivo no es válido")
            String filename) {
        Resource resource = service.cargar(carpeta, filename);
        String contentType = service.contentType(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .header("X-Content-Type-Options", "nosniff")
                .body(resource);
    }
}
