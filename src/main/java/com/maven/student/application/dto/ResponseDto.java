package com.maven.student.application.dto;

/**
 * Data Transfer Object for Response.
 * This class is used to transfer data between the client and server.
 * It includes fields for the response data.
 *  @author Joseph Magallanes
 *  @since 2025-06-16
 *
 * @param id        Unique identifier for the response
 * @param document  Document number
 * @param name      Name of the person
 * @param lastName  Last name of the person
 * @param status    Status of the response
 * @param age       Age of the person
 */
public record ResponseDto(
        Long id,
        String document,
        String name,
        String lastName,
        boolean status,
        int age) {
}