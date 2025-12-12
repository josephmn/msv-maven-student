package com.maven.student.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectStudent {
    private String operation;
    private String data;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class Operation {
        private String document;
        private String name;
        private String lastName;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class Data {
        private boolean status;
        private int age;
    }
}
