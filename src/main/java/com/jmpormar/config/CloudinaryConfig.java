package com.jmpormar.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(
            @Value("${app.cloudinary.cloud-name:}")
            String cloudName,

            @Value("${app.cloudinary.api-key:}")
            String apiKey,

            @Value("${app.cloudinary.api-secret:}")
            String apiSecret
    ) {
        String cloudNameNormalizado =
                cloudName == null ? "" : cloudName.trim();

        String apiKeyNormalizada =
                apiKey == null ? "" : apiKey.trim();

        String apiSecretNormalizado =
                apiSecret == null ? "" : apiSecret.trim();

        validarVariable(
                cloudNameNormalizado,
                "CLOUDINARY_CLOUD_NAME"
        );

        validarVariable(
                apiKeyNormalizada,
                "CLOUDINARY_API_KEY"
        );

        validarVariable(
                apiSecretNormalizado,
                "CLOUDINARY_API_SECRET"
        );

        log.info(
                "Cloudinary configurado con cloud_name: [{}]",
                cloudNameNormalizado
        );

        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name",
                        cloudNameNormalizado,

                        "api_key",
                        apiKeyNormalizada,

                        "api_secret",
                        apiSecretNormalizado,

                        "secure",
                        true
                )
        );
    }

    private void validarVariable(
            String valor,
            String nombre
    ) {
        if (!StringUtils.hasText(valor)) {
            throw new IllegalStateException(
                    "La variable " +
                    nombre +
                    " no está configurada."
            );
        }
    }
}