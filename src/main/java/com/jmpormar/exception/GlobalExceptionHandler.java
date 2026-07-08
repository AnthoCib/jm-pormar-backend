package com.jmpormar.exception;

import com.jmpormar.shared.api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@lombok.extern.slf4j.Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> notFound(ResourceNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<Object>> business(RuntimeException ex) {
        return error(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ApiResponse<Object>> conflict(Exception ex) {
        String message = ex instanceof ConflictException
                ? ex.getMessage()
                : "La operación entra en conflicto con datos existentes";
        return error(HttpStatus.CONFLICT, message, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> validation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                fields.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(globalError ->
                fields.putIfAbsent("request", globalError.getDefaultMessage()));
        return error(HttpStatus.BAD_REQUEST, "Existen datos inválidos", fields);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> constraint(ConstraintViolationException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
            fields.putIfAbsent(field, violation.getMessage());
        });
        return error(HttpStatus.BAD_REQUEST, "Existen parámetros inválidos", fields);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> typeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put(ex.getName(), "El valor enviado no tiene el formato esperado");
        return error(HttpStatus.BAD_REQUEST, "Existen parámetros inválidos", fields);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> unreadable(HttpMessageNotReadableException ex) {
        return error(HttpStatus.BAD_REQUEST,
                "El cuerpo de la solicitud contiene valores o formatos no válidos", null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> badCredentials(BadCredentialsException ex) {
        return error(HttpStatus.UNAUTHORIZED, "Usuario/correo o contraseña incorrectos", null);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<Object>> disabled(DisabledException ex) {
        return error(HttpStatus.UNAUTHORIZED, "El usuario administrador se encuentra inactivo", null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> forbidden(AccessDeniedException ex) {
        return error(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta operación", null);
    }

    @ExceptionHandler({FileStorageException.class, MaxUploadSizeExceededException.class})
    public ResponseEntity<ApiResponse<Object>> fileError(Exception ex) {
        String message = ex instanceof MaxUploadSizeExceededException
                ? "El archivo supera el tamaño máximo permitido"
                : ex.getMessage();
        return error(HttpStatus.BAD_REQUEST, message, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> generic(Exception ex) {
        log.error("Error no controlado", ex);
        return error(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno. Revisa los logs del servidor.", null);
    }

    private ResponseEntity<ApiResponse<Object>> error(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status).body(ApiResponse.error(message, data));
    }
    
    @ExceptionHandler(ArchivoStorageException.class)
    public ResponseEntity<ApiResponse<Object>>
    archivoCloudinary(
            ArchivoStorageException exception
    ) {
        log.error(
                "Error al comunicarse con Cloudinary",
                exception
        );

        return error(
                HttpStatus.BAD_GATEWAY,
                exception.getMessage(),
                null
        );
    }
    
}
