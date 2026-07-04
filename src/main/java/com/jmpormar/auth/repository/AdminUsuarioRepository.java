package com.jmpormar.auth.repository;

import com.jmpormar.auth.entity.AdminUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminUsuarioRepository extends JpaRepository<AdminUsuario, UUID> {
    Optional<AdminUsuario> findByUsuarioIgnoreCaseOrCorreoIgnoreCase(String usuario, String correo);
    boolean existsByUsuarioIgnoreCase(String usuario);
    boolean existsByCorreoIgnoreCase(String correo);
}
