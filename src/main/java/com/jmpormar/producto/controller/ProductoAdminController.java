package com.jmpormar.producto.controller;

import com.jmpormar.producto.dto.ProductoAdminResumenResponse;
import com.jmpormar.producto.dto.ProductoImagenOrdenRequest;
import com.jmpormar.producto.dto.ProductoImagenRequest;
import com.jmpormar.producto.dto.ProductoImagenResponse;
import com.jmpormar.producto.dto.ProductoRequest;
import com.jmpormar.producto.dto.ProductoResponse;
import com.jmpormar.producto.entity.DisponibilidadProducto;
import com.jmpormar.producto.service.ProductoService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/productos")
@RequiredArgsConstructor
@Validated
public class ProductoAdminController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductoAdminResumenResponse>>> listar(
            @RequestParam(required = false)
            @Size(max = 100, message = "La búsqueda debe tener máximo 100 caracteres")
            String buscar,

            @RequestParam(required = false)
            UUID categoriaId,

            @RequestParam(required = false)
            DisponibilidadProducto disponibilidad,

            @RequestParam(required = false)
            Boolean activo,

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "La página no puede ser negativa")
            int page,

            @RequestParam(defaultValue = "20")
            @Min(value = 1, message = "El tamaño debe ser mayor o igual a 1")
            @Max(value = 100, message = "El tamaño máximo permitido es 100")
            int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        service.listarAdminResumen(
                                buscar,
                                categoriaId,
                                disponibilidad,
                                activo,
                                page,
                                size
                        )
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> obtener(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        service.obtenerAdmin(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponse>> crear(
            @Valid @RequestBody ProductoRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.ok(
                                "Producto creado",
                                service.crear(request)
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Producto actualizado",
                        service.actualizar(id, request)
                )
        );
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<ProductoResponse>> estado(
            @PathVariable UUID id,
            @Valid @RequestBody EstadoRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Estado actualizado",
                        service.cambiarEstado(id, request.activo())
                )
        );
    }

    @PostMapping("/{id}/imagenes")
    public ResponseEntity<ApiResponse<ProductoImagenResponse>> imagen(
            @PathVariable UUID id,
            @Valid @RequestBody ProductoImagenRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.ok(
                                "Imagen agregada",
                                service.agregarImagen(id, request)
                        )
                );
    }

    @DeleteMapping("/{id}/imagenes/{idImagen}")
    public ResponseEntity<ApiResponse<Void>> retirarImagen(
            @PathVariable UUID id,
            @PathVariable UUID idImagen
    ) {
        service.retirarImagen(id, idImagen);

        return ResponseEntity.ok(
                ApiResponse.ok("Imagen retirada")
        );
    }

    @PatchMapping("/{id}/imagenes/orden")
    public ResponseEntity<ApiResponse<List<ProductoImagenResponse>>> ordenar(
            @PathVariable UUID id,
            @Valid @RequestBody ProductoImagenOrdenRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Galería reordenada",
                        service.reordenar(id, request)
                )
        );
    }
}