package com.jmpormar.contacto.dto;

public record ConsultaRucResponse(
        String ruc,
        String razonSocial,
        String estado,
        String condicion,
        String direccion
) {
}