package com.jmpormar.archivo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class SeedFileInitializer {

    private final Path uploadRoot;

    public SeedFileInitializer(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.uploadRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    void copyDemoProductImage() throws IOException {
        Path productDirectory = uploadRoot.resolve("productos");
        Path destination = productDirectory.resolve("demo-producto.png");
        Files.createDirectories(productDirectory);
        if (Files.notExists(destination)) {
            ClassPathResource resource = new ClassPathResource("seed/demo-producto.png");
            try (var input = resource.getInputStream()) {
                Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
