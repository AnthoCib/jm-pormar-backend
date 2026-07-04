package com.jmpormar.dashboard.service;

import com.jmpormar.categoria.repository.CategoriaRepository;
import com.jmpormar.certificacion.repository.CertificacionRepository;
import com.jmpormar.contacto.service.ContactoService;
import com.jmpormar.dashboard.dto.DashboardResponse;
import com.jmpormar.producto.entity.DisponibilidadProducto;
import com.jmpormar.producto.repository.ProductoRepository;
import com.jmpormar.servicio.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ServicioRepository servicioRepository;
    private final CertificacionRepository certificacionRepository;
    private final ContactoService contactoService;

    @Transactional(readOnly = true)
    public DashboardResponse obtener() {
        return new DashboardResponse(
                productoRepository.count(),
                productoRepository.countByActivoTrue(),
                productoRepository.countByDisponibilidad(DisponibilidadProducto.NO_DISPONIBLE),
                categoriaRepository.count(),
                servicioRepository.countByActivoTrue(),
                certificacionRepository.countByActivoTrue(),
                contactoService.configurado()
        );
    }
}
