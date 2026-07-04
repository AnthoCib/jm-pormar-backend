package com.jmpormar.producto.repository;

import com.jmpormar.producto.entity.ProductoImagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductoImagenRepository extends JpaRepository<ProductoImagen, UUID> {
    Optional<ProductoImagen> findByIdImagenAndProductoIdProducto(UUID idImagen, UUID idProducto);
}
