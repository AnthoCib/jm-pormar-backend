package com.jmpormar.producto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto_imagen")
public class ProductoImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_imagen", nullable = false, updatable = false)
    private UUID idImagen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "url_imagen", nullable = false, length = 500)
    private String urlImagen;

    @Column(nullable = false)
    private int orden;

    @Column(nullable = false)
    private boolean activo;
}
