package com.maven.student.infrastructure.exception;

import java.util.Date;
import java.util.Map;
import lombok.Data;

/**
 * ErrorResponse class represents the structure of an error response.
 * It contains information about the HTTP status, error message, date of the error,
 * and a map of error codes and messages.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Data
public class ErrorResponse {
    private int status; // HTTP status code
    private String message;
    private Date date;
    private Map<String, String> errors;

    /**
     * Constructor for ErrorResponse with status, message, date, and errors.
     *
     * @param status HTTP status code
     * @param message Error message
     * @param date Date of the error
     * @param errors Map of error codes and messages
     */
    public ErrorResponse(int status, String message, Date date, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.date = date;
        this.errors = errors;
    }

    /**
     * Constructor for ErrorResponse with status, message, and date.
     *
     * @param status HTTP status code
     * @param message Error message
     * @param date Date of the error
     */
    public ErrorResponse(int status, String message, Date date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
