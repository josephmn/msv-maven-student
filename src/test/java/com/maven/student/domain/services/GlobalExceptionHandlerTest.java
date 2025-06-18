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
import com.maven.student.infrastructure.exception.types.StudentAlreadyExistsException;
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
        final StudentAlreadyExistsException ex = new StudentAlreadyExistsException("Ya existe");
        final Mono<ResponseEntity<ErrorResponse>> responseMono = handler.handleNotFoundException(ex);
        final ResponseEntity<ErrorResponse> response = responseMono.block();
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Ya existe", response.getBody().getMessage());
    }

    @Test
    void testHandleValidationException() {
        final WebExchangeBindException ex = Mockito.mock(WebExchangeBindException.class);
        final FieldError fieldError1 = new FieldError("obj", "campo1", "obligatorio");
        final FieldError fieldError2 = new FieldError("obj", "campo2", "invalido");
        Mockito.when(ex.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        final Mono<ResponseEntity<ErrorResponse>> responseMono = handler.handleValidationException(ex);
        final ResponseEntity<ErrorResponse> response = responseMono.block();
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error", response.getBody().getMessage());
        final Map<String, String> errors = response.getBody().getErrors();
        assertEquals(2, errors.size());
        assertEquals("obligatorio", errors.get("campo1"));
        assertEquals("invalido", errors.get("campo2"));
    }

    @Test
    void testHandleDecodingException() {
        final DecodingException ex = new DecodingException("json error");
        final Mono<ResponseEntity<ErrorResponse>> responseMono = handler.handleDecodingException(ex);
        final ResponseEntity<ErrorResponse> response = responseMono.block();
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid JSON format", response.getBody().getMessage());
        final Map<String, String> errors = response.getBody().getErrors();
        assertEquals(1, errors.size());
        assertEquals("json error", errors.get("json"));
    }
}