package com.jmpormar.shared.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        OffsetDateTime timestamp
) {
    private static final ZoneId LIMA = ZoneId.of("America/Lima");

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data, OffsetDateTime.now(LIMA));
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ok("Operación realizada correctamente", data);
    }

    public static ApiResponse<Void> ok(String message) {
        return ok(message, null);
    }

    public static ApiResponse<Object> error(String message, Object data) {
        return new ApiResponse<>(false, message, data, OffsetDateTime.now(LIMA));
    }
}
