package com.jmpormar.certificacion.controller;

import com.jmpormar.certificacion.dto.CertificacionRequest;
import com.jmpormar.certificacion.dto.CertificacionResponse;
import com.jmpormar.certificacion.service.CertificacionService;
import com.jmpormar.shared.api.ApiResponse;
import com.jmpormar.shared.api.PageResponse;
import com.jmpormar.shared.dto.EstadoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/certificaciones")
@RequiredArgsConstructor
@Validated
public class CertificacionAdminController {
    private final CertificacionService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CertificacionResponse>>> listar(
            @RequestParam(required = false)
            @Size(max = 100, message = "La búsqueda debe tener máximo 100 caracteres") String buscar,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "La página no puede ser negativa") int page,
            @RequestParam(defaultValue = "20")
            @Min(value = 1, message = "El tamaño debe ser mayor o igual a 1")
            @Max(value = 100, message = "El tamaño máximo permitido es 100") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarAdmin(buscar, activo, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificacionResponse>> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CertificacionResponse>> crear(
            @Valid @RequestBody CertificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Certificación creada", service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificacionResponse>> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CertificacionRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Certificación actualizada", service.actualizar(id, request)));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<CertificacionResponse>> estado(
            @PathVariable UUID id,
            @Valid @RequestBody EstadoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", service.estado(id, request.activo())));
    }
}
