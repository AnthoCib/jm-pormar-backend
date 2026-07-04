package com.jmpormar.producto.dto;

import com.jmpormar.shared.validation.ValidationPatterns;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoImagenRequest(
        @NotBlank(message = "La URL de la imagen es obligatoria")
        @Size(max = 500, message = "La URL de la imagen debe tener máximo 500 caracteres")
        @Pattern(regexp = ValidationPatterns.PRODUCT_IMAGE_URL,
                message = "La imagen debe ser una URL válida de productos")
        String urlImagen,

        @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
        @Max(value = 3, message = "Solo se permiten tres imágenes adicionales")
        Integer orden
) {
}
