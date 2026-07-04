package com.jmpormar.contacto.controller;

import com.jmpormar.contacto.dto.ConsultaRucResponse;
import com.jmpormar.contacto.dto.ContactoResponse;
import com.jmpormar.contacto.service.ConsultaRucService;
import com.jmpormar.contacto.service.ContactoService;
import com.jmpormar.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/contacto")
@RequiredArgsConstructor
public class ContactoPublicController {
    private final ContactoService service;
    private final ConsultaRucService consultaRucService;

    @GetMapping
    public ResponseEntity<ApiResponse<ContactoResponse>> obtener() {
        return ResponseEntity.ok(ApiResponse.ok(service.obtener()));
    }
  

    @GetMapping("/consulta-ruc/{ruc}")
    public ResponseEntity<ApiResponse<ConsultaRucResponse>> consultar(
            @PathVariable String ruc
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        consultaRucService.consultar(ruc)
                )
        );
    }
}
