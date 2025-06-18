package com.maven.student.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import static com.maven.student.infrastructure.util.ConstantsConfig.MAX_AGE;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for Request.
 * This class is used to transfer data between the client and server.
 * It includes validation annotations to ensure the integrity of the data.
 *  @author Joseph Magallanes
 *  @since 2025-06-16
 *
 * @param id        Unique identifier for the request
 * @param document  Document number, must not be blank
 * @param name      Name of the person, must not be blank
 * @param lastName  Last name of the person, must not be blank
 * @param status    Status of the request
 * @param age       Age of the person, must be between 1 and 100
 */
public record RequestDto(
        Long id,

        @NotBlank(message = "Document number cannot be blank")
        @JsonProperty("document")
        String document,

        @NotBlank(message = "Name cannot be blank")
        @JsonProperty("name")
        String name,

        @NotBlank(message = "Last name cannot be blank")
        @JsonProperty("lastName")
        String lastName,

        @JsonProperty("status")
        boolean status,

        @Min(value = 1, message = "Age must be at least 1")
        @Max(value = MAX_AGE, message = "Age must be at most 100")
        @JsonProperty("age")
        int age) {
}
