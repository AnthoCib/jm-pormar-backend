package com.jmpormar.contacto.dto;

import com.jmpormar.shared.validation.ValidationPatterns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactoRequest(
        @NotBlank(message = "El WhatsApp es obligatorio")
        @Pattern(regexp = ValidationPatterns.WHATSAPP,
                message = "El WhatsApp debe contener entre 9 y 15 dígitos incluyendo el código de país")
        String whatsapp,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        @Size(max = 150, message = "El correo debe tener máximo 150 caracteres")
        String correo,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(min = 5, max = 250, message = "La dirección debe tener entre 5 y 250 caracteres")
        String direccion,

        @NotBlank(message = "El horario de atención es obligatorio")
        @Size(min = 5, max = 180, message = "El horario de atención debe tener entre 5 y 180 caracteres")
        String horarioAtencion,

        @Pattern(regexp = ValidationPatterns.RUC_OPTIONAL,
                message = "El RUC debe tener 11 dígitos o quedar vacío")
        String ruc
) {
}
