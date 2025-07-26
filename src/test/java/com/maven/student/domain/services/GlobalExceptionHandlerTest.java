package com.maven.student.domain.services;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.maven.student.infrastructure.exception.ErrorResponse;
import com.maven.student.infrastructure.exception.GlobalExceptionHandler;
import com.maven.student.infrastructure.exception.types.NotFoundException;
import com.maven.student.infrastructure.exception.types.AlreadyExistsException;
import reactor.core.publisher.Mono;

class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleNotFoundException() {
        final NotFoundException ex = new NotFoundException("No encontrado");
        final Mono<ResponseEntity<ErrorResponse>> responseMono = handler.handleNotFoundException(ex);
        final ResponseEntity<ErrorResponse> response = responseMono.block();
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No encontrado", response.getBody().getMessage());
    }

    @Test
    void testHandleStudentAlreadyExistsException() {
        final AlreadyExistsException ex = new AlreadyExistsException("Ya existe");
        final Mono<ResponseEntity<ErrorResponse>> responseMono = handler.handleNotFoundException(ex);
        final ResponseEntity<ErrorResponse> response = responseMono.block();
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ya existe", response.getBody().getMessage());
    }
}