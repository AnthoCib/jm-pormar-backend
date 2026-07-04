package com.jmpormar.servicio.dto;

import com.jmpormar.shared.validation.ValidationPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ServicioRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
        String nombre,

        @Size(max = 180, message = "El proyecto relacionado debe tener máximo 180 caracteres")
        String proyectoRelacionado,

        @NotBlank(message = "La descripción breve es obligatoria")
        @Size(max = 250, message = "La descripción breve debe tener máximo 250 caracteres")
        String descripcionBreve,

        @NotBlank(message = "La descripción completa es obligatoria")
        @Size(max = 5000, message = "La descripción completa debe tener máximo 5000 caracteres")
        String descripcionCompleta,

        @Size(max = 500, message = "La URL de imagen debe tener máximo 500 caracteres")
        @Pattern(regexp = ValidationPatterns.SERVICE_IMAGE_URL,
                message = "La imagen principal debe ser una URL válida de servicios")
        String imagenPrincipalUrl,

        Boolean activo
) {
}
