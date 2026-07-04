package com.jmpormar.servicio.service;

import com.jmpormar.exception.BusinessException;
import com.jmpormar.exception.ResourceNotFoundException;
import com.jmpormar.servicio.dto.ServicioImagenRequest;
import com.jmpormar.servicio.dto.ServicioImagenResponse;
import com.jmpormar.servicio.dto.ServicioRequest;
import com.jmpormar.servicio.dto.ServicioResponse;
import com.jmpormar.servicio.entity.Servicio;
import com.jmpormar.servicio.entity.ServicioImagen;
import com.jmpormar.servicio.mapper.ServicioMapper;
import com.jmpormar.servicio.repository.ServicioRepository;
import com.jmpormar.shared.api.PageResponse;
import lombok.RequiredArgsConstructor;
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
public class ServicioService {
    private static final int MAX_IMAGENES_SECUNDARIAS = 3;
    private final ServicioRepository repository;
    private final ServicioMapper mapper;

    @Transactional(readOnly = true)
    public PageResponse<ServicioResponse> listarAdmin(String buscar, Boolean activo, int page, int size) {
        boolean tieneBusqueda = StringUtils.hasText(buscar);
        String termino = tieneBusqueda ? buscar.trim() : null;
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaCreacion"));
        Page<Servicio> resultado;

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
    public List<ServicioResponse> listarPublico() {
        return repository.findAllByActivoTrueOrderByNombreAsc().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ServicioResponse obtener(UUID id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public ServicioResponse crear(ServicioRequest request) {
        Servicio servicio = Servicio.builder()
                .nombre(clean(request.nombre()))
                .proyectoRelacionado(optional(request.proyectoRelacionado()))
                .descripcionBreve(clean(request.descripcionBreve()))
                .descripcionCompleta(clean(request.descripcionCompleta()))
                .imagenPrincipalUrl(optional(request.imagenPrincipalUrl()))
                .activo(request.activo() == null || request.activo())
                .build();
        return mapper.toResponse(repository.save(servicio));
    }

    @Transactional
    public ServicioResponse actualizar(UUID id, ServicioRequest request) {
        Servicio servicio = getEntity(id);
        servicio.setNombre(clean(request.nombre()));
        servicio.setProyectoRelacionado(optional(request.proyectoRelacionado()));
        servicio.setDescripcionBreve(clean(request.descripcionBreve()));
        servicio.setDescripcionCompleta(clean(request.descripcionCompleta()));
        servicio.setImagenPrincipalUrl(optional(request.imagenPrincipalUrl()));
        if (request.activo() != null) servicio.setActivo(request.activo());
        return mapper.toResponse(repository.save(servicio));
    }

    @Transactional
    public ServicioResponse estado(UUID id, boolean activo) {
        Servicio servicio = getEntity(id);
        servicio.setActivo(activo);
        return mapper.toResponse(repository.save(servicio));
    }

    @Transactional
    public ServicioImagenResponse agregarImagen(UUID id, ServicioImagenRequest request) {
        Servicio servicio = getEntity(id);
        if (servicio.getImagenes().size() >= MAX_IMAGENES_SECUNDARIAS) {
            throw new BusinessException("Un servicio admite como máximo 3 imágenes adicionales");
        }
        int orden = request.orden() == null ? servicio.getImagenes().size() + 1 : request.orden();
        ServicioImagen imagen = ServicioImagen.builder()
                .servicio(servicio)
                .urlImagen(request.urlImagen().trim())
                .orden(orden)
                .activo(true)
                .build();
        servicio.getImagenes().add(imagen);
        repository.save(servicio);
        return new ServicioImagenResponse(imagen.getIdImagen(), imagen.getUrlImagen(), imagen.getOrden(), imagen.isActivo());
    }

    @Transactional
    public void retirarImagen(UUID idServicio, UUID idImagen) {
        Servicio servicio = getEntity(idServicio);
        boolean removed = servicio.getImagenes().removeIf(i -> i.getIdImagen().equals(idImagen));
        if (!removed) throw new ResourceNotFoundException("Imagen no encontrada");
        repository.save(servicio);
    }

    public Servicio getEntity(UUID id) {
        return repository.findDetailByIdServicio(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));
    }

    private String clean(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    private String optional(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
