package com.maven.student.infrastructure.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ErrorResponseTest {

    @Test
    void testConstructorWithErrors() {
        final int status = 404;
        final String message = "No encontrado";
        final Date date = new Date();
        final Map<String, String> errors = new HashMap<>();
        errors.put("field1", "El campo es obligatorio");
        errors.put("field2", "Formato inválido");

        final ErrorResponse response = new ErrorResponse(status, message, date, errors);

        assertEquals(status, response.getStatus());
        assertEquals(message, response.getMessage());
        assertEquals(date, response.getDate());
        assertEquals(errors, response.getErrors());
    }

    @Test
    void testConstructorWithoutErrors() {
        final int status = 400;
        final String message = "Solicitud incorrecta";
        final Date date = new Date();

        final ErrorResponse response = new ErrorResponse(status, message, date);

        assertEquals(status, response.getStatus());
        assertEquals(message, response.getMessage());
        assertEquals(date, response.getDate());
        assertNull(response.getErrors());
    }
}