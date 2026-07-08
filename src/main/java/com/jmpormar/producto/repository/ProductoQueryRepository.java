package com.jmpormar.producto.repository;

import com.jmpormar.producto.dto.ProductoAdminResumenResponse;
import com.jmpormar.producto.entity.DisponibilidadProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductoQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public Page<ProductoAdminResumenResponse> listarResumenAdmin(
            String buscar,
            UUID categoriaId,
            DisponibilidadProducto disponibilidad,
            Boolean activo,
            Pageable pageable
    ) {
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
        List<Object> params = new ArrayList<>();

        if (StringUtils.hasText(buscar)) {
            String like = "%" + buscar.trim().toLowerCase() + "%";

            where.append("""
                    AND (
                        LOWER(p.nombre) LIKE ?
                        OR LOWER(p.codigo_sku) LIKE ?
                        OR LOWER(c.nombre) LIKE ?
                    )
                    """);

            params.add(like);
            params.add(like);
            params.add(like);
        }

        if (categoriaId != null) {
            where.append(" AND p.id_categoria = ? ");
            params.add(categoriaId);
        }

        if (disponibilidad != null) {
            where.append(" AND p.disponibilidad = ? ");
            params.add(disponibilidad.name());
        }

        if (activo != null) {
            where.append(" AND p.activo = ? ");
            params.add(activo);
        }

        String sql = """
                SELECT
                    p.id_producto,
                    p.id_categoria,
                    c.nombre AS categoria,
                    p.codigo_sku,
                    p.nombre,
                    p.disponibilidad,
                    p.descripcion_breve,
                    p.imagen_principal_url,
                    p.activo
                FROM producto p
                INNER JOIN categoria c
                    ON c.id_categoria = p.id_categoria
                """ + where + """
                ORDER BY
                    p.fecha_creacion DESC
                LIMIT ? OFFSET ?
                """;

        List<Object> queryParams = new ArrayList<>(params);
        queryParams.add(pageable.getPageSize());
        queryParams.add(pageable.getOffset());

        List<ProductoAdminResumenResponse> content =
                jdbcTemplate.query(
                        sql,
                        queryParams.toArray(),
                        (rs, rowNum) -> new ProductoAdminResumenResponse(
                                rs.getObject("id_producto", UUID.class),
                                rs.getObject("id_categoria", UUID.class),
                                rs.getString("categoria"),
                                rs.getString("codigo_sku"),
                                rs.getString("nombre"),
                                DisponibilidadProducto.valueOf(
                                        rs.getString("disponibilidad")
                                ),
                                rs.getString("descripcion_breve"),
                                rs.getString("imagen_principal_url"),
                                rs.getBoolean("activo")
                        )
                );

        String countSql = """
                SELECT COUNT(*)
                FROM producto p
                INNER JOIN categoria c
                    ON c.id_categoria = p.id_categoria
                """ + where;

        Long total = jdbcTemplate.queryForObject(
                countSql,
                params.toArray(),
                Long.class
        );

        return new PageImpl<>(
                content,
                pageable,
                total == null ? 0 : total
        );
    }
}