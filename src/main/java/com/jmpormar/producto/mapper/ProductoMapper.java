package com.jmpormar.producto.mapper;

import com.jmpormar.producto.dto.ProductoImagenResponse;
import com.jmpormar.producto.dto.ProductoResponse;
import com.jmpormar.producto.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoResponse toResponse(Producto p) {
        var imagenes = p.getImagenes() == null ? java.util.List.<ProductoImagenResponse>of()
                : p.getImagenes().stream()
                    .filter(i -> i.isActivo())
                    .map(i -> new ProductoImagenResponse(i.getIdImagen(), i.getUrlImagen(), i.getOrden(), i.isActivo()))
                    .toList();
        return new ProductoResponse(
                p.getIdProducto(), p.getCategoria().getIdCategoria(), p.getCategoria().getNombre(),
                p.getCodigoSku(), p.getNombre(), p.getDisponibilidad(),
                p.getDescripcionBreve(), p.getDescripcionCompleta(), p.getCaracteristicas(),
                p.getEspecificacionesTecnicas(), p.getImagenPrincipalUrl(), p.isActivo(), imagenes,
                p.getFechaCreacion(), p.getFechaActualizacion());
    }
}
