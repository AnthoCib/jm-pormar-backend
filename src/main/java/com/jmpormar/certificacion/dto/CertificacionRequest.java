package com.jmpormar.certificacion.dto;

import com.jmpormar.certificacion.entity.TipoArchivoCertificacion;
import com.jmpormar.shared.validation.ValidationPatterns;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CertificacionRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
        String nombre,

        @NotBlank(message = "El tipo es obligatorio")
        @Size(min = 2, max = 100, message = "El tipo debe tener entre 2 y 100 caracteres")
        String tipo,

        @Size(max = 2000, message = "La descripción debe tener máximo 2000 caracteres")
        String descripcion,

        @NotBlank(message = "La URL del archivo es obligatoria")
        @Size(max = 500, message = "La URL del archivo debe tener máximo 500 caracteres")
        @Pattern(regexp = ValidationPatterns.CERTIFICATION_FILE_URL,
                message = "El archivo debe ser una URL válida de certificaciones")
        String archivoUrl,

        @NotNull(message = "El tipo de archivo es obligatorio")
        TipoArchivoCertificacion tipoArchivo,

        @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
        @Max(value = 100, message = "El orden debe ser menor o igual a 100")
        int orden,

        Boolean activo
) {
}
