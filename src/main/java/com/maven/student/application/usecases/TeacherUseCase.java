package com.maven.student.application.usecases;

import com.openapi.generate.model.RequestTeacherDto;
import com.openapi.generate.model.ResponseTeacherDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TeacherUseCase.
 * This interface defines the use cases for managing teachers.
 * It includes methods to retrieve all active students and to create a new student.
 *
 * @author Joseph Magallanes
 * @since 2025-06-25
 */
public interface TeacherUseCase {
    Flux<ResponseTeacherDto> getAllTeachers();
    Mono<ResponseTeacherDto> createTeacher(RequestTeacherDto requestDto);
}
