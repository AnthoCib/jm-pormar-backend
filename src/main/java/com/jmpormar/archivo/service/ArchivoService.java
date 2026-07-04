package com.jmpormar.archivo.service;

import com.jmpormar.archivo.dto.ArchivoResponse;
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
import java.nio.file.*;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class ArchivoService {
    private static final Set<String> CARPETAS = Set.of("productos", "servicios", "certificaciones");
    private static final Set<String> IMAGENES = Set.of("jpg", "jpeg", "png", "webp");
    private final Path root;

    public ArchivoService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    void init() {
        try {
            Files.createDirectories(root);
            for (String folder : CARPETAS) Files.createDirectories(root.resolve(folder));
        } catch (IOException ex) {
            throw new FileStorageException("No se pudo inicializar el directorio de archivos", ex);
        }
    }

    public ArchivoResponse guardar(String carpeta, MultipartFile file) {
        validarCarpeta(carpeta);
        if (file == null || file.isEmpty()) throw new FileStorageException("Debe seleccionar un archivo");
        long maxBytes = "certificaciones".equals(carpeta) ? 10L * 1024 * 1024 : 5L * 1024 * 1024;
        if (file.getSize() > maxBytes) throw new FileStorageException("El archivo supera el tamaño máximo permitido");
        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "archivo" : file.getOriginalFilename());
        if (original.contains("..")) throw new FileStorageException("Nombre de archivo inválido");
        String extension = extension(original);
        validarContenido(carpeta, extension, file);
        String stored = UUID.randomUUID() + "." + extension;
        Path destination = root.resolve(carpeta).resolve(stored).normalize();
        if (!destination.startsWith(root.resolve(carpeta))) throw new FileStorageException("Ruta de archivo inválida");
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            String contentType = Files.probeContentType(destination);
            return new ArchivoResponse(original, stored, "/api/files/" + carpeta + "/" + stored,
                    contentType == null ? file.getContentType() : contentType, file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("No se pudo guardar el archivo", ex);
        }
    }

    public Resource cargar(String carpeta, String filename) {
        validarCarpeta(carpeta);
        try {
            Path file = root.resolve(carpeta).resolve(filename).normalize();
            if (!file.startsWith(root.resolve(carpeta))) throw new FileStorageException("Ruta de archivo inválida");
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) throw new FileStorageException("Archivo no encontrado");
            return resource;
        } catch (java.net.MalformedURLException ex) {
            throw new FileStorageException("Archivo no encontrado", ex);
        }
    }

    public String contentType(Resource resource) {
        try {
            String type = Files.probeContentType(resource.getFile().toPath());
            return type == null ? "application/octet-stream" : type;
        } catch (IOException ex) {
            return "application/octet-stream";
        }
    }

    private void validarContenido(String carpeta, String extension, MultipartFile file) {
        boolean image = IMAGENES.contains(extension);
        boolean pdf = "pdf".equals(extension);
        if ("certificaciones".equals(carpeta)) {
            if (!image && !pdf) throw new FileStorageException("Solo se permiten PDF, JPG, PNG o WEBP");
        } else if (!image) {
            throw new FileStorageException("Solo se permiten imágenes JPG, PNG o WEBP");
        }
        try {
            if (pdf) {
                try (var in = new BufferedInputStream(file.getInputStream())) {
                    byte[] header = in.readNBytes(5);
                    if (!new String(header, java.nio.charset.StandardCharsets.US_ASCII).equals("%PDF-")) {
                        throw new FileStorageException("El contenido no corresponde a un PDF válido");
                    }
                }
            } else if ("webp".equals(extension)) {
                try (var in = new BufferedInputStream(file.getInputStream())) {
                    byte[] header = in.readNBytes(12);
                    String riff = header.length >= 4 ? new String(header, 0, 4, java.nio.charset.StandardCharsets.US_ASCII) : "";
                    String webp = header.length >= 12 ? new String(header, 8, 4, java.nio.charset.StandardCharsets.US_ASCII) : "";
                    if (!"RIFF".equals(riff) || !"WEBP".equals(webp)) {
                        throw new FileStorageException("El contenido no corresponde a una imagen WEBP válida");
                    }
                }
            } else if (ImageIO.read(file.getInputStream()) == null) {
                throw new FileStorageException("El contenido no corresponde a una imagen válida");
            }
        } catch (IOException ex) {
            throw new FileStorageException("No se pudo validar el archivo", ex);
        }
    }

    private void validarCarpeta(String carpeta) {
        if (!CARPETAS.contains(carpeta)) throw new FileStorageException("Tipo de archivo no permitido");
    }

    private String extension(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) throw new FileStorageException("El archivo no tiene una extensión válida");
        return filename.substring(dot + 1).toLowerCase(Locale.ROOT);
    }
}
