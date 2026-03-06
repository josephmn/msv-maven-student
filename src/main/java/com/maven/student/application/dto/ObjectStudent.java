package com.maven.student.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ObjectStudent DTO class.
 *
 * @author Joseph Magallanes
 * @since 2025-11-12
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectStudent {
    private String operation;
    private String data;

    /**
     * Operation DTO class.
     *
     * @author Joseph Magallanes
     * @since 2025-11-12
     */
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class Operation {
        private String document;
        private String name;
        private String lastName;
        private String email;
    }

    /**
     * Data DTO class.
     *
     * @author Joseph Magallanes
     * @since 2025-11-12
     */
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class Data {
        private boolean status;
        private int age;
    }
}
