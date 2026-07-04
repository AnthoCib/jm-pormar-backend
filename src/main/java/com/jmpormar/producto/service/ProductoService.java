package com.jmpormar.producto.service;

import com.jmpormar.categoria.entity.Categoria;
import com.jmpormar.categoria.service.CategoriaService;
import com.jmpormar.exception.BusinessException;
import com.jmpormar.exception.ConflictException;
import com.jmpormar.exception.ResourceNotFoundException;
import com.jmpormar.producto.dto.*;
import com.jmpormar.producto.entity.*;
import com.jmpormar.producto.mapper.ProductoMapper;
import com.jmpormar.producto.repository.*;
import com.jmpormar.shared.api.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;
    private final ProductoImagenRepository imagenRepository;
    private final CategoriaService categoriaService;
    private final ProductoMapper mapper;

    @Transactional(readOnly = true)
    public PageResponse<ProductoResponse> listarAdmin(String buscar, UUID categoriaId,
            DisponibilidadProducto disponibilidad, Boolean activo, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaCreacion"));
        var result = repository.findAll(ProductoSpecifications.filtros(buscar, categoriaId, disponibilidad, activo, false), pageable);
        return PageResponse.from(result, mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PageResponse<ProductoResponse> listarPublico(String buscar, UUID categoriaId,
            DisponibilidadProducto disponibilidad, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        var result = repository.findAll(ProductoSpecifications.filtros(buscar, categoriaId, disponibilidad, true, true), pageable);
        return PageResponse.from(result, mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtenerAdmin(UUID id) {
        return mapper.toResponse(getDetail(id));
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtenerPublico(UUID id) {
        return mapper.toResponse(repository.findDetailByIdProductoAndActivoTrueAndCategoriaActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado")));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> relacionados(UUID id) {
        Producto producto = repository.findDetailByIdProductoAndActivoTrueAndCategoriaActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        return repository.findTop4ByCategoriaIdCategoriaAndActivoTrueAndCategoriaActivoTrueAndIdProductoNotOrderByFechaCreacionDesc(
                        producto.getCategoria().getIdCategoria(), id)
                .stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        validarSku(request.codigoSku(), null);
        Categoria categoria = categoriaService.getEntity(request.idCategoria());
        validarPublicacion(request.activo(), categoria, request.imagenPrincipalUrl());
        Producto entity = Producto.builder()
                .categoria(categoria)
                .codigoSku(normalizarSku(request.codigoSku()))
                .nombre(limpiar(request.nombre()))
                .disponibilidad(request.disponibilidad())
                .descripcionBreve(limpiar(request.descripcionBreve()))
                .descripcionCompleta(limpiar(request.descripcionCompleta()))
                .caracteristicas(opcional(request.caracteristicas()))
                .especificacionesTecnicas(opcional(request.especificacionesTecnicas()))
                .imagenPrincipalUrl(opcional(request.imagenPrincipalUrl()))
                .activo(Boolean.TRUE.equals(request.activo()))
                .build();
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public ProductoResponse actualizar(UUID id, ProductoRequest request) {
        Producto entity = getDetail(id);
        validarSku(request.codigoSku(), id);
        Categoria categoria = categoriaService.getEntity(request.idCategoria());
        boolean activoFinal = request.activo() == null ? entity.isActivo() : request.activo();
        validarPublicacion(activoFinal, categoria, request.imagenPrincipalUrl());
        entity.setCategoria(categoria);
        entity.setCodigoSku(normalizarSku(request.codigoSku()));
        entity.setNombre(limpiar(request.nombre()));
        entity.setDisponibilidad(request.disponibilidad());
        entity.setDescripcionBreve(limpiar(request.descripcionBreve()));
        entity.setDescripcionCompleta(limpiar(request.descripcionCompleta()));
        entity.setCaracteristicas(opcional(request.caracteristicas()));
        entity.setEspecificacionesTecnicas(opcional(request.especificacionesTecnicas()));
        entity.setImagenPrincipalUrl(opcional(request.imagenPrincipalUrl()));
        entity.setActivo(activoFinal);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public ProductoResponse cambiarEstado(UUID id, boolean activo) {
        Producto entity = getDetail(id);
        if (activo) validarPublicacion(true, entity.getCategoria(), entity.getImagenPrincipalUrl());
        entity.setActivo(activo);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public ProductoImagenResponse agregarImagen(UUID id, ProductoImagenRequest request) {
        Producto producto = getDetail(id);
        int cantidadActual = producto.getImagenes().size();
        if (cantidadActual >= 3) {
            throw new BusinessException("El producto admite como máximo tres imágenes de detalle");
        }

        int orden = request.orden() == null ? cantidadActual + 1 : request.orden();
        if (orden < 1 || orden > 3) {
            throw new BusinessException("El orden de la imagen debe estar entre 1 y 3");
        }
        boolean ordenOcupado = producto.getImagenes().stream().anyMatch(imagen -> imagen.getOrden() == orden);
        if (ordenOcupado) {
            throw new BusinessException("Ya existe una imagen de detalle con el orden indicado");
        }

        ProductoImagen imagen = ProductoImagen.builder()
                .producto(producto)
                .urlImagen(request.urlImagen().trim())
                .orden(orden)
                .activo(true)
                .build();
        producto.getImagenes().add(imagen);
        repository.saveAndFlush(producto);
        return new ProductoImagenResponse(imagen.getIdImagen(), imagen.getUrlImagen(), imagen.getOrden(), imagen.isActivo());
    }

    @Transactional
    public void retirarImagen(UUID idProducto, UUID idImagen) {
        Producto producto = getDetail(idProducto);
        ProductoImagen imagen = imagenRepository.findByIdImagenAndProductoIdProducto(idImagen, idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada"));
        producto.getImagenes().removeIf(i -> i.getIdImagen().equals(imagen.getIdImagen()));
        var imagenesOrdenadas = producto.getImagenes().stream()
                .sorted(java.util.Comparator.comparingInt(ProductoImagen::getOrden))
                .toList();
        for (int indice = 0; indice < imagenesOrdenadas.size(); indice++) {
            imagenesOrdenadas.get(indice).setOrden(indice + 1);
        }
        repository.saveAndFlush(producto);
    }

    @Transactional
    public List<ProductoImagenResponse> reordenar(UUID idProducto, ProductoImagenOrdenRequest request) {
        Producto producto = getDetail(idProducto);
        if (producto.getImagenes().size() > 3 || request.items().size() > 3) {
            throw new BusinessException("El producto admite como máximo tres imágenes de detalle");
        }

        var ids = request.items().stream().map(ProductoImagenOrdenRequest.Item::idImagen).toList();
        var ordenesSolicitados = request.items().stream().map(ProductoImagenOrdenRequest.Item::orden).toList();

        if (ids.stream().distinct().count() != ids.size()) {
            throw new BusinessException("No se puede repetir una imagen en el reordenamiento");
        }
        if (ordenesSolicitados.stream().distinct().count() != ordenesSolicitados.size()) {
            throw new BusinessException("No se puede repetir un número de orden");
        }

        var idsProducto = producto.getImagenes().stream().map(ProductoImagen::getIdImagen).collect(java.util.stream.Collectors.toSet());
        if (!idsProducto.containsAll(ids)) {
            throw new BusinessException("Una o más imágenes no pertenecen al producto");
        }

        var ordenes = request.items().stream().collect(java.util.stream.Collectors.toMap(
                ProductoImagenOrdenRequest.Item::idImagen,
                ProductoImagenOrdenRequest.Item::orden));
        producto.getImagenes().forEach(img -> {
            Integer orden = ordenes.get(img.getIdImagen());
            if (orden != null) img.setOrden(orden);
        });
        repository.saveAndFlush(producto);
        return producto.getImagenes().stream()
                .sorted(java.util.Comparator.comparingInt(ProductoImagen::getOrden))
                .map(i -> new ProductoImagenResponse(i.getIdImagen(), i.getUrlImagen(), i.getOrden(), i.isActivo()))
                .toList();
    }

    public Producto getDetail(UUID id) {
        return repository.findDetailByIdProducto(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    private void validarSku(String sku, UUID id) {
        boolean exists = id == null ? repository.existsByCodigoSkuIgnoreCase(normalizarSku(sku))
                : repository.existsByCodigoSkuIgnoreCaseAndIdProductoNot(normalizarSku(sku), id);
        if (exists) throw new ConflictException("Ya existe un producto con el SKU indicado");
    }

    private void validarPublicacion(Boolean activo, Categoria categoria, String imagenUrl) {
        if (Boolean.TRUE.equals(activo)) {
            if (!categoria.isActivo()) throw new BusinessException("No se puede publicar con una categoría inactiva");
            if (!StringUtils.hasText(imagenUrl)) throw new BusinessException("La imagen principal es obligatoria para publicar el producto");
        }
    }

    private String normalizarSku(String value) { return value.trim().toUpperCase(); }
    private String limpiar(String value) { return value.trim().replaceAll("\\s+", " "); }
    private String opcional(String value) { return StringUtils.hasText(value) ? value.trim() : null; }
}
