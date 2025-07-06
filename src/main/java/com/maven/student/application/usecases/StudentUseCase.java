package com.maven.student.application.usecases;

import com.openapi.generate.model.RequestStudentDto;
import com.openapi.generate.model.ResponseDTO;
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
    Flux<ResponseStudentDto> getAllStudents();
    Flux<ResponseStudentDto> getAllStudentsActives();
    Mono<ResponseStudentDto> createStudent(RequestStudentDto requestDto);
    Mono<ResponseStudentDto> getStudentById(Long id);
    Mono<ResponseStudentDto> updateStudentById(Long id, RequestStudentDto requestDto);
    Mono<ResponseDTO> deleteStudentById(Long id);
    Flux<ResponseStudentDto> getListStudentByName(String name);
}
