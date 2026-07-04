CREATE TABLE admin_usuario (
    id_admin UUID PRIMARY KEY,
    usuario VARCHAR(80) NOT NULL UNIQUE,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMPTZ NOT NULL,
    fecha_actualizacion TIMESTAMPTZ NULL
);

CREATE TABLE categoria (
    id_categoria UUID PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMPTZ NOT NULL,
    fecha_actualizacion TIMESTAMPTZ NULL
);

CREATE UNIQUE INDEX uk_categoria_nombre_normalizado
    ON categoria (LOWER(REGEXP_REPLACE(BTRIM(nombre), '\s+', ' ', 'g')));
CREATE INDEX idx_categoria_activo_nombre ON categoria (activo, nombre);

CREATE TABLE producto (
    id_producto UUID PRIMARY KEY,
    id_categoria UUID NOT NULL REFERENCES categoria(id_categoria),
    codigo_sku VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    disponibilidad VARCHAR(30) NOT NULL,
    descripcion_breve VARCHAR(250) NOT NULL,
    descripcion_completa TEXT NOT NULL,
    caracteristicas TEXT NULL,
    especificaciones_tecnicas TEXT NULL,
    imagen_principal_url VARCHAR(500) NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMPTZ NOT NULL,
    fecha_actualizacion TIMESTAMPTZ NULL,
    CONSTRAINT ck_producto_disponibilidad CHECK (disponibilidad IN ('DISPONIBLE', 'NO_DISPONIBLE'))
);

CREATE INDEX idx_producto_categoria ON producto (id_categoria);
CREATE INDEX idx_producto_activo_disponibilidad ON producto (activo, disponibilidad);
CREATE INDEX idx_producto_nombre_lower ON producto (LOWER(nombre));

CREATE TABLE producto_imagen (
    id_imagen UUID PRIMARY KEY,
    id_producto UUID NOT NULL REFERENCES producto(id_producto) ON DELETE CASCADE,
    url_imagen VARCHAR(500) NOT NULL,
    orden INTEGER NOT NULL DEFAULT 1,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT ck_producto_imagen_orden CHECK (orden >= 1)
);
CREATE INDEX idx_producto_imagen_producto_orden ON producto_imagen (id_producto, orden);

CREATE TABLE servicio (
    id_servicio UUID PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    proyecto_relacionado VARCHAR(180) NULL,
    descripcion_breve VARCHAR(250) NOT NULL,
    descripcion_completa TEXT NOT NULL,
    imagen_principal_url VARCHAR(500) NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMPTZ NOT NULL,
    fecha_actualizacion TIMESTAMPTZ NULL
);
CREATE INDEX idx_servicio_activo_nombre ON servicio (activo, nombre);

CREATE TABLE servicio_imagen (
    id_imagen UUID PRIMARY KEY,
    id_servicio UUID NOT NULL REFERENCES servicio(id_servicio) ON DELETE CASCADE,
    url_imagen VARCHAR(500) NOT NULL,
    orden INTEGER NOT NULL DEFAULT 1,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT ck_servicio_imagen_orden CHECK (orden >= 1)
);
CREATE INDEX idx_servicio_imagen_servicio_orden ON servicio_imagen (id_servicio, orden);

CREATE TABLE certificacion (
    id_certificacion UUID PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    descripcion TEXT NULL,
    archivo_url VARCHAR(500) NOT NULL,
    tipo_archivo VARCHAR(20) NOT NULL,
    orden INTEGER NOT NULL DEFAULT 1,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMPTZ NOT NULL,
    fecha_actualizacion TIMESTAMPTZ NULL,
    CONSTRAINT ck_certificacion_tipo_archivo CHECK (tipo_archivo IN ('PDF', 'IMAGEN')),
    CONSTRAINT ck_certificacion_orden CHECK (orden >= 1)
);
CREATE INDEX idx_certificacion_activo_orden ON certificacion (activo, orden);

CREATE TABLE configuracion_contacto (
    id_configuracion UUID PRIMARY KEY,
    whatsapp VARCHAR(20) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    direccion VARCHAR(250) NOT NULL,
    horario_atencion VARCHAR(180) NOT NULL,
    ruc VARCHAR(11) NULL,
    fecha_actualizacion TIMESTAMPTZ NOT NULL,
    CONSTRAINT ck_contacto_whatsapp CHECK (whatsapp ~ '^[0-9]{9,15}$'),
    CONSTRAINT ck_contacto_ruc CHECK (ruc IS NULL OR ruc ~ '^[0-9]{11}$')
);
