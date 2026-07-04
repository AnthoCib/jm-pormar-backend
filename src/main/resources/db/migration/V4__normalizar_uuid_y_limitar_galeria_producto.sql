-- Convierte los UUID de demostración legibles de V2 a UUID v4 reales.
-- También limita la galería a 3 imágenes adicionales por producto:
-- 1 imagen principal + hasta 3 imágenes de detalle = 4 imágenes totales.

ALTER TABLE producto DROP CONSTRAINT IF EXISTS producto_id_categoria_fkey;
ALTER TABLE producto_imagen DROP CONSTRAINT IF EXISTS producto_imagen_id_producto_fkey;
ALTER TABLE servicio_imagen DROP CONSTRAINT IF EXISTS servicio_imagen_id_servicio_fkey;

-- Categorías demo.
UPDATE categoria
SET id_categoria = CASE id_categoria
    WHEN '10000000-0000-0000-0000-000000000001'::uuid THEN 'f2f2854d-3617-42f6-8063-8fad79ca03c3'::uuid
    WHEN '10000000-0000-0000-0000-000000000002'::uuid THEN '5fbaaad4-8d15-4578-a89e-d30d6bf29398'::uuid
    WHEN '10000000-0000-0000-0000-000000000003'::uuid THEN '90c4d422-c3fc-4e01-85e9-99de8b154a13'::uuid
    WHEN '10000000-0000-0000-0000-000000000004'::uuid THEN '2b4c6a8b-65f8-423f-bb72-5836043087b1'::uuid
    WHEN '10000000-0000-0000-0000-000000000005'::uuid THEN '77dbd7e7-c7c8-422e-aa2a-46d24b439af6'::uuid
    WHEN '10000000-0000-0000-0000-000000000006'::uuid THEN '64ab9864-eab5-4974-a8fb-299d5bfe956f'::uuid
    ELSE id_categoria
END
WHERE id_categoria::text LIKE '10000000-0000-0000-0000-%';

UPDATE producto
SET id_categoria = CASE id_categoria
    WHEN '10000000-0000-0000-0000-000000000001'::uuid THEN 'f2f2854d-3617-42f6-8063-8fad79ca03c3'::uuid
    WHEN '10000000-0000-0000-0000-000000000002'::uuid THEN '5fbaaad4-8d15-4578-a89e-d30d6bf29398'::uuid
    WHEN '10000000-0000-0000-0000-000000000003'::uuid THEN '90c4d422-c3fc-4e01-85e9-99de8b154a13'::uuid
    WHEN '10000000-0000-0000-0000-000000000004'::uuid THEN '2b4c6a8b-65f8-423f-bb72-5836043087b1'::uuid
    WHEN '10000000-0000-0000-0000-000000000005'::uuid THEN '77dbd7e7-c7c8-422e-aa2a-46d24b439af6'::uuid
    WHEN '10000000-0000-0000-0000-000000000006'::uuid THEN '64ab9864-eab5-4974-a8fb-299d5bfe956f'::uuid
    ELSE id_categoria
END
WHERE id_categoria::text LIKE '10000000-0000-0000-0000-%';

-- Productos demo.
UPDATE producto
SET id_producto = CASE id_producto
    WHEN '40000000-0000-0000-0000-000000000001'::uuid THEN '759413de-ad21-4122-af22-f697fa4fccdb'::uuid
    WHEN '40000000-0000-0000-0000-000000000002'::uuid THEN '7ddcfa56-e04c-469c-93fd-2d02df3492b7'::uuid
    WHEN '40000000-0000-0000-0000-000000000003'::uuid THEN '9993a71c-a57d-4407-af7e-2828f737d606'::uuid
    WHEN '40000000-0000-0000-0000-000000000004'::uuid THEN 'd663191e-a5fe-4262-a596-69b88747ed81'::uuid
    WHEN '40000000-0000-0000-0000-000000000005'::uuid THEN '21d432a5-1631-40d8-8aef-4a92e934a651'::uuid
    WHEN '40000000-0000-0000-0000-000000000006'::uuid THEN '0e76739a-d93a-4f30-98d1-89cad84007e0'::uuid
    WHEN '40000000-0000-0000-0000-000000000007'::uuid THEN '87b2a896-8d6a-44f5-b8fb-b89a8d1c8b86'::uuid
    WHEN '40000000-0000-0000-0000-000000000008'::uuid THEN '8c82f182-af53-483e-9fe4-594a22213709'::uuid
    WHEN '40000000-0000-0000-0000-000000000009'::uuid THEN '7310bbf4-1bd8-4ac0-afbd-e44c7cfda651'::uuid
    WHEN '40000000-0000-0000-0000-000000000010'::uuid THEN '9479edb5-c1db-46f1-8478-40d83e26a75b'::uuid
    WHEN '40000000-0000-0000-0000-000000000011'::uuid THEN '4e1af70b-a1f6-439c-967a-401f976e00ec'::uuid
    WHEN '40000000-0000-0000-0000-000000000012'::uuid THEN '058f45a5-eefb-4d70-b44a-49b686904f93'::uuid
    ELSE id_producto
END
WHERE id_producto::text LIKE '40000000-0000-0000-0000-%';

