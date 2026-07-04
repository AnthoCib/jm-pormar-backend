package com.jmpormar.auth.config;

import com.jmpormar.auth.entity.AdminUsuario;
import com.jmpormar.auth.repository.AdminUsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {
    private final AdminUsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String username;
    @Value("${app.admin.email:admin@jmpormar.com}")
    private String email;
    @Value("${app.admin.password:}")
    private String password;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (repository.count() > 0) {
            return;
        }
        if (!StringUtils.hasText(password)) {
            log.warn("No se creó el administrador inicial porque ADMIN_PASSWORD está vacío");
            return;
        }
        AdminUsuario admin = AdminUsuario.builder()
                .usuario(username.trim())
                .correo(email.trim().toLowerCase())
                .passwordHash(passwordEncoder.encode(password))
                .activo(true)
                .build();
        repository.save(admin);
        log.info("Administrador inicial creado con usuario {}", username);
    }
}
