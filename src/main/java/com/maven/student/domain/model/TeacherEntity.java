package com.maven.student.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TeacherEntity.
 *
 * @author Joseph Magallanes
 * @since 2025-06-25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher")
public class TeacherEntity {

    @Id
    private Long id;
    private String document;
    private String name;
    private String lastName;
    private boolean status;
    private int age;
}
