package com.jmpormar.archivo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.jmpormar.archivo.dto.ArchivoResponse;
import com.jmpormar.exception.ArchivoStorageException;
import com.jmpormar.exception.FileStorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class ArchivoService {

    private static final Set<String> CARPETAS =
            Set.of(
                    "productos",
                    "servicios",
                    "certificaciones"
            );

    private static final Set<String> IMAGENES =
            Set.of(
                    "jpg",
                    "jpeg",
                    "png",
                    "webp"
            );

    private static final long MAX_IMAGEN_BYTES =
            5L * 1024L * 1024L;

    private static final long MAX_DOCUMENTO_BYTES =
            10L * 1024L * 1024L;

    private final Path root;
    private final Cloudinary cloudinary;
    private final String cloudinaryRootFolder;

    public ArchivoService(
            Cloudinary cloudinary,

            @Value("${app.upload.dir:uploads}")
            String uploadDir,

            @Value("${app.cloudinary.root-folder:jm-pormar}")
            String cloudinaryRootFolder
    ) {
        this.cloudinary = cloudinary;

        this.root = Paths
                .get(uploadDir)
                .toAbsolutePath()
                .normalize();

        this.cloudinaryRootFolder =
                normalizarRootFolder(
                        cloudinaryRootFolder
                );
    }

    /**
     * Se conserva para que continúen funcionando
     * las imágenes antiguas almacenadas localmente.
     */
    @PostConstruct
    void init() {
        try {
            Files.createDirectories(root);

            for (String carpeta : CARPETAS) {
                Files.createDirectories(
                        root.resolve(carpeta)
                );
            }

        } catch (IOException exception) {
            throw new FileStorageException(
                    "No se pudo inicializar el directorio de archivos.",
                    exception
            );
        }
    }

    /**
     * Las nuevas cargas se almacenan en Cloudinary.
     */
    public ArchivoResponse guardar(
            String carpeta,
            MultipartFile file
    ) {
        String carpetaNormalizada =
                normalizarCarpeta(carpeta);

        validarArchivo(
                carpetaNormalizada,
                file
        );

        String original =
                obtenerNombreOriginal(file);

        String extension =
                obtenerExtension(original);

        validarContenido(
                carpetaNormalizada,
                extension,
                file
        );

        boolean esPdf =
                "pdf".equals(extension);

        String resourceType =
                esPdf
                        ? "raw"
                        : "image";

        String identificador =
                UUID.randomUUID().toString();

        /*
         * Los recursos RAW necesitan conservar la
         * extensión dentro del public_id.
         */
        String publicIdSolicitado =
                esPdf
                        ? identificador + ".pdf"
                        : identificador;

        String carpetaCloudinary =
                cloudinaryRootFolder +
                "/" +
                carpetaNormalizada;

        try {
            Map<?, ?> resultado =
                    cloudinary
                            .uploader()
                            .upload(
                                    file.getBytes(),
                                    ObjectUtils.asMap(
                                            "folder",
                                            carpetaCloudinary,

                                            "public_id",
                                            publicIdSolicitado,

                                            "resource_type",
                                            resourceType,

                                            "overwrite",
                                            false,

                                            "unique_filename",
                                            false
                                    )
                            );

            String secureUrl =
                    obtenerTexto(
                            resultado,
                            "secure_url"
                    );

            String publicId =
                    obtenerTexto(
                            resultado,
                            "public_id"
                    );

            String tipoRecurso =
                    obtenerTextoPredeterminado(
                            resultado,
                            "resource_type",
                            resourceType
                    );

            String formato =
                    obtenerTextoPredeterminado(
                            resultado,
                            "format",
                            extension
                    );

            long bytes =
                    obtenerLong(
                            resultado,
                            "bytes",
                            file.getSize()
                    );

            if (!StringUtils.hasText(secureUrl)) {
                throw new ArchivoStorageException(
                        "Cloudinary no devolvió la URL del archivo."
                );
            }

            if (!StringUtils.hasText(publicId)) {
                throw new ArchivoStorageException(
                        "Cloudinary no devolvió el publicId del archivo."
                );
            }

            String nombreAlmacenado =
                    obtenerNombreAlmacenado(
                            publicId,
                            formato,
                            tipoRecurso
                    );

            return new ArchivoResponse(
                    original,
                    nombreAlmacenado,
                    secureUrl,
                    publicId,
                    tipoRecurso,
                    file.getContentType(),
                    bytes
            );

        } catch (IOException exception) {
            throw new ArchivoStorageException(
                    "No se pudo leer el archivo enviado.",
                    exception
            );

        } catch (ArchivoStorageException exception) {
            throw exception;

        } catch (Exception exception) {
            throw new ArchivoStorageException(
                    "No se pudo cargar el archivo en Cloudinary.",
                    exception
            );
        }
    }

    /**
     * Permite eliminar una imagen o PDF de Cloudinary
     * utilizando su publicId.
     */
    public void eliminarCloudinary(
            String publicId,
            String resourceType
    ) {
        if (!StringUtils.hasText(publicId)) {
            throw new FileStorageException(
                    "El publicId del archivo es obligatorio."
            );
        }

        String tipoNormalizado =
                StringUtils.hasText(resourceType)
                        ? resourceType
                            .trim()
                            .toLowerCase(Locale.ROOT)
                        : "image";

        if (
                !"image".equals(tipoNormalizado) &&
                !"raw".equals(tipoNormalizado)
        ) {
            throw new FileStorageException(
                    "El tipo de recurso no es válido."
            );
        }

        try {
            Map<?, ?> resultado =
                    cloudinary
                            .uploader()
                            .destroy(
                                    publicId,
                                    ObjectUtils.asMap(
                                            "resource_type",
                                            tipoNormalizado,
                                            "invalidate",
                                            true
                                    )
                            );

            String estado =
                    Objects.toString(
                            resultado.get("result"),
                            ""
                    );

            if (
                    !"ok".equalsIgnoreCase(estado) &&
                    !"not found".equalsIgnoreCase(estado)
            ) {
                throw new ArchivoStorageException(
                        "Cloudinary no pudo eliminar el archivo."
                );
            }

        } catch (ArchivoStorageException exception) {
            throw exception;

        } catch (Exception exception) {
            throw new ArchivoStorageException(
                    "No se pudo eliminar el archivo de Cloudinary.",
                    exception
            );
        }
    }

    /**
     * Se conserva para servir archivos antiguos guardados
     * en el directorio local uploads/.
     */
    public Resource cargar(
            String carpeta,
            String filename
    ) {
        String carpetaNormalizada =
                normalizarCarpeta(carpeta);

        try {
            Path directorio =
                    root.resolve(
                            carpetaNormalizada
                    );

            Path archivo =
                    directorio
                            .resolve(filename)
                            .normalize();

            if (!archivo.startsWith(directorio)) {
                throw new FileStorageException(
                        "Ruta de archivo inválida."
                );
            }

            Resource resource =
                    new UrlResource(
                            archivo.toUri()
                    );

            if (
                    !resource.exists() ||
                    !resource.isReadable()
            ) {
                throw new FileStorageException(
                        "Archivo no encontrado."
                );
            }

            return resource;

        } catch (
                java.net.MalformedURLException exception
        ) {
            throw new FileStorageException(
                    "Archivo no encontrado.",
                    exception
            );
        }
    }

    public String contentType(
            Resource resource
    ) {
        try {
            String type =
                    Files.probeContentType(
                            resource
                                    .getFile()
                                    .toPath()
                    );

            return type == null
                    ? "application/octet-stream"
                    : type;

        } catch (IOException exception) {
            return "application/octet-stream";
        }
    }

    private void validarArchivo(
            String carpeta,
            MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException(
                    "Debe seleccionar un archivo."
            );
        }

        long maxBytes =
                "certificaciones".equals(carpeta)
                        ? MAX_DOCUMENTO_BYTES
                        : MAX_IMAGEN_BYTES;

        if (file.getSize() > maxBytes) {
            throw new FileStorageException(
                    "El archivo supera el tamaño máximo permitido."
            );
        }
    }

    private String obtenerNombreOriginal(
            MultipartFile file
    ) {
        String nombre =
                file.getOriginalFilename();

        String original =
                StringUtils.cleanPath(
                        nombre == null
                                ? "archivo"
                                : nombre
                );

        if (original.contains("..")) {
            throw new FileStorageException(
                    "Nombre de archivo inválido."
            );
        }

        return original;
    }

    private void validarContenido(
            String carpeta,
            String extension,
            MultipartFile file
    ) {
        boolean image =
                IMAGENES.contains(extension);

        boolean pdf =
                "pdf".equals(extension);

        if ("certificaciones".equals(carpeta)) {
            if (!image && !pdf) {
                throw new FileStorageException(
                        "Solo se permiten PDF, JPG, PNG o WEBP."
                );
            }
        } else if (!image) {
            throw new FileStorageException(
                    "Solo se permiten imágenes JPG, PNG o WEBP."
            );
        }

        try {
            if (pdf) {
                validarPdf(file);
                return;
            }

            if ("webp".equals(extension)) {
                validarWebp(file);
                return;
            }

            if (ImageIO.read(file.getInputStream()) == null) {
                throw new FileStorageException(
                        "El contenido no corresponde a una imagen válida."
                );
            }

        } catch (IOException exception) {
            throw new FileStorageException(
                    "No se pudo validar el archivo.",
                    exception
            );
        }
    }

    private void validarPdf(
            MultipartFile file
    ) throws IOException {
        try (
                BufferedInputStream input =
                        new BufferedInputStream(
                                file.getInputStream()
                        )
        ) {
            byte[] header =
                    input.readNBytes(5);

            String contenido =
                    new String(
                            header,
                            StandardCharsets.US_ASCII
                    );

            if (!"%PDF-".equals(contenido)) {
                throw new FileStorageException(
                        "El contenido no corresponde a un PDF válido."
                );
            }
        }
    }

    private void validarWebp(
            MultipartFile file
    ) throws IOException {
        try (
                BufferedInputStream input =
                        new BufferedInputStream(
                                file.getInputStream()
                        )
        ) {
            byte[] header =
                    input.readNBytes(12);

            String riff =
                    header.length >= 4
                            ? new String(
                                    header,
                                    0,
                                    4,
                                    StandardCharsets.US_ASCII
                            )
                            : "";

            String webp =
                    header.length >= 12
                            ? new String(
                                    header,
                                    8,
                                    4,
                                    StandardCharsets.US_ASCII
                            )
                            : "";

            if (
                    !"RIFF".equals(riff) ||
                    !"WEBP".equals(webp)
            ) {
                throw new FileStorageException(
                        "El contenido no corresponde a una imagen WEBP válida."
                );
            }
        }
    }

    private String normalizarCarpeta(
            String carpeta
    ) {
        if (!StringUtils.hasText(carpeta)) {
            throw new FileStorageException(
                    "La carpeta es obligatoria."
            );
        }

        String normalizada =
                carpeta
                        .trim()
                        .toLowerCase(Locale.ROOT);

        if (!CARPETAS.contains(normalizada)) {
            throw new FileStorageException(
                    "Tipo de archivo no permitido."
            );
        }

        return normalizada;
    }

    private String normalizarRootFolder(
            String rootFolder
    ) {
        if (!StringUtils.hasText(rootFolder)) {
            return "jm-pormar";
        }

        return rootFolder
                .trim()
                .replaceAll("^/+", "")
                .replaceAll("/+$", "");
    }

    private String obtenerExtension(
            String filename
    ) {
        int dot =
                filename.lastIndexOf('.');

        if (
                dot < 0 ||
                dot == filename.length() - 1
        ) {
            throw new FileStorageException(
                    "El archivo no tiene una extensión válida."
            );
        }

        return filename
                .substring(dot + 1)
                .toLowerCase(Locale.ROOT);
    }

    private String obtenerTexto(
            Map<?, ?> resultado,
            String clave
    ) {
        Object valor =
                resultado.get(clave);

        return valor == null
                ? null
                : valor.toString();
    }

    private String obtenerTextoPredeterminado(
            Map<?, ?> resultado,
            String clave,
            String predeterminado
    ) {
        String valor =
                obtenerTexto(
                        resultado,
                        clave
                );

        return StringUtils.hasText(valor)
                ? valor
                : predeterminado;
    }

    private long obtenerLong(
            Map<?, ?> resultado,
            String clave,
            long predeterminado
    ) {
        Object valor =
                resultado.get(clave);

        if (valor instanceof Number numero) {
            return numero.longValue();
        }

        return predeterminado;
    }

    private String obtenerNombreAlmacenado(
            String publicId,
            String formato,
            String resourceType
    ) {
        String nombre =
                publicId.substring(
                        publicId.lastIndexOf('/') + 1
                );

        if (
                "raw".equals(resourceType) ||
                !StringUtils.hasText(formato) ||
                nombre.endsWith("." + formato)
        ) {
            return nombre;
        }

        return nombre + "." + formato;
    }
}