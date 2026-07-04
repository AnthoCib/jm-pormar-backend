package com.jmpormar.categoria.service;

import com.jmpormar.categoria.dto.CategoriaRequest;
import com.jmpormar.categoria.dto.CategoriaResponse;
import com.jmpormar.categoria.entity.Categoria;
import com.jmpormar.categoria.mapper.CategoriaMapper;
import com.jmpormar.categoria.repository.CategoriaRepository;
import com.jmpormar.exception.ConflictException;
import com.jmpormar.exception.ResourceNotFoundException;
import com.jmpormar.producto.repository.ProductoRepository;
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
public class CategoriaService {
    private final CategoriaRepository repository;
    private final ProductoRepository productoRepository;
    private final CategoriaMapper mapper;

    @Transactional(readOnly = true)
    public PageResponse<CategoriaResponse> listar(String buscar, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Categoria> resultado = StringUtils.hasText(buscar)
                ? repository.findByNombreContainingIgnoreCase(buscar.trim(), pageable)
                : repository.findAll(pageable);
        return PageResponse.from(resultado, mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarPublicas() {
        return repository.findAllByActivoTrueOrderByNombreAsc().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse obtener(UUID id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public CategoriaResponse crear(CategoriaRequest request) {
        String nombre = limpiarNombre(request.nombre());
        validarNombre(nombre, null);
        Categoria entity = Categoria.builder()
                .nombre(nombre)
                .descripcion(limpiar(request.descripcion()))
                .activo(request.activo() == null || request.activo())
                .build();
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public CategoriaResponse actualizar(UUID id, CategoriaRequest request) {
        Categoria entity = getEntity(id);
        String nombre = limpiarNombre(request.nombre());
        validarNombre(nombre, id);
        entity.setNombre(nombre);
        entity.setDescripcion(limpiar(request.descripcion()));
        if (request.activo() != null) entity.setActivo(request.activo());
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public CategoriaResponse cambiarEstado(UUID id, boolean activo) {
        Categoria entity = getEntity(id);
        if (!activo && productoRepository.existsByCategoriaIdCategoria(id)) {
            entity.setActivo(false);
        } else {
            entity.setActivo(activo);
        }
        return mapper.toResponse(repository.save(entity));
    }

    public Categoria getEntity(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
    }

    private void validarNombre(String nombre, UUID idActual) {
        boolean existe = idActual == null
                ? repository.existsByNombreIgnoreCase(nombre)
                : repository.existsByNombreIgnoreCaseAndIdCategoriaNot(nombre, idActual);
        if (existe) throw new ConflictException("Ya existe una categoría con ese nombre");
    }

    private String limpiarNombre(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    private String limpiar(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}
