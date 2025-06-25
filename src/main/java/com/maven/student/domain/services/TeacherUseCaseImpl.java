package com.maven.student.domain.services;

import org.springframework.stereotype.Service;
import com.maven.student.application.usecases.TeacherUseCase;
import com.maven.student.domain.repository.TeacherRepositoryReactive;
import com.maven.student.infrastructure.exception.types.StudentAlreadyExistsException;
import com.maven.student.infrastructure.util.TeacherMapper;
import com.openapi.generate.model.RequestTeacherDto;
import com.openapi.generate.model.ResponseTeacherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the TeacherUseCase interface.
 * This class is currently empty and serves as a placeholder for future teacher-related use cases.
 *
 * @author Joseph Magallanes
 * @since 2025-06-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherUseCaseImpl implements TeacherUseCase {

    private final TeacherRepositoryReactive repositoryReactive;
    private final TeacherMapper teacherMapper;

    @Override
    public Flux<ResponseTeacherDto> getAllTeachers() {
        return repositoryReactive.findAll()
                .map(teacherMapper::teacherToResponse)
                .doOnTerminate(() -> log.info("Finished executing method getAllTeachers"));
    }

    @Override
    public Mono<ResponseTeacherDto> createTeacher(RequestTeacherDto requestDto) {
        log.info("Start execute method createTeacher");
        final String documentNumber = requestDto.getDocument();
        return repositoryReactive.findByDocument(documentNumber)
                .flatMap(existingCustomer -> Mono.error(new StudentAlreadyExistsException(
                        "Teacher exists with document number: %s", documentNumber)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Teacher before create: {}", requestDto);
                    return repositoryReactive.save(teacherMapper.requestToTeacher(requestDto))
                            .map(teacherMapper::teacherToResponse)
                            .doOnNext(customerAfter -> log.info("Teacher after create: {}", customerAfter));
                }))
                .cast(ResponseTeacherDto.class)
                .doOnTerminate(() -> log.info("Finished execute method createTeacher"));
    }
}
