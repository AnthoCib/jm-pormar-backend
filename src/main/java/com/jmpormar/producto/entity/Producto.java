package com.jmpormar.producto.entity;

import com.jmpormar.categoria.entity.Categoria;
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
@Table(name = "producto")
public class Producto extends BaseAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_producto", nullable = false, updatable = false)
    private UUID idProducto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "codigo_sku", nullable = false, unique = true, length = 50)
    private String codigoSku;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DisponibilidadProducto disponibilidad;

    @Column(name = "descripcion_breve", nullable = false, length = 250)
    private String descripcionBreve;

    @Column(name = "descripcion_completa", nullable = false, columnDefinition = "text")
    private String descripcionCompleta;

    @Column(columnDefinition = "text")
    private String caracteristicas;

    @Column(name = "especificaciones_tecnicas", columnDefinition = "text")
    private String especificacionesTecnicas;

    @Column(name = "imagen_principal_url", length = 500)
    private String imagenPrincipalUrl;

    @Column(nullable = false)
    private boolean activo;

    @Builder.Default
    @OrderBy("orden ASC")
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoImagen> imagenes = new ArrayList<>();
}
