package com.jmpormar.servicio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servicio_imagen")
public class ServicioImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_imagen", nullable = false, updatable = false)
    private UUID idImagen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Column(name = "url_imagen", nullable = false, length = 500)
    private String urlImagen;

    @Column(nullable = false)
    private int orden;

    @Column(nullable = false)
    private boolean activo;
}
