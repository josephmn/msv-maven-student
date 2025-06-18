package com.maven.student.infrastructure.exception.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentAlreadyExistsExceptionTest {

    @Test
    void testMessageFormatting() {
        final StudentAlreadyExistsException ex = new StudentAlreadyExistsException(
                "El estudiante con id %d ya existe", 101);
        assertEquals("El estudiante con id 101 ya existe", ex.getMessage());
    }
}