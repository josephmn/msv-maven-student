package com.maven.student.application.dto;

/**
 * Data Transfer Object for Student.
 *
 * @param document  the document identifier of the student
 * @param name      the first name of the student
 * @param lastName  the last name of the student
 * @param status    the active status of the student
 * @param age       the age of the student
 *
 * @author Joseph Magallanes
 * @since 2025-11-30
 */
public record StudentDto(
    String document,
    String name,
    String lastName,
    String email,
    boolean status,
    int age
) {
}
