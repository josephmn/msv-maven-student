package com.maven.student.infrastructure.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import com.maven.student.infrastructure.exception.types.NotFoundException;
import com.maven.student.infrastructure.exception.types.StudentAlreadyExistsException;
import reactor.core.publisher.Mono;

/**
 * GlobalExceptionHandler class handles exceptions thrown by the application.
 * It provides custom error responses for different types of exceptions.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle NotFoundException and return a 404 response.
     *
     * @param exception the NotFoundException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<ErrorResponse>>handleNotFoundException(NotFoundException exception) {
        final ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage(),
            new Date()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    /**
     * Handle CustomerAlreadyExistsException and return a 409 response.
     *
     * @param exception the CustomerAlreadyExistsException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(StudentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ResponseEntity<ErrorResponse>> handleNotFoundException(StudentAlreadyExistsException exception) {
        final ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            exception.getMessage(),
            new Date()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(error));
    }

    /**
     * Handle ValidationException and return a 400 response with validation errors.
     *
     * @param exception the ValidationException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationException(WebExchangeBindException exception) {
        final Map<String, String> errors = new HashMap<>();
        exception.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        final ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                new Date(),
                errors
        );

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));
    }

    /**
     * Handle DecodingException and return a 400 response with decoding errors.
     *
     * @param exception the DecodingException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(DecodingException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDecodingException(DecodingException exception) {
        final Map<String, String> mapErrors = new HashMap<>();
        mapErrors.put("json", exception.getMessage());

        final ErrorResponse errors = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid JSON format",
                new Date(),
                mapErrors
        );

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors));
    }
}