UPDATE producto_imagen
SET id_producto = CASE id_producto
    WHEN '40000000-0000-0000-0000-000000000001'::uuid THEN '759413de-ad21-4122-af22-f697fa4fccdb'::uuid
    WHEN '40000000-0000-0000-0000-000000000002'::uuid THEN '7ddcfa56-e04c-469c-93fd-2d02df3492b7'::uuid
    WHEN '40000000-0000-0000-0000-000000000003'::uuid THEN '9993a71c-a57d-4407-af7e-2828f737d606'::uuid
    WHEN '40000000-0000-0000-0000-000000000004'::uuid THEN 'd663191e-a5fe-4262-a596-69b88747ed81'::uuid
    WHEN '40000000-0000-0000-0000-000000000005'::uuid THEN '21d432a5-1631-40d8-8aef-4a92e934a651'::uuid
    WHEN '40000000-0000-0000-0000-000000000006'::uuid THEN '0e76739a-d93a-4f30-98d1-89cad84007e0'::uuid
    WHEN '40000000-0000-0000-0000-000000000007'::uuid THEN '87b2a896-8d6a-44f5-b8fb-b89a8d1c8b86'::uuid
    WHEN '40000000-0000-0000-0000-000000000008'::uuid THEN '8c82f182-af53-483e-9fe4-594a22213709'::uuid
    WHEN '40000000-0000-0000-0000-000000000009'::uuid THEN '7310bbf4-1bd8-4ac0-afbd-e44c7cfda651'::uuid
    WHEN '40000000-0000-0000-0000-000000000010'::uuid THEN '9479edb5-c1db-46f1-8478-40d83e26a75b'::uuid
    WHEN '40000000-0000-0000-0000-000000000011'::uuid THEN '4e1af70b-a1f6-439c-967a-401f976e00ec'::uuid
    WHEN '40000000-0000-0000-0000-000000000012'::uuid THEN '058f45a5-eefb-4d70-b44a-49b686904f93'::uuid
    ELSE id_producto
END
WHERE id_producto::text LIKE '40000000-0000-0000-0000-%';

-- Servicios demo.
UPDATE servicio
SET id_servicio = CASE id_servicio
    WHEN '20000000-0000-0000-0000-000000000001'::uuid THEN '33b983f5-4739-4f08-b26e-bad93e6627e5'::uuid
    WHEN '20000000-0000-0000-0000-000000000002'::uuid THEN 'fb460047-1785-4986-a808-e2938bfd5d10'::uuid
    WHEN '20000000-0000-0000-0000-000000000003'::uuid THEN '9b3cdca7-6656-43c6-b2e8-fcb146cd18be'::uuid
    WHEN '20000000-0000-0000-0000-000000000004'::uuid THEN 'd040a0e0-38c9-4c15-bc1d-c6874b7b825a'::uuid
    ELSE id_servicio
END
WHERE id_servicio::text LIKE '20000000-0000-0000-0000-%';

UPDATE servicio_imagen
SET id_servicio = CASE id_servicio
    WHEN '20000000-0000-0000-0000-000000000001'::uuid THEN '33b983f5-4739-4f08-b26e-bad93e6627e5'::uuid
    WHEN '20000000-0000-0000-0000-000000000002'::uuid THEN 'fb460047-1785-4986-a808-e2938bfd5d10'::uuid
    WHEN '20000000-0000-0000-0000-000000000003'::uuid THEN '9b3cdca7-6656-43c6-b2e8-fcb146cd18be'::uuid
    WHEN '20000000-0000-0000-0000-000000000004'::uuid THEN 'd040a0e0-38c9-4c15-bc1d-c6874b7b825a'::uuid
    ELSE id_servicio
END
WHERE id_servicio::text LIKE '20000000-0000-0000-0000-%';

UPDATE configuracion_contacto
SET id_configuracion = '8a53ef5f-a93a-4e78-ac7b-70c8a4ec8cb3'::uuid
WHERE id_configuracion = '30000000-0000-0000-0000-000000000001'::uuid;

ALTER TABLE producto
    ADD CONSTRAINT producto_id_categoria_fkey
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria);
ALTER TABLE producto_imagen
    ADD CONSTRAINT producto_imagen_id_producto_fkey
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE;
ALTER TABLE servicio_imagen
    ADD CONSTRAINT servicio_imagen_id_servicio_fkey
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio) ON DELETE CASCADE;

-- Normaliza y limita imágenes adicionales a tres por producto.
WITH ranked AS (
    SELECT id_imagen,
           ROW_NUMBER() OVER (PARTITION BY id_producto ORDER BY orden, id_imagen) AS posicion
    FROM producto_imagen
)
DELETE FROM producto_imagen pi
USING ranked r
WHERE pi.id_imagen = r.id_imagen
  AND r.posicion > 3;

WITH ranked AS (
    SELECT id_imagen,
           ROW_NUMBER() OVER (PARTITION BY id_producto ORDER BY orden, id_imagen) AS posicion
    FROM producto_imagen
)
UPDATE producto_imagen pi
SET orden = r.posicion
FROM ranked r
WHERE pi.id_imagen = r.id_imagen;

ALTER TABLE producto_imagen DROP CONSTRAINT IF EXISTS ck_producto_imagen_orden;
ALTER TABLE producto_imagen
    ADD CONSTRAINT ck_producto_imagen_orden CHECK (orden BETWEEN 1 AND 3);
