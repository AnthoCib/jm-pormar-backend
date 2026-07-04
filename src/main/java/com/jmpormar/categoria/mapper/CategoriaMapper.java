package com.jmpormar.categoria.mapper;

import com.jmpormar.categoria.dto.CategoriaResponse;
import com.jmpormar.categoria.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponse toResponse(Categoria c) {
        return new CategoriaResponse(c.getIdCategoria(), c.getNombre(), c.getDescripcion(), c.isActivo(),
                c.getFechaCreacion(), c.getFechaActualizacion());
    }
}
