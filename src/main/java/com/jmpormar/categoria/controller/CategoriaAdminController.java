package com.jmpormar.categoria.controller;

import com.jmpormar.categoria.dto.CategoriaRequest;
import com.jmpormar.categoria.dto.CategoriaResponse;
import com.jmpormar.categoria.service.CategoriaService;
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
@RequestMapping("/api/admin/categorias")
@RequiredArgsConstructor
@Validated
public class CategoriaAdminController {
    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoriaResponse>>> listar(
            @RequestParam(required = false)
            @Size(max = 100, message = "La búsqueda debe tener máximo 100 caracteres") String buscar,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "La página no puede ser negativa") int page,
            @RequestParam(defaultValue = "20")
            @Min(value = 1, message = "El tamaño debe ser mayor o igual a 1")
            @Max(value = 100, message = "El tamaño máximo permitido es 100") int size) {
        return ResponseEntity.ok(ApiResponse.ok(service.listar(buscar, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponse>> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponse>> crear(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Categoría creada", service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponse>> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Categoría actualizada", service.actualizar(id, request)));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<CategoriaResponse>> estado(
            @PathVariable UUID id,
            @Valid @RequestBody EstadoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", service.cambiarEstado(id, request.activo())));
    }
}
