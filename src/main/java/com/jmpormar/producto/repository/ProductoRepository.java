package com.jmpormar.producto.repository;

import com.jmpormar.producto.entity.DisponibilidadProducto;
import com.jmpormar.producto.entity.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID>, JpaSpecificationExecutor<Producto> {
    boolean existsByCodigoSkuIgnoreCase(String codigoSku);
    boolean existsByCodigoSkuIgnoreCaseAndIdProductoNot(String codigoSku, UUID idProducto);
    boolean existsByCategoriaIdCategoria(UUID idCategoria);

    @EntityGraph(attributePaths = {"categoria", "imagenes"})
    Optional<Producto> findDetailByIdProducto(UUID idProducto);

    @EntityGraph(attributePaths = {"categoria", "imagenes"})
    Optional<Producto> findDetailByIdProductoAndActivoTrueAndCategoriaActivoTrue(UUID idProducto);

    @EntityGraph(attributePaths = {"categoria"})
    List<Producto> findTop4ByCategoriaIdCategoriaAndActivoTrueAndCategoriaActivoTrueAndIdProductoNotOrderByFechaCreacionDesc(
            UUID idCategoria, UUID idProducto);

    long countByActivoTrue();
    long countByDisponibilidad(DisponibilidadProducto disponibilidad);
}
