package com.jmpormar.certificacion.repository;

import com.jmpormar.certificacion.entity.Certificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CertificacionRepository extends JpaRepository<Certificacion, UUID> {
    Page<Certificacion> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Page<Certificacion> findByActivo(Boolean activo, Pageable pageable);

    Page<Certificacion> findByNombreContainingIgnoreCaseAndActivo(String nombre, Boolean activo, Pageable pageable);

    List<Certificacion> findAllByActivoTrueOrderByOrdenAscNombreAsc();

    long countByActivoTrue();
}
