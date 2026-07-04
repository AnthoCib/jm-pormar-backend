package com.jmpormar.contacto.service;

import com.jmpormar.contacto.dto.*;
import com.jmpormar.contacto.entity.ConfiguracionContacto;
import com.jmpormar.contacto.repository.ConfiguracionContactoRepository;
import com.jmpormar.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ContactoService {
    private final ConfiguracionContactoRepository repository;

    @Transactional(readOnly = true)
    public ContactoResponse obtener() {
        return repository.findAll().stream().findFirst().map(this::toResponse)
                .orElseThrow(() -> new BusinessException("La configuración de contacto aún no ha sido registrada"));
    }

    @Transactional
    public ContactoResponse guardar(ContactoRequest r) {
        ConfiguracionContacto c = repository.findAll().stream().findFirst().orElseGet(ConfiguracionContacto::new);
        c.setWhatsapp(r.whatsapp().trim());
        c.setCorreo(r.correo().trim().toLowerCase());
        c.setDireccion(r.direccion().trim());
        c.setHorarioAtencion(r.horarioAtencion().trim());
        c.setRuc(StringUtils.hasText(r.ruc()) ? r.ruc().trim() : null);
        return toResponse(repository.save(c));
    }

    @Transactional(readOnly = true)
    public boolean configurado() { return repository.count() == 1; }

    private ContactoResponse toResponse(ConfiguracionContacto c) {
        return new ContactoResponse(c.getIdConfiguracion(), c.getWhatsapp(), c.getCorreo(), c.getDireccion(),
                c.getHorarioAtencion(), c.getRuc(),c.getRazonSocial(), c.getFechaActualizacion());
    }
}
