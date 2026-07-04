package com.jmpormar.contacto.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "configuracion_contacto")
public class ConfiguracionContacto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_configuracion", nullable = false, updatable = false)
    private UUID idConfiguracion;

    @Column(nullable = false, length = 20)
    private String whatsapp;

    @Column(nullable = false, length = 150)
    private String correo;

    @Column(nullable = false, length = 250)
    private String direccion;

    @Column(name = "horario_atencion", nullable = false, length = 180)
    private String horarioAtencion;

    @Column(length = 11)
    private String ruc;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;
}
