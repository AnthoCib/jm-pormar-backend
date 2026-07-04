package com.jmpormar.dashboard.dto;

public record DashboardResponse(
        long totalProductos,
        long productosActivos,
        long productosNoDisponibles,
        long totalCategorias,
        long serviciosActivos,
        long certificacionesActivas,
        boolean contactoConfigurado
) {}
