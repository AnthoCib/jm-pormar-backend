package com.jmpormar.certificacion.entity;

import com.jmpormar.shared.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "certificacion")
public class Certificacion extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_certificacion", nullable = false, updatable = false)
    private UUID idCertificacion;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(name = "archivo_url", nullable = false, length = 500)
    private String archivoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_archivo", nullable = false, length = 20)
    private TipoArchivoCertificacion tipoArchivo;

    @Column(nullable = false)
    private int orden;

    @Column(nullable = false)
    private boolean activo;
}
