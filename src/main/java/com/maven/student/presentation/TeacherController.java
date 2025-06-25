package com.maven.student.presentation;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import com.maven.student.application.usecases.TeacherUseCase;
import com.openapi.generate.api.TeacherApi;
import com.openapi.generate.model.RequestTeacherDto;
import com.openapi.generate.model.ResponseTeacherDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TeacherController.
 * This class handles HTTP requests related to teachers.
 * It provides endpoints to manage teacher-related operations.
 *
 * @author Joseph Magallanes
 * @since 2025-06-25
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TeacherController implements TeacherApi {

    private final TeacherUseCase teacherUseCase;

    @Override
    public Mono<ResponseEntity<Flux<ResponseTeacherDto>>> getAllTeachers(
            ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(this.teacherUseCase.getAllTeachers()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<ResponseTeacherDto>> createTeacher(
            Mono<RequestTeacherDto> requestTeacherDto, ServerWebExchange exchange) {
        return requestTeacherDto.flatMap(dto ->
                this.teacherUseCase.createTeacher(dto)
                        .map(responseDto -> ResponseEntity
                                .created(URI.create("/api/v1/students/"))
                                .body(responseDto))
                        .defaultIfEmpty(ResponseEntity.notFound().build())
        );
    }
}
