package com.maven.student.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserEntity.
 * Represents a user in the system with fields for username, password, email, role, and enabled status.
 * This class is mapped to the "users" table in the database.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    private Long id;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("email")
    private String email;

    @Column("role")
    private String role;

    @Column("enabled")
    private Boolean enabled = true;
}
