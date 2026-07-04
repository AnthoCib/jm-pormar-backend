package com.jmpormar.producto.repository;

import com.jmpormar.producto.entity.DisponibilidadProducto;
import com.jmpormar.producto.entity.Producto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.UUID;

public final class ProductoSpecifications {
    private ProductoSpecifications() {}

    public static Specification<Producto> filtros(String buscar, UUID categoriaId,
                                                    DisponibilidadProducto disponibilidad,
                                                    Boolean activo, boolean soloCategoriaActiva) {
        return (root, query, cb) -> {
            query.distinct(true);
            var predicates = new java.util.ArrayList<jakarta.persistence.criteria.Predicate>();
            var categoria = root.join("categoria");

            if (StringUtils.hasText(buscar)) {
                String like = "%" + buscar.trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("nombre")), like),
                        cb.like(cb.lower(root.get("codigoSku")), like)
                ));
            }
            if (categoriaId != null) predicates.add(cb.equal(categoria.get("idCategoria"), categoriaId));
            if (disponibilidad != null) predicates.add(cb.equal(root.get("disponibilidad"), disponibilidad));
            if (activo != null) predicates.add(cb.equal(root.get("activo"), activo));
            if (soloCategoriaActiva) predicates.add(cb.isTrue(categoria.get("activo")));
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
