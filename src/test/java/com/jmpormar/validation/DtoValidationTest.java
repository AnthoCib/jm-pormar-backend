package com.jmpormar.validation;

import com.jmpormar.auth.dto.LoginRequest;
import com.jmpormar.categoria.dto.CategoriaRequest;
import com.jmpormar.certificacion.dto.CertificacionRequest;
import com.jmpormar.certificacion.entity.TipoArchivoCertificacion;
import com.jmpormar.contacto.dto.ContactoRequest;
import com.jmpormar.producto.dto.ProductoImagenOrdenRequest;
import com.jmpormar.producto.dto.ProductoImagenRequest;
import com.jmpormar.producto.dto.ProductoRequest;
import com.jmpormar.producto.entity.DisponibilidadProducto;
import com.jmpormar.servicio.dto.ServicioImagenRequest;
import com.jmpormar.servicio.dto.ServicioRequest;
import com.jmpormar.shared.dto.EstadoRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void loginRequestRechazaCamposVaciosYPasswordCorto() {
        assertThat(validator.validate(new LoginRequest("", "123"))).isNotEmpty();
    }

    @Test
    void categoriaRequestRechazaNombreCortoYDescripcionExtensa() {
        assertThat(validator.validate(new CategoriaRequest("ab", "x".repeat(2001), true))).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void productoRequestRechazaSkuInvalidoYCamposObligatorios() {
        ProductoRequest request = new ProductoRequest(
                null,
                "SKU CON ESPACIO",
                "ab",
                null,
                "",
                "",
                "x".repeat(4001),
                "x".repeat(4001),
                "archivo-local.jpg",
                true
        );
        assertThat(validator.validate(request)).hasSizeGreaterThanOrEqualTo(8);
    }

    @Test
    void productoImagenRequestRechazaUrlYOrdenInvalidos() {
        assertThat(validator.validate(new ProductoImagenRequest("servicios/imagen.jpg", 0))).hasSizeGreaterThanOrEqualTo(2);
        assertThat(validator.validate(new ProductoImagenRequest("/api/files/productos/imagen.jpg", 4))).isNotEmpty();
    }

    @Test
    void productoImagenOrdenRequestValidaElementosAnidados() {
        var item = new ProductoImagenOrdenRequest.Item(null, 0);
        assertThat(validator.validate(new ProductoImagenOrdenRequest(List.of(item)))).hasSizeGreaterThanOrEqualTo(2);

        var cuatroImagenes = List.of(
                new ProductoImagenOrdenRequest.Item(UUID.randomUUID(), 1),
                new ProductoImagenOrdenRequest.Item(UUID.randomUUID(), 2),
                new ProductoImagenOrdenRequest.Item(UUID.randomUUID(), 3),
                new ProductoImagenOrdenRequest.Item(UUID.randomUUID(), 3)
        );
        assertThat(validator.validate(new ProductoImagenOrdenRequest(cuatroImagenes))).isNotEmpty();
    }

    @Test
    void servicioRequestRechazaLongitudesYUrlInvalidas() {
        ServicioRequest request = new ServicioRequest(
                "ab",
                "x".repeat(181),
                "",
                "",
                "productos/imagen.jpg",
                true
        );
        assertThat(validator.validate(request)).hasSizeGreaterThanOrEqualTo(5);
    }

    @Test
    void servicioImagenRequestRechazaUrlYOrdenInvalidos() {
        assertThat(validator.validate(new ServicioImagenRequest("/api/files/productos/x.jpg", 4))).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void certificacionRequestRechazaArchivoYOrdenInvalidos() {
        CertificacionRequest request = new CertificacionRequest(
                "ab",
                "x",
                "x".repeat(2001),
                "/api/files/productos/documento.pdf",
                null,
                0,
                true
        );
        assertThat(validator.validate(request)).hasSizeGreaterThanOrEqualTo(6);
    }

    @Test
    void contactoRequestRechazaFormatosInvalidos() {
        ContactoRequest request = new ContactoRequest(
                "+51 999",
                "correo-invalido",
                "abc",
                "123",
                "123"
        );
        assertThat(validator.validate(request)).hasSizeGreaterThanOrEqualTo(5);
    }

    @Test
    void estadoRequestRequiereValorBooleano() {
        assertThat(validator.validate(new EstadoRequest(null))).isNotEmpty();
    }

    @Test
    void contratosValidosNoGeneranErrores() {
        UUID categoriaId = UUID.randomUUID();
        UUID imagenId = UUID.randomUUID();

        assertThat(validator.validate(new LoginRequest("admin", "Admin2026"))).isEmpty();
        assertThat(validator.validate(new CategoriaRequest("Ferretería", "Descripción", true))).isEmpty();
        assertThat(validator.validate(new ProductoRequest(
                categoriaId,
                "FER-001",
                "Taladro percutor",
                DisponibilidadProducto.DISPONIBLE,
                "Descripción breve",
                "Descripción completa",
                "Potencia: 800 W",
                "Voltaje: 220 V",
                "/api/files/productos/imagen.webp",
                true
        ))).isEmpty();
        assertThat(validator.validate(new ProductoImagenRequest(
                "/api/files/productos/galeria.webp", 1))).isEmpty();
        assertThat(validator.validate(new ProductoImagenOrdenRequest(
                List.of(new ProductoImagenOrdenRequest.Item(imagenId, 1))))).isEmpty();
        assertThat(validator.validate(new ServicioRequest(
                "Abastecimiento para obras",
                "Proyecto institucional",
                "Descripción breve",
                "Descripción completa",
                "/api/files/servicios/servicio.webp",
                true
        ))).isEmpty();
        assertThat(validator.validate(new ServicioImagenRequest(
                "/api/files/servicios/galeria.webp", 1))).isEmpty();
        assertThat(validator.validate(new CertificacionRequest(
                "Certificación de calidad",
                "Calidad",
                "Documento institucional",
                "/api/files/certificaciones/certificado.pdf",
                TipoArchivoCertificacion.PDF,
                1,
                true
        ))).isEmpty();
        assertThat(validator.validate(new ContactoRequest(
                "51999999999",
                "contacto@jmpormar.com",
                "Callao, Perú",
                "Lunes a viernes de 8:00 a 18:00",
                "20612345678"
        ))).isEmpty();
        assertThat(validator.validate(new EstadoRequest(true))).isEmpty();
    }
}
