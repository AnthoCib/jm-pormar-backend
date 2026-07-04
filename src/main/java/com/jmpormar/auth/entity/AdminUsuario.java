package com.jmpormar.auth.entity;

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
@Table(name = "admin_usuario")
public class AdminUsuario extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_admin", nullable = false, updatable = false)
    private UUID idAdmin;

    @Column(nullable = false, unique = true, length = 80)
    private String usuario;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false)
    private boolean activo;
}
