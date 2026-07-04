package com.jmpormar.contacto.controller;

import com.jmpormar.contacto.dto.ContactoResponse;
import com.jmpormar.contacto.service.ContactoService;
import com.jmpormar.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/contacto")
@RequiredArgsConstructor
public class ContactoPublicController {
    private final ContactoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<ContactoResponse>> obtener() {
        return ResponseEntity.ok(ApiResponse.ok(service.obtener()));
    }
}
