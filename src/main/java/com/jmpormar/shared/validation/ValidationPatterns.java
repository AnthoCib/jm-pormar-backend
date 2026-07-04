package com.jmpormar.shared.validation;

public final class ValidationPatterns {
    private ValidationPatterns() {
    }

    public static final String SKU = "^[A-Za-z0-9._/-]+$";
    public static final String WHATSAPP = "^[0-9]{9,15}$";
    public static final String RUC_OPTIONAL = "^$|^[0-9]{11}$";
    public static final String PRODUCT_IMAGE_URL = "^$|^(?:/api/files/productos/[A-Za-z0-9._-]+|https?://\\S+)$";
    public static final String SERVICE_IMAGE_URL = "^$|^(?:/api/files/servicios/[A-Za-z0-9._-]+|https?://\\S+)$";
    public static final String CERTIFICATION_FILE_URL = "^(?:/api/files/certificaciones/[A-Za-z0-9._-]+|https?://\\S+)$";
}
