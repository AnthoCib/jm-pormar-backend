package com.jmpormar.producto.controller;

import com.jmpormar.producto.dto.ProductoResponse;
import com.jmpormar.producto.entity.DisponibilidadProducto;
import com.jmpormar.producto.service.ProductoService;
import com.jmpormar.shared.api.ApiResponse;
import com.jmpormar.shared.api.PageResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/productos")
@RequiredArgsConstructor
@Validated
public class ProductoPublicController {
    private final ProductoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductoResponse>>> listar(
            @RequestParam(required = false)
            @Size(max = 100, message = "La búsqueda debe tener máximo 100 caracteres") String buscar,
            @RequestParam(required = false) UUID categoriaId,
            @RequestParam(required = false) DisponibilidadProducto disponibilidad,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "La página no puede ser negativa") int page,
            @RequestParam(defaultValue = "12")
            @Min(value = 1, message = "El tamaño debe ser mayor o igual a 1")
            @Max(value = 100, message = "El tamaño máximo permitido es 100") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                service.listarPublico(buscar, categoriaId, disponibilidad, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPublico(id)));
    }

    @GetMapping("/{id}/relacionados")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> relacionados(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(service.relacionados(id)));
    }
}
