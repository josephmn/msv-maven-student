package com.maven.student.infrastructure.exception.types;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationExceptionTest {
    @Test
    void testErrorsAreStoredCorrectly() {
        final Map<Integer, String> errors = new HashMap<>();
        errors.put(1, "Campo nombre es obligatorio");
        errors.put(2, "El email no es válido");

        final ValidationException exception = new ValidationException(errors);

        assertEquals(errors, exception.getErrors());
        assertTrue(exception.getErrors().containsKey(1));
        assertTrue(exception.getErrors().containsKey(2));
        assertEquals("Campo nombre es obligatorio", exception.getErrors().get(1));
        assertEquals("El email no es válido", exception.getErrors().get(2));
    }
}