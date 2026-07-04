-- =====================================================================
-- V5__insertar_datos_actuales.sql
-- JM PORMAR
--
-- Generado a partir de datos_actuales.sql (PostgreSQL 18.2).
--
-- Objetivo:
--   Sincronizar los datos actuales de categorías, productos, imágenes,
--   servicios, certificaciones y configuración de contacto después de V4.
--
-- Características:
--   * Compatible con Flyway.
--   * No inserta flyway_schema_history.
--   * No contiene comandos exclusivos de psql (\restrict, SET, etc.).
--   * Es idempotente respecto de las claves primarias: usa UPSERT.
--   * No inserta admin_usuario; el administrador debe seguir siendo creado
--     mediante ADMIN_USERNAME, ADMIN_EMAIL y ADMIN_PASSWORD.
--
-- IMPORTANTE:
--   Las rutas /api/files/... solo guardan referencias. También debes copiar
--   los archivos físicos de productos, servicios y certificaciones al
--   directorio de uploads configurado en el backend.
-- =====================================================================

SET search_path TO public;


-- ---------------------------------------------------------------------
-- 1. CATEGORÍAS
-- ---------------------------------------------------------------------

INSERT INTO public.categoria (id_categoria, nombre, descripcion, activo, fecha_creacion, fecha_actualizacion) VALUES ('f2f2854d-3617-42f6-8063-8fad79ca03c3', 'Materiales de construcción', 'Materiales básicos para obras civiles, remodelaciones y mantenimiento.', true, '2026-07-03 17:14:07.000956-05', NULL)
ON CONFLICT (id_categoria) DO UPDATE
SET nombre = EXCLUDED.nombre,
    descripcion = EXCLUDED.descripcion,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.categoria (id_categoria, nombre, descripcion, activo, fecha_creacion, fecha_actualizacion) VALUES ('5fbaaad4-8d15-4578-a89e-d30d6bf29398', 'Ferretería', 'Artículos generales de fijación, unión, sellado y uso industrial.', true, '2026-07-03 17:14:07.000956-05', NULL)
ON CONFLICT (id_categoria) DO UPDATE
SET nombre = EXCLUDED.nombre,
    descripcion = EXCLUDED.descripcion,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.categoria (id_categoria, nombre, descripcion, activo, fecha_creacion, fecha_actualizacion) VALUES ('90c4d422-c3fc-4e01-85e9-99de8b154a13', 'Tuberías y conexiones', 'Tuberías, accesorios y conexiones para instalaciones sanitarias y de agua.', true, '2026-07-03 17:14:07.000956-05', NULL)
ON CONFLICT (id_categoria) DO UPDATE
SET nombre = EXCLUDED.nombre,
    descripcion = EXCLUDED.descripcion,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.categoria (id_categoria, nombre, descripcion, activo, fecha_creacion, fecha_actualizacion) VALUES ('77dbd7e7-c7c8-422e-aa2a-46d24b439af6', 'Herramientas', 'Herramientas manuales y eléctricas para obra, mantenimiento e instalación.', true, '2026-07-03 17:14:07.000956-05', NULL)
ON CONFLICT (id_categoria) DO UPDATE
SET nombre = EXCLUDED.nombre,
    descripcion = EXCLUDED.descripcion,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.categoria (id_categoria, nombre, descripcion, activo, fecha_creacion, fecha_actualizacion) VALUES ('64ab9864-eab5-4974-a8fb-299d5bfe956f', 'Equipos de protección personal', 'Elementos de seguridad para protección de cabeza, manos, ojos y vías respiratorias.', true, '2026-07-03 17:14:07.000956-05', NULL)
