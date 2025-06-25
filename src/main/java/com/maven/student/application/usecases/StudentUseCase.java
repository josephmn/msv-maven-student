package com.maven.student.application.usecases;

import com.openapi.generate.model.RequestStudentDto;
import com.openapi.generate.model.ResponseStudentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * StudentUseCase interface defines the use cases for managing students.
 * It provides methods to retrieve all active students and to create a new student.
 *
 * @author Joseph Magallanes
 * @since 2025-06-16
 */
public interface StudentUseCase {
    Flux<ResponseStudentDto> getAllStudentsActives();
    Mono<ResponseStudentDto> createStudent(RequestStudentDto requestDto);
}
