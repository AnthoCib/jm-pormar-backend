package com.jmpormar.contacto.service;


import com.jmpormar.contacto.dto.ConsultaRucResponse;
import com.jmpormar.contacto.integration.ApiPeruRucResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConsultaRucService {

    private final RestClient rucRestClient;

    public ConsultaRucResponse consultar(String ruc) {
        validarRuc(ruc);

        try {
            ApiPeruRucResponse response = rucRestClient
                    .post()
                    .uri("/ruc")
                    .body(Map.of("ruc", ruc))
                    .retrieve()
                    .body(ApiPeruRucResponse.class);

            if (
                    response == null ||
                    !response.success() ||
                    response.data() == null ||
                    !StringUtils.hasText(
                            response.data().nombreORazonSocial()
                    )
            ) {
                throw new IllegalArgumentException(
                        "No se encontró información para el RUC ingresado."
                );
            }

            ApiPeruRucResponse.Data data = response.data();

            String direccion = StringUtils.hasText(
                    data.direccionCompleta()
            )
                    ? data.direccionCompleta()
                    : data.direccion();

            return new ConsultaRucResponse(
                    data.ruc(),
                    data.nombreORazonSocial(),
                    data.estado(),
                    data.condicion(),
                    direccion
            );

        } catch (HttpClientErrorException.NotFound exception) {
            throw new IllegalArgumentException(
                    "El RUC ingresado no fue encontrado."
            );
        } catch (HttpClientErrorException.Unauthorized exception) {
            throw new IllegalStateException(
                    "El token de consulta RUC no es válido."
            );
        } catch (RestClientException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar el RUC en este momento.",
                    exception
            );
        }
    }

    private void validarRuc(String ruc) {
        if (ruc == null || !ruc.matches("\\d{11}")) {
            throw new IllegalArgumentException(
                    "El RUC debe contener exactamente 11 dígitos."
            );
        }
    }
}