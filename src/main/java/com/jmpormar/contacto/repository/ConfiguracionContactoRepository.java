package com.jmpormar.contacto.repository;

import com.jmpormar.contacto.entity.ConfiguracionContacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfiguracionContactoRepository extends JpaRepository<ConfiguracionContacto, UUID> {
}
