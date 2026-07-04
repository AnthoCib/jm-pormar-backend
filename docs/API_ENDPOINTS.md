# Endpoints API

## Autenticación

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/auth/login` | Público | Inicia sesión con usuario/correo y contraseña. |
| GET | `/api/auth/me` | ADMIN | Devuelve la sesión actual. |
| POST | `/api/auth/logout` | ADMIN | Confirma cierre de sesión stateless. |

## Dashboard

| Método | Ruta | Acceso |
|---|---|---|
| GET | `/api/admin/dashboard` | ADMIN |

## Categorías

| Método | Ruta | Acceso |
|---|---|---|
| GET | `/api/public/categorias` | Público |
| GET | `/api/admin/categorias` | ADMIN |
| GET | `/api/admin/categorias/{id}` | ADMIN |
| POST | `/api/admin/categorias` | ADMIN |
| PUT | `/api/admin/categorias/{id}` | ADMIN |
| PATCH | `/api/admin/categorias/{id}/estado` | ADMIN |

## Productos

| Método | Ruta | Acceso |
|---|---|---|
| GET | `/api/public/productos` | Público |
| GET | `/api/public/productos/{id}` | Público |
| GET | `/api/public/productos/{id}/relacionados` | Público |
| GET | `/api/admin/productos` | ADMIN |
| GET | `/api/admin/productos/{id}` | ADMIN |
| POST | `/api/admin/productos` | ADMIN |
| PUT | `/api/admin/productos/{id}` | ADMIN |
| PATCH | `/api/admin/productos/{id}/estado` | ADMIN |
| POST | `/api/admin/productos/{id}/imagenes` | ADMIN |
| DELETE | `/api/admin/productos/{id}/imagenes/{idImagen}` | ADMIN |
| PATCH | `/api/admin/productos/{id}/imagenes/orden` | ADMIN |

Filtros disponibles: `buscar`, `categoriaId`, `disponibilidad`, `activo`, `page` y `size` según el endpoint.

## Servicios

| Método | Ruta | Acceso |
|---|---|---|
| GET | `/api/public/servicios` | Público |
| GET | `/api/admin/servicios` | ADMIN |
| GET | `/api/admin/servicios/{id}` | ADMIN |
| POST | `/api/admin/servicios` | ADMIN |
| PUT | `/api/admin/servicios/{id}` | ADMIN |
| PATCH | `/api/admin/servicios/{id}/estado` | ADMIN |
| POST | `/api/admin/servicios/{id}/imagenes` | ADMIN |
| DELETE | `/api/admin/servicios/{id}/imagenes/{idImagen}` | ADMIN |

## Certificaciones

| Método | Ruta | Acceso |
|---|---|---|
| GET | `/api/public/certificaciones` | Público |
| GET | `/api/public/certificaciones/{id}/ver` | Público |
| GET | `/api/admin/certificaciones` | ADMIN |
| GET | `/api/admin/certificaciones/{id}` | ADMIN |
| POST | `/api/admin/certificaciones` | ADMIN |
| PUT | `/api/admin/certificaciones/{id}` | ADMIN |
| PATCH | `/api/admin/certificaciones/{id}/estado` | ADMIN |

## Contacto

| Método | Ruta | Acceso |
|---|---|---|
| GET | `/api/public/contacto` | Público |
| GET | `/api/admin/contacto` | ADMIN |
| PUT | `/api/admin/contacto` | ADMIN |

## Archivos

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/admin/archivos/{carpeta}` | ADMIN | Carga multipart en `productos`, `servicios` o `certificaciones`. |
| GET | `/api/files/{carpeta}/{filename}` | Público | Visualiza el archivo con disposición inline. |
