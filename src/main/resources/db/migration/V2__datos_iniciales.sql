-- =============================================================
-- JM PORMAR - DATOS INICIALES PARA BASE DE DATOS NUEVA
-- Compatible con el modelo SIN producto.marca y SIN
-- configuracion_contacto.google_maps_url.
--
-- IMPORTANTE:
-- 1. El administrador NO se inserta aquí. Se crea al iniciar el
--    backend usando ADMIN_USERNAME, ADMIN_EMAIL y ADMIN_PASSWORD.
-- 2. Los productos se crean INACTIVOS porque todavía no cuentan
--    con una imagen principal cargada en /uploads/productos.
-- 3. Las certificaciones no se insertan hasta disponer del PDF o
--    imagen real que se mostrará públicamente.
-- =============================================================

-- -------------------------------------------------------------
-- 1. CATEGORÍAS
-- -------------------------------------------------------------
INSERT INTO categoria (
    id_categoria,
    nombre,
    descripcion,
    activo,
    fecha_creacion,
    fecha_actualizacion
)
VALUES
(
    '10000000-0000-0000-0000-000000000001',
    'Materiales de construcción',
    'Materiales básicos para obras civiles, remodelaciones y mantenimiento.',
    TRUE,
    NOW(),
    NULL
),
(
    '10000000-0000-0000-0000-000000000002',
    'Ferretería',
    'Artículos generales de fijación, unión, sellado y uso industrial.',
    TRUE,
    NOW(),
    NULL
),
(
    '10000000-0000-0000-0000-000000000003',
    'Tuberías y conexiones',
    'Tuberías, accesorios y conexiones para instalaciones sanitarias y de agua.',
    TRUE,
    NOW(),
    NULL
),
(
    '10000000-0000-0000-0000-000000000004',
    'Electricidad',
    'Conductores, protecciones y accesorios para instalaciones eléctricas.',
    TRUE,
    NOW(),
    NULL
),
(
    '10000000-0000-0000-0000-000000000005',
    'Herramientas',
    'Herramientas manuales y eléctricas para obra, mantenimiento e instalación.',
    TRUE,
    NOW(),
    NULL
),
(
    '10000000-0000-0000-0000-000000000006',
    'Equipos de protección personal',
    'Elementos de seguridad para protección de cabeza, manos, ojos y vías respiratorias.',
    TRUE,
    NOW(),
    NULL
)
ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- 2. PRODUCTOS DE PRUEBA
-- Todos se registran con activo = FALSE porque no tienen todavía
-- una imagen principal cargada. Luego de cargar la imagen desde el
-- panel administrativo, se puede actualizar imagen_principal_url y
-- activar el producto.
-- -------------------------------------------------------------
INSERT INTO producto (
    id_producto,
    id_categoria,
    codigo_sku,
    nombre,
    disponibilidad,
    descripcion_breve,
    descripcion_completa,
    caracteristicas,
    especificaciones_tecnicas,
    imagen_principal_url,
    activo,
    fecha_creacion,
    fecha_actualizacion
)
VALUES
(
    '40000000-0000-0000-0000-000000000001',
    '10000000-0000-0000-0000-000000000001',
    'MAT-CEM-001',
    'Cemento Portland Tipo I de 42.5 kg',
    'DISPONIBLE',
    'Cemento de uso general para obras, reparaciones y elementos de concreto.',
    'Producto para preparación de concreto, mortero y trabajos generales de construcción. La disponibilidad y presentación final se confirman al solicitar la cotización.',
    'Uso general; buena trabajabilidad; presentación en bolsa.',
    'Presentación: 42.5 kg; tipo: Portland Tipo I; uso: construcción general.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000002',
    '10000000-0000-0000-0000-000000000001',
    'MAT-YES-001',
    'Yeso para construcción de 25 kg',
    'DISPONIBLE',
    'Material para acabados interiores, resanes y trabajos de albañilería.',
    'Yeso para aplicaciones de construcción y mantenimiento en interiores. Se recomienda verificar las condiciones de almacenamiento y la presentación disponible antes de cotizar.',
    'Aplicación interior; fácil preparación; acabado uniforme.',
    'Presentación referencial: 25 kg; aplicación: resanes y acabados interiores.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000003',
    '10000000-0000-0000-0000-000000000002',
    'FER-TOR-001',
    'Tornillo hexagonal galvanizado de 1/2 x 3 pulgadas',
    'DISPONIBLE',
    'Elemento de fijación galvanizado para uniones metálicas y trabajos generales.',
    'Tornillo hexagonal para aplicaciones de montaje, estructuras y mantenimiento. Puede suministrarse con tuerca y arandela según el requerimiento del cliente.',
    'Acabado galvanizado; cabeza hexagonal; uso industrial y de obra.',
    'Diámetro: 1/2 pulgada; longitud: 3 pulgadas; acabado: galvanizado.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000004',
    '10000000-0000-0000-0000-000000000002',
    'FER-SIL-001',
    'Sellador de silicona de uso general',
    'DISPONIBLE',
    'Sellador para juntas, uniones y trabajos de mantenimiento.',
    'Sellador de silicona para aplicaciones generales en vidrio, aluminio, cerámica y otras superficies compatibles. El color y la presentación se coordinan en la cotización.',
    'Buena adherencia; aplicación con pistola; resistente a humedad.',
    'Presentación referencial: cartucho; uso: sellado general; color: según disponibilidad.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000005',
    '10000000-0000-0000-0000-000000000003',
    'TUB-PVC-001',
    'Tubería PVC SAP de 1/2 pulgada',
    'DISPONIBLE',
    'Tubería rígida para instalaciones de agua y aplicaciones sanitarias compatibles.',
    'Tubería PVC para instalaciones y proyectos de mantenimiento. La clase, longitud y presión requerida deben confirmarse durante la cotización.',
    'Material PVC; fácil instalación; resistente a corrosión.',
    'Diámetro nominal: 1/2 pulgada; material: PVC; longitud: según presentación disponible.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000006',
    '10000000-0000-0000-0000-000000000003',
    'TUB-COD-001',
    'Codo PVC de 90 grados de 1/2 pulgada',
    'DISPONIBLE',
    'Accesorio para cambio de dirección en instalaciones con tubería PVC.',
    'Conexión de PVC para realizar cambios de dirección de noventa grados. Debe verificarse la compatibilidad con el tipo y clase de tubería seleccionada.',
    'Conexión de 90 grados; material PVC; instalación sencilla.',
    'Ángulo: 90 grados; diámetro nominal: 1/2 pulgada; tipo de unión: según modelo.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000007',
    '10000000-0000-0000-0000-000000000004',
    'ELE-CAB-001',
    'Cable eléctrico THW calibre 12 AWG',
    'DISPONIBLE',
    'Conductor eléctrico para instalaciones residenciales, comerciales e industriales.',
    'Cable para circuitos eléctricos de acuerdo con la capacidad y diseño de la instalación. El color, metraje y presentación se coordinan al cotizar.',
    'Aislamiento termoplástico; conductor flexible o sólido según disponibilidad; varios colores.',
    'Calibre: 12 AWG; tipo: THW; presentación: por metro o rollo según disponibilidad.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000008',
    '10000000-0000-0000-0000-000000000004',
    'ELE-ITM-001',
    'Interruptor termomagnético bipolar de 20 A',
    'DISPONIBLE',
    'Dispositivo de protección para circuitos eléctricos de baja tensión.',
    'Interruptor termomagnético para protección ante sobrecargas y cortocircuitos. La curva, capacidad de ruptura y compatibilidad con el tablero deben validarse antes de la compra.',
    'Protección bipolar; montaje en riel; accionamiento manual.',
    'Polos: 2; corriente nominal: 20 A; tensión y capacidad de ruptura: según modelo solicitado.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000009',
    '10000000-0000-0000-0000-000000000005',
    'HER-TAL-001',
    'Taladro percutor de 800 W',
    'DISPONIBLE',
    'Herramienta eléctrica para perforación y trabajos de mantenimiento.',
    'Taladro percutor para perforación en madera, metal y mampostería utilizando el accesorio adecuado. Las características finales dependen del modelo disponible.',
    'Función de percusión; velocidad regulable; giro reversible.',
    'Potencia referencial: 800 W; mandril: 13 mm; alimentación: 220 V.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000010',
    '10000000-0000-0000-0000-000000000005',
    'HER-LLA-001',
    'Juego de llaves combinadas',
    'DISPONIBLE',
    'Conjunto de llaves para montaje, ajuste y mantenimiento mecánico.',
    'Juego de llaves combinadas para trabajos de ajuste y mantenimiento. La cantidad de piezas y el rango de medidas se definirán según el requerimiento.',
    'Extremo abierto y corona; medidas variadas; estuche organizador según presentación.',
    'Sistema de medidas: métrico; cantidad de piezas: según presentación solicitada.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000011',
    '10000000-0000-0000-0000-000000000006',
    'EPP-CAS-001',
    'Casco de seguridad industrial',
    'DISPONIBLE',
    'Protección para la cabeza en obras, almacenes y actividades industriales.',
    'Casco de seguridad para reducir el riesgo de lesiones por impacto. El tipo de suspensión, color y certificación se confirman durante la cotización.',
    'Suspensión interior ajustable; ranuras laterales según modelo; colores variados.',
    'Tipo: casco industrial; ajuste: regulable; certificación: según modelo requerido.',
    NULL,
    FALSE,
    NOW(),
    NULL
),
(
    '40000000-0000-0000-0000-000000000012',
    '10000000-0000-0000-0000-000000000006',
    'EPP-GUA-001',
    'Guantes de protección recubiertos de nitrilo',
    'DISPONIBLE',
    'Guantes para manipulación de materiales y trabajos generales.',
    'Guantes de protección para mejorar el agarre y reducir el contacto directo con superficies de trabajo. La talla y el nivel de protección deben definirse según la actividad.',
    'Palma recubierta; buen agarre; puño tejido; tallas variadas.',
    'Material: tejido con recubrimiento de nitrilo; talla: según requerimiento.',
    NULL,
    FALSE,
    NOW(),
    NULL
)
ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- 3. SERVICIOS
-- Las imágenes de servicios son opcionales, por eso pueden quedar
-- activos desde la carga inicial.
-- -------------------------------------------------------------
INSERT INTO servicio (
    id_servicio,
    nombre,
    proyecto_relacionado,
    descripcion_breve,
    descripcion_completa,
    imagen_principal_url,
    activo,
    fecha_creacion,
    fecha_actualizacion
)
VALUES
(
    '20000000-0000-0000-0000-000000000001',
    'Abastecimiento para obras',
    NULL,
    'Suministro coordinado de materiales, herramientas y equipos para proyectos.',
    'Atendemos requerimientos de abastecimiento para obras, instituciones y empresas, coordinando productos, cantidades, disponibilidad y condiciones de entrega según cada necesidad.',
    NULL,
    TRUE,
    NOW(),
    NULL
),
(
    '20000000-0000-0000-0000-000000000002',
    'Transporte de materiales',
    NULL,
    'Coordinación logística para el traslado y entrega de materiales.',
    'Gestionamos el transporte de materiales y suministros de acuerdo con el volumen, destino y condiciones requeridas por el proyecto.',
    NULL,
    TRUE,
    NOW(),
    NULL
),
(
    '20000000-0000-0000-0000-000000000003',
    'Mantenimiento y servicios generales',
    NULL,
    'Atención de requerimientos de mantenimiento preventivo y correctivo.',
    'Brindamos soluciones de mantenimiento y servicios generales para instalaciones, equipos y espacios de trabajo, previa evaluación del requerimiento.',
    NULL,
    TRUE,
    NOW(),
    NULL
),
(
    '20000000-0000-0000-0000-000000000004',
    'Asesoría técnica comercial',
    NULL,
    'Orientación para seleccionar productos y soluciones adecuadas.',
    'Apoyamos al cliente en la identificación de materiales, herramientas y alternativas técnicas que se ajusten a las características de su proyecto.',
    NULL,
    TRUE,
    NOW(),
    NULL
)
ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- 4. CONFIGURACIÓN GLOBAL DE CONTACTO
-- Estos datos alimentan el footer, la página de contacto y los CTA
-- de WhatsApp. Reemplazar los valores provisionales por los datos
-- reales de la empresa desde el panel administrativo.
-- -------------------------------------------------------------
INSERT INTO configuracion_contacto (
    id_configuracion,
    whatsapp,
    correo,
    direccion,
    horario_atencion,
    ruc,
    fecha_actualizacion
)
VALUES (
    '30000000-0000-0000-0000-000000000001',
    '51999999999',
    'contacto@jmpormar.com',
    'Callao, Perú',
    'Lunes a viernes de 8:00 a 18:00 y sábados de 8:00 a 13:00',
    NULL,
    NOW()
)
ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- 5. CERTIFICACIONES
-- No se insertan registros ficticios porque archivo_url es
-- obligatorio y debe apuntar a un PDF o imagen realmente cargado.
-- -------------------------------------------------------------

-- CONSULTAS DE VERIFICACIÓN
-- SELECT COUNT(*) AS total_categorias FROM categoria;
-- SELECT COUNT(*) AS total_productos FROM producto;
-- SELECT COUNT(*) AS total_servicios FROM servicio;
-- SELECT * FROM configuracion_contacto;
