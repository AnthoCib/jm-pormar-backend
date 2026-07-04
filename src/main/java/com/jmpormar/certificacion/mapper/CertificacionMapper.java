package com.jmpormar.certificacion.mapper;

import com.jmpormar.certificacion.dto.CertificacionResponse;
import com.jmpormar.certificacion.entity.Certificacion;
import org.springframework.stereotype.Component;

@Component
public class CertificacionMapper {
    public CertificacionResponse toResponse(Certificacion c) {
        return new CertificacionResponse(c.getIdCertificacion(), c.getNombre(), c.getTipo(), c.getDescripcion(),
                c.getArchivoUrl(), c.getTipoArchivo(), c.getOrden(), c.isActivo(),
                c.getFechaCreacion(), c.getFechaActualizacion());
    }
}
