package com.jmpormar.servicio.mapper;

import com.jmpormar.servicio.dto.ServicioImagenResponse;
import com.jmpormar.servicio.dto.ServicioResponse;
import com.jmpormar.servicio.entity.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {
    public ServicioResponse toResponse(Servicio s) {
        var images = s.getImagenes() == null ? java.util.List.<ServicioImagenResponse>of() :
                s.getImagenes().stream().filter(i -> i.isActivo())
                        .map(i -> new ServicioImagenResponse(i.getIdImagen(), i.getUrlImagen(), i.getOrden(), i.isActivo()))
                        .toList();
        return new ServicioResponse(s.getIdServicio(), s.getNombre(), s.getProyectoRelacionado(),
                s.getDescripcionBreve(), s.getDescripcionCompleta(), s.getImagenPrincipalUrl(),
                s.isActivo(), images, s.getFechaCreacion(), s.getFechaActualizacion());
    }
}
