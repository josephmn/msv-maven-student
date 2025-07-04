package com.maven.student.domain.services;

import com.maven.student.infrastructure.exception.types.NotFoundException;
import org.springframework.stereotype.Service;
import com.maven.student.application.usecases.StudentUseCase;
import com.maven.student.domain.repository.StudentRepositoryReactive;
import com.maven.student.infrastructure.exception.types.AlreadyExistsException;
import com.maven.student.infrastructure.util.StudentMapper;
import com.openapi.generate.model.RequestStudentDto;
import com.openapi.generate.model.ResponseDTO;
import com.openapi.generate.model.ResponseStudentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the StudentUseCase interface.
 * Provides methods to manage students, including retrieving active students and creating new students.
 * Uses reactive programming with Project Reactor.
 *
 * @author Joseph Magallanes
 * @since 2025-06-16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentUseCaseImpl implements StudentUseCase {

    private final StudentRepositoryReactive repositoryReactive;
    private final StudentMapper studentMapper;


    @Override
    public Flux<ResponseStudentDto> getAllStudents() {
        log.info("Start execute method getAllStudents");
        return repositoryReactive.findAll()
                .map(studentMapper::studentToResponse)
                .doOnTerminate(() -> log.info("Finished execute method getAllStudents"));
    }

    @Override
    public Flux<ResponseStudentDto> getAllStudentsActives() {
        log.info("Start execute method getAllStudentsActives");
        return repositoryReactive.findByStatusTrue()
                .map(studentMapper::studentToResponse)
                .doOnTerminate(() -> log.info("Finished execute method getAllStudentsActives"));
    }

    @Override
    public Mono<ResponseStudentDto> getStudentById(Long id) {
        log.info("Start execute method getStudentById");
        return repositoryReactive.findById(id)
                .map(studentMapper::studentToResponse)
                .switchIfEmpty(Mono.error(new AlreadyExistsException(
                        "Student not found with id: %s", id)))
                .doOnTerminate(() -> log.info("Finished execute method getStudentById"));
    }

    @Override
    public Mono<ResponseStudentDto> createStudent(RequestStudentDto requestDto) {
        log.info("Start execute method createStudent");
        final String documentNumber = requestDto.getDocument();
        return repositoryReactive.findByDocument(documentNumber)
                .flatMap(existingCustomer -> Mono.error(new AlreadyExistsException(
                        "Student exists with document number: %s", documentNumber)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Student before create: {}", requestDto);
                    return repositoryReactive.save(studentMapper.requestToStudent(requestDto))
                            .map(studentMapper::studentToResponse)
                            .doOnNext(customerAfter -> log.info(
                                    "Student after create: {}", customerAfter));
                }))
                .cast(ResponseStudentDto.class)
                .doOnTerminate(() -> log.info("Finished execute method createStudent"));
    }

    @Override
    public Mono<ResponseStudentDto> updateStudentById(Long id, RequestStudentDto requestDto) {
        log.info("Start execute method updateStudentById");
        return repositoryReactive.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Student not found with id: %s", id)))
                .flatMap(existingStudent -> {
                    if (!existingStudent.getDocument().equals(requestDto.getDocument())) {
                        return Mono.error(new NotFoundException(
                                "Student with document %s, doesn't correspond the object", requestDto.getDocument()));
                    }
                    else {
                        final var updatedStudent = studentMapper.requestToStudent(requestDto);
                        updatedStudent.setId(id);
                        return repositoryReactive.save(updatedStudent)
                                .map(studentMapper::studentToResponse);
                    }
                })
                .doOnTerminate(() -> log.info("Finished execute method updateStudentById"));
    }

    @Override
    public Mono<ResponseDTO> deleteStudentById(Long id) {
        log.info("Start execute method deleteStudentById");
        return repositoryReactive.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return repositoryReactive.deleteById(id)
                                .then(Mono.just(new ResponseDTO()
                                        .code("200")
                                        .message("Student deleted successfully"))
                                );
                    }
                    else {
                        return Mono.error(new NotFoundException(
                                "Student not found with id: %s", id));
                    }
                })
                .doOnTerminate(() -> log.info("Finished execute method deleteStudentById"));
    }
}
