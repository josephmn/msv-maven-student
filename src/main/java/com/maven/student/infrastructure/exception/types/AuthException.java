package com.maven.student.infrastructure.exception.types;

import java.util.Map;
import lombok.Getter;

/**
 * Exception thrown when a user is unauthorized to perform an action.
 * @author Joseph Magallanes
 * @since 2025-07-26
 */
@Getter
public class AuthException extends RuntimeException {
    private final Map<String, String> errors;

    /**
     * Constructor for UnauthorizedException.
     *
     * @param errors the detail message
     */
    public AuthException(Map<String, String> errors) {
        this.errors = errors;
    }
}
