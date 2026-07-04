package com.jmpormar.servicio.controller;

import com.jmpormar.servicio.dto.ServicioImagenRequest;
import com.jmpormar.servicio.dto.ServicioImagenResponse;
import com.jmpormar.servicio.dto.ServicioRequest;
import com.jmpormar.servicio.dto.ServicioResponse;
import com.jmpormar.servicio.service.ServicioService;
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
@RequestMapping("/api/admin/servicios")
@RequiredArgsConstructor
@Validated
public class ServicioAdminController {
    private final ServicioService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ServicioResponse>>> listar(
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
    public ResponseEntity<ApiResponse<ServicioResponse>> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtener(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ServicioResponse>> crear(@Valid @RequestBody ServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Servicio creado", service.crear(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServicioResponse>> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ServicioRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Servicio actualizado", service.actualizar(id, request)));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<ServicioResponse>> estado(
            @PathVariable UUID id,
            @Valid @RequestBody EstadoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Estado actualizado", service.estado(id, request.activo())));
    }

    @PostMapping("/{id}/imagenes")
    public ResponseEntity<ApiResponse<ServicioImagenResponse>> imagen(
            @PathVariable UUID id,
            @Valid @RequestBody ServicioImagenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Imagen agregada", service.agregarImagen(id, request)));
    }

    @DeleteMapping("/{id}/imagenes/{idImagen}")
    public ResponseEntity<ApiResponse<Void>> retirar(
            @PathVariable UUID id,
            @PathVariable UUID idImagen) {
        service.retirarImagen(id, idImagen);
        return ResponseEntity.ok(ApiResponse.ok("Imagen retirada"));
    }
}
