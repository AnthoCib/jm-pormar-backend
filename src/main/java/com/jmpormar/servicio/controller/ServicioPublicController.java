package com.jmpormar.servicio.controller;

import com.jmpormar.servicio.dto.ServicioResponse;
import com.jmpormar.servicio.service.ServicioService;
import com.jmpormar.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/servicios")
@RequiredArgsConstructor
public class ServicioPublicController {
    private final ServicioService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServicioResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPublico()));
    }
}
