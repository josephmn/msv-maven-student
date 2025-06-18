package com.maven.student.infrastructure.exception.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotFoundExceptionTest {

    @Test
    void testMessageFormatting() {
        final NotFoundException ex = new NotFoundException(
                "No se encontró el recurso con id %d", 123);
        assertEquals("No se encontró el recurso con id 123", ex.getMessage());
    }
}