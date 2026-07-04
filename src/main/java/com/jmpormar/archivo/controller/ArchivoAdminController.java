package com.jmpormar.archivo.controller;

import com.jmpormar.archivo.dto.ArchivoResponse;
import com.jmpormar.archivo.service.ArchivoService;
import com.jmpormar.shared.api.ApiResponse;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/archivos")
@RequiredArgsConstructor
@Validated
public class ArchivoAdminController {
    private final ArchivoService service;

    @PostMapping(value = "/{carpeta}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<ArchivoResponse>> cargar(
            @PathVariable
            @Pattern(regexp = "productos|servicios|certificaciones", message = "La carpeta de archivos no es válida")
            String carpeta,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Archivo cargado", service.guardar(carpeta, file)));
    }
}
