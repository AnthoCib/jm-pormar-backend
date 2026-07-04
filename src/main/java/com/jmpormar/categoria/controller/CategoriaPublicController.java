package com.jmpormar.categoria.controller;

import com.jmpormar.categoria.dto.CategoriaResponse;
import com.jmpormar.categoria.service.CategoriaService;
import com.jmpormar.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/categorias")
@RequiredArgsConstructor
public class CategoriaPublicController {
    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPublicas()));
    }
}
