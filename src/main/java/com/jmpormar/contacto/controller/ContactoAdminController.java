package com.jmpormar.contacto.controller;

import com.jmpormar.contacto.dto.*;
import com.jmpormar.contacto.service.ContactoService;
import com.jmpormar.shared.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/contacto")
@RequiredArgsConstructor
public class ContactoAdminController {
    private final ContactoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<ContactoResponse>> obtener() {
        return ResponseEntity.ok(ApiResponse.ok(service.obtener()));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ContactoResponse>> guardar(@Valid @RequestBody ContactoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Configuración de contacto guardada", service.guardar(request)));
    }
}
