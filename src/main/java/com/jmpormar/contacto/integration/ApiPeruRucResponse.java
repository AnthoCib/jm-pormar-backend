package com.jmpormar.contacto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiPeruRucResponse(
        boolean success,
        Data data
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
            String ruc,

            @JsonProperty("nombre_o_razon_social")
            String nombreORazonSocial,

            String estado,
            String condicion,

            @JsonProperty("direccion_completa")
            String direccionCompleta,

            String direccion
    ) {
    }
}