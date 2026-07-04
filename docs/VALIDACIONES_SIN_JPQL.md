# JM Pormar — Integración sin JPQL y validación completa

## 1. Objetivo del ajuste

El proyecto fue actualizado para cumplir dos decisiones técnicas:

1. El backend no contiene consultas JPQL ni anotaciones `@Query`.
2. Los DTO de entrada y los formularios del frontend aplican las mismas reglas de validación.

Los DTO de respuesta no incorporan anotaciones de Bean Validation porque representan información ya validada y producida por el servidor. La validación se concentra en solicitudes, parámetros, archivos y reglas de negocio.

## 2. Persistencia sin JPQL

### Categorías

Se utilizan métodos derivados de Spring Data JPA:

```java
Page<Categoria> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
boolean existsByNombreIgnoreCase(String nombre);
boolean existsByNombreIgnoreCaseAndIdCategoriaNot(String nombre, UUID idCategoria);
```

El servicio selecciona `findAll(pageable)` cuando no existe búsqueda, evitando parámetros nulos ambiguos y el error `lower(bytea)`.

### Servicios y certificaciones

Se utilizan combinaciones explícitas de métodos derivados:

```java
findByNombreContainingIgnoreCase(...)
findByActivo(...)
findByNombreContainingIgnoreCaseAndActivo(...)
findAll(...)
```

No se envían parámetros nulos a funciones SQL.

### Productos

Los filtros combinables continúan usando `JpaSpecificationExecutor` y Criteria API:

```java
repository.findAll(ProductoSpecifications.filtros(...), pageable);
```

Criteria API no es JPQL. La consulta se construye de forma tipada mediante `CriteriaBuilder` y permite combinar búsqueda, categoría, disponibilidad y estado.

### Verificación

La búsqueda de código se realizó con:

```bash
grep -R "@Query\|createQuery\|nativeQuery" src/main/java
```

Resultado esperado: sin coincidencias.

## 3. Validaciones del backend

### LoginRequest

- Identificador obligatorio.
- Máximo 150 caracteres.
- Contraseña obligatoria.
- Entre 8 y 72 caracteres.

### CategoriaRequest

- Nombre obligatorio.
- Nombre entre 3 y 100 caracteres.
- Descripción con máximo 2000 caracteres.

### ProductoRequest

- Categoría obligatoria.
- SKU obligatorio, entre 2 y 50 caracteres.
- SKU limitado a letras, números, punto, guion, guion bajo y barra.
- Nombre obligatorio, entre 3 y 150 caracteres.
- Disponibilidad obligatoria.
- Descripción breve obligatoria, máximo 250 caracteres.
- Descripción completa obligatoria, máximo 5000 caracteres.
- Características y especificaciones con máximo 4000 caracteres.
- URL de imagen con máximo 500 caracteres y ruta válida de productos.

Además, el servicio valida:

- SKU único.
- Categoría existente.
- Categoría activa al publicar.
- Imagen principal obligatoria al publicar.

### ProductoImagenRequest

- URL obligatoria y válida para productos.
- Orden entre 1 y 100.

### ProductoImagenOrdenRequest

- Lista no vacía.
- Máximo 100 elementos.
- Identificador de imagen obligatorio.
- Orden entre 1 y 100.
- Sin imágenes repetidas.
- Sin órdenes repetidos.
- Todas las imágenes deben pertenecer al producto.

### ServicioRequest

- Nombre obligatorio, entre 3 y 150 caracteres.
- Proyecto relacionado con máximo 180 caracteres.
- Descripción breve obligatoria, máximo 250 caracteres.
- Descripción completa obligatoria, máximo 5000 caracteres.
- URL de imagen válida para servicios.

### ServicioImagenRequest

- URL obligatoria y válida para servicios.
- Orden entre 1 y 3.
- Máximo tres imágenes secundarias por servicio.

### CertificacionRequest

- Nombre obligatorio, entre 3 y 150 caracteres.
- Tipo obligatorio, entre 2 y 100 caracteres.
- Descripción con máximo 2000 caracteres.
- Archivo obligatorio y con ruta válida de certificaciones.
- Tipo de archivo obligatorio.
- Orden entre 1 y 100.
- Máximo global configurable de certificaciones.

### ContactoRequest

- WhatsApp obligatorio, entre 9 y 15 dígitos.
- Correo obligatorio y válido, máximo 150 caracteres.
- Dirección obligatoria, entre 5 y 250 caracteres.
- Horario obligatorio, entre 5 y 180 caracteres.
- RUC vacío o con exactamente 11 dígitos.

### EstadoRequest

- El campo `activo` es obligatorio.

### Parámetros de búsqueda y paginación

- Búsqueda: máximo 100 caracteres.
- Página: mínimo 0.
- Tamaño: entre 1 y 100.
- UUID y enums son validados por conversión de Spring MVC.

### Archivos

- Productos y servicios: JPG, JPEG, PNG o WEBP, máximo 5 MB.
- Certificaciones: PDF, JPG, JPEG, PNG o WEBP, máximo 10 MB.
- Validación de extensión, MIME, contenido básico, nombre seguro y carpeta permitida.

## 4. Respuesta de errores

Los errores de validación retornan campos individuales:

```json
{
  "success": false,
  "message": "Existen datos inválidos",
  "data": {
    "nombre": "El nombre debe tener entre 3 y 150 caracteres",
    "codigoSku": "El SKU solo admite letras, números, punto, guion, guion bajo y barra"
  },
  "timestamp": "2026-07-02T23:30:00-05:00"
}
```

El frontend extrae los mensajes de `data` y los presenta al usuario.

## 5. Validaciones del frontend

Se validan antes de realizar la petición HTTP:

- Login.
- Categorías.
- Productos.
- Servicios.
- Certificaciones.
- Configuración de contacto y footer.
- Formulario público de contacto.
- Formulario público de cotización.
- Formulario de cotización de la página de inicio.
- Imágenes y documentos antes de subirlos.

Los formularios usan `NgForm`, restricciones HTML/Angular e indicadores por campo:

```html
<input
  name="nombre"
  [(ngModel)]="form.nombre"
  #nombre="ngModel"
  required
  minlength="3"
  maxlength="150"
>
```

Cuando el formulario es inválido:

```typescript
formDirective.control.markAllAsTouched();
```

No se envía ninguna petición hasta corregir los campos.

## 6. Archivos principales modificados

### Backend

- Repositorios de categoría, servicio y certificación.
- Servicios de categoría, servicio y certificación.
- Todos los DTO de entrada.
- Controladores con validación de paginación y búsqueda.
- Controladores de archivos.
- `GlobalExceptionHandler`.
- `ValidationPatterns`.
- Pruebas `DtoValidationTest`.

### Frontend

- Formularios administrativos completos.
- Formularios públicos de contacto y cotización.
- Validación previa de archivos.
- Lectura de errores por campo del backend.
- Pruebas TypeScript desactualizadas corregidas.

## 7. Verificación realizada

- Sin usos de `@Query`, `createQuery` o `nativeQuery`.
- 84 archivos Java analizados sin errores sintácticos.
- TypeScript de aplicación compilado con `tsc --noEmit`.
- Plantillas Angular verificadas con Angular Compiler (`ngc`).
- TypeScript de pruebas compilado con `tsc -p tsconfig.spec.json --noEmit`.

El comando `ng build` no pudo ejecutarse en este entorno porque Angular 22.0.5 requiere Node.js 22.22.3 o superior y el entorno disponible usa Node.js 22.16.0. La compilación TypeScript y la comprobación de plantillas sí finalizaron correctamente.
