package com.maven.student.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.maven.student.infrastructure.exception.types.AlreadyExistsException;
import com.maven.student.infrastructure.exception.types.AuthException;
import com.maven.student.infrastructure.exception.types.NotFoundException;
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
            exception.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    /**
     * Handle CustomerAlreadyExistsException and return a 409 response.
     *
     * @param exception the CustomerAlreadyExistsException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ResponseEntity<ErrorResponse>> handleNotFoundException(AlreadyExistsException exception) {
        final ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            exception.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(error));
    }

    /**
     * Handle handleAuthException and return a 400 response.
     *
     * @param exception the UnauthorizedException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(AuthException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleAuthException(AuthException exception) {
        final ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Bad request",
                exception.getErrors()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
    }
}
