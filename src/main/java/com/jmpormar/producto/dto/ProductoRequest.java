package com.jmpormar.producto.dto;

import com.jmpormar.producto.entity.DisponibilidadProducto;
import com.jmpormar.shared.validation.ValidationPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ProductoRequest(
        @NotNull(message = "La categoría es obligatoria")
        UUID idCategoria,

        @NotBlank(message = "El SKU es obligatorio")
        @Size(min = 2, max = 50, message = "El SKU debe tener entre 2 y 50 caracteres")
        @Pattern(regexp = ValidationPatterns.SKU, message = "El SKU solo admite letras, números, punto, guion, guion bajo y barra")
        String codigoSku,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
        String nombre,

        @NotNull(message = "La disponibilidad es obligatoria")
        DisponibilidadProducto disponibilidad,

        @NotBlank(message = "La descripción breve es obligatoria")
        @Size(max = 250, message = "La descripción breve debe tener máximo 250 caracteres")
        String descripcionBreve,

        @NotBlank(message = "La descripción completa es obligatoria")
        @Size(max = 5000, message = "La descripción completa debe tener máximo 5000 caracteres")
        String descripcionCompleta,

        @Size(max = 4000, message = "Las características deben tener máximo 4000 caracteres")
        String caracteristicas,

        @Size(max = 4000, message = "Las especificaciones técnicas deben tener máximo 4000 caracteres")
        String especificacionesTecnicas,

        @Size(max = 500, message = "La URL de imagen debe tener máximo 500 caracteres")
        @Pattern(regexp = ValidationPatterns.PRODUCT_IMAGE_URL,
                message = "La imagen principal debe ser una URL válida de productos")
        String imagenPrincipalUrl,

        Boolean activo
) {
}
