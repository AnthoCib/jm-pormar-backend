package com.jmpormar.producto.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ProductoImagenOrdenRequest(
        @NotEmpty(message = "Debe enviar al menos una imagen")
        @Size(max = 3, message = "Solo se pueden reordenar tres imágenes adicionales")
        List<@NotNull(message = "La imagen del listado no puede ser nula") @Valid Item> items
) {
    public record Item(
            @NotNull(message = "El identificador de imagen es obligatorio")
            UUID idImagen,

            @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
            @Max(value = 3, message = "El orden debe ser menor o igual a 3")
            int orden
    ) {
    }
}