ON CONFLICT (id_categoria) DO UPDATE
SET nombre = EXCLUDED.nombre,
    descripcion = EXCLUDED.descripcion,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.categoria (id_categoria, nombre, descripcion, activo, fecha_creacion, fecha_actualizacion) VALUES ('2b4c6a8b-65f8-423f-bb72-5836043087b1', 'Electricidad', 'Conductores, protecciones y accesorios para instalaciones eléctricas...', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 18:39:34.353082-05')
ON CONFLICT (id_categoria) DO UPDATE
SET nombre = EXCLUDED.nombre,
    descripcion = EXCLUDED.descripcion,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;


-- ---------------------------------------------------------------------
-- 2. PRODUCTOS
-- ---------------------------------------------------------------------

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('759413de-ad21-4122-af22-f697fa4fccdb', 'f2f2854d-3617-42f6-8063-8fad79ca03c3', 'MAT-CEM-001', 'Cemento Portland Tipo I de 42.5 kg', 'DISPONIBLE', 'Cemento de uso general para obras, reparaciones y elementos de concreto.', 'Producto para preparación de concreto, mortero y trabajos generales de construcción. La disponibilidad y presentación final se confirman al solicitar la cotización.', 'Uso general; buena trabajabilidad; presentación en bolsa.', 'Presentación: 42.5 kg; tipo: Portland Tipo I; uso: construcción general.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('7ddcfa56-e04c-469c-93fd-2d02df3492b7', 'f2f2854d-3617-42f6-8063-8fad79ca03c3', 'MAT-YES-001', 'Yeso para construcción de 25 kg', 'DISPONIBLE', 'Material para acabados interiores, resanes y trabajos de albañilería.', 'Yeso para aplicaciones de construcción y mantenimiento en interiores. Se recomienda verificar las condiciones de almacenamiento y la presentación disponible antes de cotizar.', 'Aplicación interior; fácil preparación; acabado uniforme.', 'Presentación referencial: 25 kg; aplicación: resanes y acabados interiores.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('9993a71c-a57d-4407-af7e-2828f737d606', '5fbaaad4-8d15-4578-a89e-d30d6bf29398', 'FER-TOR-001', 'Tornillo hexagonal galvanizado de 1/2 x 3 pulgadas', 'DISPONIBLE', 'Elemento de fijación galvanizado para uniones metálicas y trabajos generales.', 'Tornillo hexagonal para aplicaciones de montaje, estructuras y mantenimiento. Puede suministrarse con tuerca y arandela según el requerimiento del cliente.', 'Acabado galvanizado; cabeza hexagonal; uso industrial y de obra.', 'Diámetro: 1/2 pulgada; longitud: 3 pulgadas; acabado: galvanizado.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('d663191e-a5fe-4262-a596-69b88747ed81', '5fbaaad4-8d15-4578-a89e-d30d6bf29398', 'FER-SIL-001', 'Sellador de silicona de uso general', 'DISPONIBLE', 'Sellador para juntas, uniones y trabajos de mantenimiento.', 'Sellador de silicona para aplicaciones generales en vidrio, aluminio, cerámica y otras superficies compatibles. El color y la presentación se coordinan en la cotización.', 'Buena adherencia; aplicación con pistola; resistente a humedad.', 'Presentación referencial: cartucho; uso: sellado general; color: según disponibilidad.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('21d432a5-1631-40d8-8aef-4a92e934a651', '90c4d422-c3fc-4e01-85e9-99de8b154a13', 'TUB-PVC-001', 'Tubería PVC SAP de 1/2 pulgada', 'DISPONIBLE', 'Tubería rígida para instalaciones de agua y aplicaciones sanitarias compatibles.', 'Tubería PVC para instalaciones y proyectos de mantenimiento. La clase, longitud y presión requerida deben confirmarse durante la cotización.', 'Material PVC; fácil instalación; resistente a corrosión.', 'Diámetro nominal: 1/2 pulgada; material: PVC; longitud: según presentación disponible.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('0e76739a-d93a-4f30-98d1-89cad84007e0', '90c4d422-c3fc-4e01-85e9-99de8b154a13', 'TUB-COD-001', 'Codo PVC de 90 grados de 1/2 pulgada', 'DISPONIBLE', 'Accesorio para cambio de dirección en instalaciones con tubería PVC.', 'Conexión de PVC para realizar cambios de dirección de noventa grados. Debe verificarse la compatibilidad con el tipo y clase de tubería seleccionada.', 'Conexión de 90 grados; material PVC; instalación sencilla.', 'Ángulo: 90 grados; diámetro nominal: 1/2 pulgada; tipo de unión: según modelo.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('87b2a896-8d6a-44f5-b8fb-b89a8d1c8b86', '2b4c6a8b-65f8-423f-bb72-5836043087b1', 'ELE-CAB-001', 'Cable eléctrico THW calibre 12 AWG', 'DISPONIBLE', 'Conductor eléctrico para instalaciones residenciales, comerciales e industriales.', 'Cable para circuitos eléctricos de acuerdo con la capacidad y diseño de la instalación. El color, metraje y presentación se coordinan al cotizar.', 'Aislamiento termoplástico; conductor flexible o sólido según disponibilidad; varios colores.', 'Calibre: 12 AWG; tipo: THW; presentación: por metro o rollo según disponibilidad.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('8c82f182-af53-483e-9fe4-594a22213709', '2b4c6a8b-65f8-423f-bb72-5836043087b1', 'ELE-ITM-001', 'Interruptor termomagnético bipolar de 20 A', 'DISPONIBLE', 'Dispositivo de protección para circuitos eléctricos de baja tensión.', 'Interruptor termomagnético para protección ante sobrecargas y cortocircuitos. La curva, capacidad de ruptura y compatibilidad con el tablero deben validarse antes de la compra.', 'Protección bipolar; montaje en riel; accionamiento manual.', 'Polos: 2; corriente nominal: 20 A; tensión y capacidad de ruptura: según modelo solicitado.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('7310bbf4-1bd8-4ac0-afbd-e44c7cfda651', '77dbd7e7-c7c8-422e-aa2a-46d24b439af6', 'HER-TAL-001', 'Taladro percutor de 800 W', 'DISPONIBLE', 'Herramienta eléctrica para perforación y trabajos de mantenimiento.', 'Taladro percutor para perforación en madera, metal y mampostería utilizando el accesorio adecuado. Las características finales dependen del modelo disponible.', 'Función de percusión; velocidad regulable; giro reversible.', 'Potencia referencial: 800 W; mandril: 13 mm; alimentación: 220 V.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('9479edb5-c1db-46f1-8478-40d83e26a75b', '77dbd7e7-c7c8-422e-aa2a-46d24b439af6', 'HER-LLA-001', 'Juego de llaves combinadas', 'DISPONIBLE', 'Conjunto de llaves para montaje, ajuste y mantenimiento mecánico.', 'Juego de llaves combinadas para trabajos de ajuste y mantenimiento. La cantidad de piezas y el rango de medidas se definirán según el requerimiento.', 'Extremo abierto y corona; medidas variadas; estuche organizador según presentación.', 'Sistema de medidas: métrico; cantidad de piezas: según presentación solicitada.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('4e1af70b-a1f6-439c-967a-401f976e00ec', '64ab9864-eab5-4974-a8fb-299d5bfe956f', 'EPP-CAS-001', 'Casco de seguridad industrial', 'DISPONIBLE', 'Protección para la cabeza en obras, almacenes y actividades industriales.', 'Casco de seguridad para reducir el riesgo de lesiones por impacto. El tipo de suspensión, color y certificación se confirman durante la cotización.', 'Suspensión interior ajustable; ranuras laterales según modelo; colores variados.', 'Tipo: casco industrial; ajuste: regulable; certificación: según modelo requerido.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('058f45a5-eefb-4d70-b44a-49b686904f93', '64ab9864-eab5-4974-a8fb-299d5bfe956f', 'EPP-GUA-001', 'Guantes de protección recubiertos de nitrilo', 'DISPONIBLE', 'Guantes para manipulación de materiales y trabajos generales.', 'Guantes de protección para mejorar el agarre y reducir el contacto directo con superficies de trabajo. La talla y el nivel de protección deben definirse según la actividad.', 'Palma recubierta; buen agarre; puño tejido; tallas variadas.', 'Material: tejido con recubrimiento de nitrilo; talla: según requerimiento.', '/api/files/productos/demo-producto.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 17:14:07.019009-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.producto (id_producto, id_categoria, codigo_sku, nombre, disponibilidad, descripcion_breve, descripcion_completa, caracteristicas, especificaciones_tecnicas, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('0a4ea880-9ba8-4e23-a046-26a25f38c3f5', 'f2f2854d-3617-42f6-8063-8fad79ca03c3', 'EJEM-EXP-001', 'PRUEBA', 'DISPONIBLE', 'texto corto', 'texto completo texto completo texto completo texto completo texto completo texto completo texto completo texto completo', 'Caracteristicas..', 'Especificaciones tecnicas', '/api/files/productos/95f551d8-0af0-4878-9193-f62e6e1f109c.jpeg', true, '2026-07-03 18:12:58.588592-05', '2026-07-03 18:13:20.106168-05')
ON CONFLICT (id_producto) DO UPDATE
SET id_categoria = EXCLUDED.id_categoria,
    codigo_sku = EXCLUDED.codigo_sku,
    nombre = EXCLUDED.nombre,
    disponibilidad = EXCLUDED.disponibilidad,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    caracteristicas = EXCLUDED.caracteristicas,
    especificaciones_tecnicas = EXCLUDED.especificaciones_tecnicas,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;


-- ---------------------------------------------------------------------
-- 3. IMÁGENES ADICIONALES DE PRODUCTOS
-- ---------------------------------------------------------------------

INSERT INTO public.producto_imagen (id_imagen, id_producto, url_imagen, orden, activo) VALUES ('0567fb9f-a2e1-4363-9e8e-fdf7b907db1d', '058f45a5-eefb-4d70-b44a-49b686904f93', '/api/files/productos/52a74e8a-3075-4348-a31f-638bfa54894a.jpeg', 1, true)
ON CONFLICT (id_imagen) DO UPDATE
SET id_producto = EXCLUDED.id_producto,
    url_imagen = EXCLUDED.url_imagen,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo;

INSERT INTO public.producto_imagen (id_imagen, id_producto, url_imagen, orden, activo) VALUES ('ba738a0e-9ff7-4931-b5b8-dc2ca7c895b1', '058f45a5-eefb-4d70-b44a-49b686904f93', '/api/files/productos/79243489-1b57-4ea4-8b5d-0ccd2bbc75e7.jpeg', 2, true)
ON CONFLICT (id_imagen) DO UPDATE
SET id_producto = EXCLUDED.id_producto,
    url_imagen = EXCLUDED.url_imagen,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo;

INSERT INTO public.producto_imagen (id_imagen, id_producto, url_imagen, orden, activo) VALUES ('04b7eea8-ee73-4ed5-8a0c-5f63c8c68391', '0a4ea880-9ba8-4e23-a046-26a25f38c3f5', '/api/files/productos/d7d74bed-d7fb-4b85-9226-60d488664e3e.jpeg', 1, true)
ON CONFLICT (id_imagen) DO UPDATE
SET id_producto = EXCLUDED.id_producto,
    url_imagen = EXCLUDED.url_imagen,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo;

INSERT INTO public.producto_imagen (id_imagen, id_producto, url_imagen, orden, activo) VALUES ('4f93799a-3875-4f42-87b7-a2bbcdf72653', '0a4ea880-9ba8-4e23-a046-26a25f38c3f5', '/api/files/productos/17bf216f-774e-46e1-84e5-e23a8e7ebaf9.jpeg', 2, true)
ON CONFLICT (id_imagen) DO UPDATE
SET id_producto = EXCLUDED.id_producto,
    url_imagen = EXCLUDED.url_imagen,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo;

INSERT INTO public.producto_imagen (id_imagen, id_producto, url_imagen, orden, activo) VALUES ('e3f30b17-c93c-4923-b2ae-face08894b83', '0a4ea880-9ba8-4e23-a046-26a25f38c3f5', '/api/files/productos/8cf26091-9953-4705-9520-38af2e8f1465.jpeg', 3, true)
ON CONFLICT (id_imagen) DO UPDATE
SET id_producto = EXCLUDED.id_producto,
    url_imagen = EXCLUDED.url_imagen,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo;


-- ---------------------------------------------------------------------
-- 4. SERVICIOS
-- ---------------------------------------------------------------------

INSERT INTO public.servicio (id_servicio, nombre, proyecto_relacionado, descripcion_breve, descripcion_completa, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('33b983f5-4739-4f08-b26e-bad93e6627e5', 'Abastecimiento para obras', 'Obras', 'Suministro coordinado de materiales, herramientas y equipos para proyectos.', 'Atendemos requerimientos de abastecimiento para obras, instituciones y empresas, coordinando productos, cantidades, disponibilidad y condiciones de entrega según cada necesidad..', '/api/files/servicios/0b255baa-365c-477e-9896-6d8767a53368.png', true, '2026-07-03 17:14:07.000956-05', '2026-07-03 19:35:54.733341-05')
ON CONFLICT (id_servicio) DO UPDATE
SET nombre = EXCLUDED.nombre,
    proyecto_relacionado = EXCLUDED.proyecto_relacionado,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.servicio (id_servicio, nombre, proyecto_relacionado, descripcion_breve, descripcion_completa, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('d040a0e0-38c9-4c15-bc1d-c6874b7b825a', 'Asesoría técnica comercial', 'Instituciones', 'Orientación para seleccionar productos y soluciones adecuadas.', 'Apoyamos al cliente en la identificación de materiales, herramientas y alternativas técnicas que se ajusten a las características de su proyecto.', NULL, true, '2026-07-03 17:14:07.000956-05', '2026-07-03 19:36:17.048894-05')
ON CONFLICT (id_servicio) DO UPDATE
SET nombre = EXCLUDED.nombre,
    proyecto_relacionado = EXCLUDED.proyecto_relacionado,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.servicio (id_servicio, nombre, proyecto_relacionado, descripcion_breve, descripcion_completa, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('9b3cdca7-6656-43c6-b2e8-fcb146cd18be', 'Mantenimiento y servicios generales', 'Mantenimiento de Alcantarillas', 'Atención de requerimientos de mantenimiento preventivo y correctivo.', 'Brindamos soluciones de mantenimiento y servicios generales para instalaciones, equipos y espacios de trabajo, previa evaluación del requerimiento.', NULL, true, '2026-07-03 17:14:07.000956-05', '2026-07-03 19:36:32.928489-05')
ON CONFLICT (id_servicio) DO UPDATE
SET nombre = EXCLUDED.nombre,
    proyecto_relacionado = EXCLUDED.proyecto_relacionado,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.servicio (id_servicio, nombre, proyecto_relacionado, descripcion_breve, descripcion_completa, imagen_principal_url, activo, fecha_creacion, fecha_actualizacion) VALUES ('fb460047-1785-4986-a808-e2938bfd5d10', 'Transporte de materiales', 'Transporte de carga', 'Coordinación logística para el traslado y entrega de materiales.', 'Gestionamos el transporte de materiales y suministros de acuerdo con el volumen, destino y condiciones requeridas por el proyecto.', NULL, true, '2026-07-03 17:14:07.000956-05', '2026-07-03 19:36:55.913968-05')
ON CONFLICT (id_servicio) DO UPDATE
SET nombre = EXCLUDED.nombre,
    proyecto_relacionado = EXCLUDED.proyecto_relacionado,
    descripcion_breve = EXCLUDED.descripcion_breve,
    descripcion_completa = EXCLUDED.descripcion_completa,
    imagen_principal_url = EXCLUDED.imagen_principal_url,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;


-- ---------------------------------------------------------------------
-- 5. CERTIFICACIONES
-- ---------------------------------------------------------------------

INSERT INTO public.certificacion (id_certificacion, nombre, tipo, descripcion, archivo_url, tipo_archivo, orden, activo, fecha_creacion, fecha_actualizacion) VALUES ('687db4bd-1fce-4e3f-98da-25b74f6d352b', 'ISO 9001', 'Sistema de Gestion de Calidad', 'Certificado de Registro 
INVERSIONES JM PORMAR BIENES Y 
SERVICIOS E.I.R.L.', '/api/files/certificaciones/6dc3abc9-8c37-4ae5-a739-f229b7236f91.pdf', 'PDF', 1, true, '2026-07-03 18:42:28.753086-05', '2026-07-03 21:00:34.871328-05')
ON CONFLICT (id_certificacion) DO UPDATE
SET nombre = EXCLUDED.nombre,
    tipo = EXCLUDED.tipo,
    descripcion = EXCLUDED.descripcion,
    archivo_url = EXCLUDED.archivo_url,
    tipo_archivo = EXCLUDED.tipo_archivo,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.certificacion (id_certificacion, nombre, tipo, descripcion, archivo_url, tipo_archivo, orden, activo, fecha_creacion, fecha_actualizacion) VALUES ('0977bc22-c57c-402c-b208-1e932a862eec', 'ISO 37001', 'Sistema de Gestión Antisoborno', 'Certificado de Registro 
INVERSIONES JM PORMAR BIENES Y 
SERVICIOS E.I.R.L.', '/api/files/certificaciones/ba366e09-1bea-4d46-9ea0-7c323f47dbaf.pdf', 'PDF', 1, true, '2026-07-03 21:01:36.976622-05', '2026-07-03 21:01:36.976622-05')
ON CONFLICT (id_certificacion) DO UPDATE
SET nombre = EXCLUDED.nombre,
    tipo = EXCLUDED.tipo,
    descripcion = EXCLUDED.descripcion,
    archivo_url = EXCLUDED.archivo_url,
    tipo_archivo = EXCLUDED.tipo_archivo,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.certificacion (id_certificacion, nombre, tipo, descripcion, archivo_url, tipo_archivo, orden, activo, fecha_creacion, fecha_actualizacion) VALUES ('24b38d6a-99b6-480c-be51-6f0ecabb3f13', 'ISO 14001', 'Sistema de Gestión Ambiental', 'Certificado de Registro
INVERSIONES JM PORMAR BIENES Y 
SERVICIOS E.I.R.L.', '/api/files/certificaciones/4d42b499-5bd7-49db-bf56-75248353c25d.pdf', 'PDF', 1, true, '2026-07-03 21:02:22.917658-05', '2026-07-03 21:02:22.917658-05')
ON CONFLICT (id_certificacion) DO UPDATE
SET nombre = EXCLUDED.nombre,
    tipo = EXCLUDED.tipo,
    descripcion = EXCLUDED.descripcion,
    archivo_url = EXCLUDED.archivo_url,
    tipo_archivo = EXCLUDED.tipo_archivo,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;

INSERT INTO public.certificacion (id_certificacion, nombre, tipo, descripcion, archivo_url, tipo_archivo, orden, activo, fecha_creacion, fecha_actualizacion) VALUES ('d72a9af7-2162-4004-9fbc-8645451f979e', 'BUENAS PRACTICAS LABORALES', 'BPL', 'Certificado de Cumplimiento:

INVERSIONES JM PORMAR BIENES Y 
SERVICIOS E.I.R.L', '/api/files/certificaciones/33c16486-0105-4f6a-b13e-59e547391c19.pdf', 'PDF', 1, true, '2026-07-03 21:04:50.859527-05', '2026-07-03 21:04:50.859527-05')
ON CONFLICT (id_certificacion) DO UPDATE
SET nombre = EXCLUDED.nombre,
    tipo = EXCLUDED.tipo,
    descripcion = EXCLUDED.descripcion,
    archivo_url = EXCLUDED.archivo_url,
    tipo_archivo = EXCLUDED.tipo_archivo,
    orden = EXCLUDED.orden,
    activo = EXCLUDED.activo,
    fecha_creacion = EXCLUDED.fecha_creacion,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;


-- ---------------------------------------------------------------------
-- 6. CONFIGURACIÓN DE CONTACTO
-- ---------------------------------------------------------------------

INSERT INTO public.configuracion_contacto (id_configuracion, whatsapp, correo, direccion, horario_atencion, ruc, fecha_actualizacion) VALUES ('8a53ef5f-a93a-4e78-ac7b-70c8a4ec8cb3', '51949180383', 'master@jmpormar.com', 'Narnia, Espejo derecho', 'Lunes a viernes de 8:00 a 18:00 y sábados de 8:00 a 13:00', '10949180383', '2026-07-03 19:17:37.458347-05')
ON CONFLICT (id_configuracion) DO UPDATE
SET whatsapp = EXCLUDED.whatsapp,
    correo = EXCLUDED.correo,
    direccion = EXCLUDED.direccion,
    horario_atencion = EXCLUDED.horario_atencion,
    ruc = EXCLUDED.ruc,
    fecha_actualizacion = EXCLUDED.fecha_actualizacion;


-- ---------------------------------------------------------------------
-- 7. VERIFICACIÓN OPCIONAL
-- ---------------------------------------------------------------------
-- Estas consultas quedan comentadas para no interferir con Flyway.
--
-- SELECT COUNT(*) AS total_categorias FROM categoria;
-- SELECT COUNT(*) AS total_productos FROM producto;
-- SELECT COUNT(*) AS total_imagenes_producto FROM producto_imagen;
-- SELECT COUNT(*) AS total_servicios FROM servicio;
-- SELECT COUNT(*) AS total_certificaciones FROM certificacion;
-- SELECT COUNT(*) AS total_configuracion_contacto FROM configuracion_contacto;
