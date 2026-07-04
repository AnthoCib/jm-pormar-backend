package com.jmpormar.servicio.repository;

import com.jmpormar.servicio.entity.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServicioRepository extends JpaRepository<Servicio, UUID> {
    Page<Servicio> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Page<Servicio> findByActivo(Boolean activo, Pageable pageable);

    Page<Servicio> findByNombreContainingIgnoreCaseAndActivo(String nombre, Boolean activo, Pageable pageable);

    @EntityGraph(attributePaths = "imagenes")
    Optional<Servicio> findDetailByIdServicio(UUID idServicio);

    @EntityGraph(attributePaths = "imagenes")
    List<Servicio> findAllByActivoTrueOrderByNombreAsc();

    long countByActivoTrue();
}
