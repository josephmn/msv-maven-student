package com.maven.student.infrastructure.exception.types;

/**
 * Exception thrown when a customer already exists.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
public class StudentAlreadyExistsException extends RuntimeException {

    /**
     * Constructor for CustomerAlreadyExistsException.
     *
     * @param message the detail message
     * @param args the arguments to format the message
     */
    public StudentAlreadyExistsException(String message, Object... args) {
        super(String.format(message, args));
    }
}
