package com.universidad.docentes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- 404: Docente no encontrado ---
    @ExceptionHandler(DocenteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(DocenteNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // --- 409: Documento duplicado ---
    @ExceptionHandler(DocumentoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleDuplicado(DocumentoDuplicadoException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // --- 400: Errores de validación (@Valid) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> campos = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            campos.put(fe.getField(), fe.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("mensaje", "Error de validación en los campos");
        response.put("errores", campos);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.badRequest().body(response);
    }

    // --- 500: Cualquier otro error inesperado ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // DTO interno para respuestas de error
    public record ErrorResponse(int status, String mensaje, LocalDateTime timestamp) {}
}
