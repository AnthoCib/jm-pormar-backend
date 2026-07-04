# JM Pormar Backend

Backend REST para la web corporativa, catálogo y panel administrador de **Inversiones JM Pormar Bienes y Servicios E.I.R.L.**

## Stack

- Java 21
- Spring Boot 3
- Spring Web, Security, Validation y Data JPA
- JWT + BCrypt
- PostgreSQL 16
- Flyway
- Springdoc OpenAPI / Swagger
- Docker y Docker Compose

## Módulos

```text
com.jmpormar
├── auth             # login, sesión, JWT, seguridad y administrador inicial
├── dashboard        # indicadores del panel
├── categoria        # CRUD y listado público
├── producto         # CRUD, filtros, galería y relacionados
├── servicio         # CRUD, imágenes y listado público
├── certificacion    # CRUD, límite configurable y visor
├── contacto         # configuración global singleton
├── archivo          # carga y lectura controlada de imágenes/PDF
├── config           # CORS y OpenAPI
├── exception        # manejo centralizado de errores
└── shared           # respuestas, paginación, auditoría y DTO comunes
```

La estructura detallada está en [`docs/ESTRUCTURA_PROYECTO.md`](docs/ESTRUCTURA_PROYECTO.md) y el modelo relacional en [`docs/MODELO_DATOS.md`](docs/MODELO_DATOS.md).

## Inicio con Docker

1. Copia las variables:

```bash
cp .env.example .env
```

2. Cambia como mínimo:

```env
POSTGRES_PASSWORD=una_clave_segura
JWT_SECRET=una_clave_base64_larga_y_segura
ADMIN_PASSWORD=una_clave_segura
```

3. Levanta PostgreSQL y backend:

```bash
docker compose up -d --build
```

4. Revisa:

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Salud: `http://localhost:8080/actuator/health`

El administrador inicial se crea únicamente cuando la tabla `admin_usuario` está vacía y `ADMIN_PASSWORD` contiene un valor.

## Inicio local

Requisitos: Java 21, Maven y PostgreSQL 16.

Crea la base y usuario:

```sql
CREATE USER jm_pormar WITH PASSWORD 'jm_pormar123';
CREATE DATABASE jm_pormar_db OWNER jm_pormar;
```

Configura las variables y ejecuta:

```bash
export ADMIN_PASSWORD='Admin123*'
export JWT_SECRET='change-this-development-secret-with-at-least-32-characters'
mvn spring-boot:run
```

En Windows PowerShell:

```powershell
$env:ADMIN_PASSWORD='Admin123*'
$env:JWT_SECRET='change-this-development-secret-with-at-least-32-characters'
mvn spring-boot:run
```

## Flujo para registrar un producto

1. Inicia sesión en `POST /api/auth/login`.
2. Carga la imagen principal en `POST /api/admin/archivos/productos` usando `multipart/form-data`, campo `file`.
3. Usa la propiedad `url` devuelta como `imagenPrincipalUrl`.
4. Registra el producto en `POST /api/admin/productos`.
5. Para imágenes de detalle, carga archivos y luego registra cada URL en `POST /api/admin/productos/{id}/imagenes`.
6. Se permiten como máximo tres imágenes de detalle; junto con la principal son cuatro imágenes por producto.

Ejemplo de producto:

```json
{
  "idCategoria": "5fbaaad4-8d15-4578-a89e-d30d6bf29398",
  "codigoSku": "FER-001",
  "nombre": "Taladro percutor 13 mm",
  "disponibilidad": "DISPONIBLE",
  "descripcionBreve": "Taladro para trabajos de obra y mantenimiento.",
  "descripcionCompleta": "Equipo orientado a perforación y trabajos generales.",
  "caracteristicas": "Potencia: 800 W\nMandril: 13 mm",
  "especificacionesTecnicas": "Voltaje: 220 V",
  "imagenPrincipalUrl": "/api/files/productos/archivo-generado.webp",
  "activo": true
}
```

## Ajuste del modelo de datos

La versión actual crea directamente el esquema sin `marca` en productos y sin `google_maps_url` en la configuración de contacto. Para una base nueva, Flyway ejecuta únicamente `V1__crear_esquema_inicial.sql` y `V2__datos_iniciales.sql`.

## Respuesta estándar

```json
{
  "success": true,
  "message": "Operación realizada correctamente",
  "data": {},
  "timestamp": "2026-07-02T18:00:00-05:00"
}
```

## Seguridad

- `/api/public/**` y `/api/files/**`: acceso público.
- `/api/admin/**`: requiere `Authorization: Bearer <token>`.
- `/api/auth/me` y `/api/auth/logout`: requieren token.
- No existe CRUD de administradores en la versión 1.
- El cierre de sesión es stateless: el frontend elimina el JWT.

## Archivos

- Productos y servicios: JPG, PNG o WEBP, máximo 5 MB.
- Certificaciones: PDF, JPG, PNG o WEBP, máximo 10 MB.
- Los nombres se reemplazan por UUID.
- En Docker se persisten en el volumen `jm_pormar_uploads`.

## Decisiones implementadas

- La certificación tiene un límite configurable por `CERTIFICATIONS_MAX_RECORDS`.
- El valor por defecto limita a cinco **registros totales**, activos o inactivos.
- Un producto puede permanecer como borrador sin imagen si `activo=false`.
- Para activar un producto, su categoría debe estar activa y debe tener imagen principal.
- No hay endpoints DELETE para contenido de negocio; se utiliza desactivación lógica.

## Migraciones

- `V1__crear_esquema_inicial.sql`: tablas, claves, checks e índices.
- `V2__datos_iniciales.sql`: categorías, servicios y contacto demostrativo.
- El administrador se inicializa desde variables de entorno porque su contraseña debe almacenarse con BCrypt.

## Pruebas manuales

Puedes usar [`requests/jm-pormar-api.http`](requests/jm-pormar-api.http) desde IntelliJ o VS Code con REST Client.

## Consultas sin JPQL y validaciones

Este backend no contiene anotaciones `@Query` ni consultas JPQL. Los filtros simples usan métodos derivados de Spring Data y los filtros combinables de productos utilizan Criteria API con `JpaSpecificationExecutor`.

Todos los DTO de entrada, parámetros de paginación, filtros y rutas de archivos están validados. Consulta `docs/VALIDACIONES_SIN_JPQL.md`.
