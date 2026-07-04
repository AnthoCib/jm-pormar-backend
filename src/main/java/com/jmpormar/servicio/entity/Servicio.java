package com.jmpormar.servicio.entity;

import com.jmpormar.shared.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servicio")
public class Servicio extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_servicio", nullable = false, updatable = false)
    private UUID idServicio;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(name = "proyecto_relacionado", length = 180)
    private String proyectoRelacionado;

    @Column(name = "descripcion_breve", nullable = false, length = 250)
    private String descripcionBreve;

    @Column(name = "descripcion_completa", nullable = false, columnDefinition = "text")
    private String descripcionCompleta;

    @Column(name = "imagen_principal_url", length = 500)
    private String imagenPrincipalUrl;

    @Column(nullable = false)
    private boolean activo;

    @Builder.Default
    @OrderBy("orden ASC")
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicioImagen> imagenes = new ArrayList<>();
}
