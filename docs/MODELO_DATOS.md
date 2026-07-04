# Modelo de datos

## Diagrama entidad-relación

```mermaid
erDiagram
    ADMIN_USUARIO {
        UUID id_admin PK
        VARCHAR usuario UK
        VARCHAR correo UK
        VARCHAR password_hash
        BOOLEAN activo
        TIMESTAMPTZ fecha_creacion
        TIMESTAMPTZ fecha_actualizacion
    }

    CATEGORIA ||--o{ PRODUCTO : clasifica
    PRODUCTO ||--o{ PRODUCTO_IMAGEN : contiene
    SERVICIO ||--o{ SERVICIO_IMAGEN : contiene

    CATEGORIA {
        UUID id_categoria PK
        VARCHAR nombre UK
        TEXT descripcion
        BOOLEAN activo
        TIMESTAMPTZ fecha_creacion
        TIMESTAMPTZ fecha_actualizacion
    }

    PRODUCTO {
        UUID id_producto PK
        UUID id_categoria FK
        VARCHAR codigo_sku UK
        VARCHAR nombre
        VARCHAR disponibilidad
        VARCHAR descripcion_breve
        TEXT descripcion_completa
        TEXT caracteristicas
        TEXT especificaciones_tecnicas
        VARCHAR imagen_principal_url
        BOOLEAN activo
        TIMESTAMPTZ fecha_creacion
        TIMESTAMPTZ fecha_actualizacion
    }

    PRODUCTO_IMAGEN {
        UUID id_imagen PK
        UUID id_producto FK
        VARCHAR url_imagen
        INTEGER orden
        BOOLEAN activo
    }

    SERVICIO {
        UUID id_servicio PK
        VARCHAR nombre
        VARCHAR proyecto_relacionado
        VARCHAR descripcion_breve
        TEXT descripcion_completa
        VARCHAR imagen_principal_url
        BOOLEAN activo
        TIMESTAMPTZ fecha_creacion
        TIMESTAMPTZ fecha_actualizacion
    }

    SERVICIO_IMAGEN {
        UUID id_imagen PK
        UUID id_servicio FK
        VARCHAR url_imagen
        INTEGER orden
        BOOLEAN activo
    }

    CERTIFICACION {
        UUID id_certificacion PK
        VARCHAR nombre
        VARCHAR tipo
        TEXT descripcion
        VARCHAR archivo_url
        VARCHAR tipo_archivo
        INTEGER orden
        BOOLEAN activo
        TIMESTAMPTZ fecha_creacion
        TIMESTAMPTZ fecha_actualizacion
    }

    CONFIGURACION_CONTACTO {
        UUID id_configuracion PK
        VARCHAR whatsapp
        VARCHAR correo
        VARCHAR direccion
        VARCHAR horario_atencion
        VARCHAR ruc
        TIMESTAMPTZ fecha_actualizacion
    }
```

## Relaciones

| Origen | Relación | Destino | Regla |
|---|---:|---|---|
| `categoria` | 1:N | `producto` | Un producto pertenece a una categoría obligatoria. |
| `producto` | 1:N | `producto_imagen` | Las imágenes secundarias se eliminan en cascada con el producto a nivel de BD. |
| `servicio` | 1:N | `servicio_imagen` | Máximo funcional de tres imágenes adicionales. |

## Reglas persistentes

- `producto.codigo_sku` es único.
- `categoria.nombre` es único ignorando mayúsculas, espacios extremos y espacios repetidos.
- `producto.disponibilidad` solo permite `DISPONIBLE` o `NO_DISPONIBLE`.
- `certificacion.tipo_archivo` solo permite `PDF` o `IMAGEN`.
- Los campos `orden` deben ser mayores o iguales a uno.
- WhatsApp contiene entre 9 y 15 dígitos.
- RUC, cuando existe, contiene 11 dígitos.
- El contenido se desactiva lógicamente; la API no ofrece borrado físico de categorías, productos, servicios o certificaciones.

## Índices

- Categoría: estado y nombre.
- Producto: categoría, estado/disponibilidad, nombre y SKU único.
- Galerías: entidad padre y orden.
- Servicio: estado y nombre.
- Certificación: estado y orden.

## Uso de `configuracion_contacto`

Esta tabla contiene la información global que se muestra en el footer, la página de contacto y los botones de cotización por WhatsApp. No almacena una URL de Google Maps. El frontend consulta un único registro y reutiliza sus datos en todas esas secciones.

## Decisión sobre características y especificaciones

En esta versión se almacenan como `TEXT`, permitiendo saltos de línea o contenido estructurado simple. Si posteriormente se necesitan filtros técnicos por atributo, se recomienda migrar a:

```text
producto_especificacion
- id_especificacion UUID PK
- id_producto UUID FK
- nombre VARCHAR(100)
- valor VARCHAR(250)
- unidad VARCHAR(30)
- orden INTEGER
```

No se agrega en la primera versión para mantener el alcance del catálogo y evitar complejidad innecesaria.
