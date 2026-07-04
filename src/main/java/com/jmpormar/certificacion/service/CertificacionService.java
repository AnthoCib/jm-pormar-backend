package com.jmpormar.certificacion.service;

import com.jmpormar.certificacion.dto.CertificacionRequest;
import com.jmpormar.certificacion.dto.CertificacionResponse;
import com.jmpormar.certificacion.entity.Certificacion;
import com.jmpormar.certificacion.mapper.CertificacionMapper;
import com.jmpormar.certificacion.repository.CertificacionRepository;
import com.jmpormar.exception.BusinessException;
import com.jmpormar.exception.ResourceNotFoundException;
import com.jmpormar.shared.api.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificacionService {
    private final CertificacionRepository repository;
    private final CertificacionMapper mapper;

    @Value("${app.certifications.max-records:5}")
    private int maxRecords;

    @Transactional(readOnly = true)
    public PageResponse<CertificacionResponse> listarAdmin(String buscar, Boolean activo, int page, int size) {
        boolean tieneBusqueda = StringUtils.hasText(buscar);
        String termino = tieneBusqueda ? buscar.trim() : null;
        var pageable = PageRequest.of(page, size, Sort.by("orden").ascending().and(Sort.by("nombre")));
        Page<Certificacion> resultado;

        if (tieneBusqueda && activo != null) {
            resultado = repository.findByNombreContainingIgnoreCaseAndActivo(termino, activo, pageable);
        } else if (tieneBusqueda) {
            resultado = repository.findByNombreContainingIgnoreCase(termino, pageable);
        } else if (activo != null) {
            resultado = repository.findByActivo(activo, pageable);
        } else {
            resultado = repository.findAll(pageable);
        }
        return PageResponse.from(resultado, mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<CertificacionResponse> listarPublico() {
        return repository.findAllByActivoTrueOrderByOrdenAscNombreAsc().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CertificacionResponse obtener(UUID id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public CertificacionResponse crear(CertificacionRequest request) {
        if (repository.count() >= maxRecords) {
            throw new BusinessException("Se alcanzó el máximo de " + maxRecords + " certificaciones registradas");
        }
        Certificacion certificacion = Certificacion.builder()
                .nombre(clean(request.nombre()))
                .tipo(clean(request.tipo()))
                .descripcion(optional(request.descripcion()))
                .archivoUrl(request.archivoUrl().trim())
                .tipoArchivo(request.tipoArchivo())
                .orden(request.orden())
                .activo(request.activo() == null || request.activo())
                .build();
        return mapper.toResponse(repository.save(certificacion));
    }

    @Transactional
    public CertificacionResponse actualizar(UUID id, CertificacionRequest request) {
        Certificacion certificacion = getEntity(id);
        certificacion.setNombre(clean(request.nombre()));
        certificacion.setTipo(clean(request.tipo()));
        certificacion.setDescripcion(optional(request.descripcion()));
        certificacion.setArchivoUrl(request.archivoUrl().trim());
        certificacion.setTipoArchivo(request.tipoArchivo());
        certificacion.setOrden(request.orden());
        if (request.activo() != null) certificacion.setActivo(request.activo());
        return mapper.toResponse(repository.save(certificacion));
    }

    @Transactional
    public CertificacionResponse estado(UUID id, boolean activo) {
        Certificacion certificacion = getEntity(id);
        certificacion.setActivo(activo);
        return mapper.toResponse(repository.save(certificacion));
    }

    public Certificacion getEntity(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificación no encontrada"));
    }

    private String clean(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    private String optional(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
