ALTER TABLE public.configuracion_contacto
ADD COLUMN IF NOT EXISTS razon_social VARCHAR(200);

CREATE INDEX IF NOT EXISTS idx_configuracion_contacto_fecha_actualizacion
ON public.configuracion_contacto (fecha_actualizacion DESC);