package com.maven.student.presentation;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import com.maven.student.application.usecases.StudentUseCase;
import com.openapi.generate.api.StudentApi;
import com.openapi.generate.model.RequestDto;
import com.openapi.generate.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * StudentController.
 * This class handles HTTP requests related to students.
 * It provides endpoints to retrieve all active students and to create a new student.
 *
 * @author Joseph Magallanes
 * @since 2025-06-16
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class StudentController implements StudentApi {

    private final StudentUseCase studentUseCase;

    @Override
    public Mono<ResponseEntity<Flux<ResponseDto>>> getAllStudentsActives(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(this.studentUseCase.getAllStudentsActives()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<ResponseDto>> createStudent(Mono<RequestDto> requestDto, ServerWebExchange exchange) {
        return requestDto.flatMap(dto ->
                this.studentUseCase.createStudent(dto)
                        .map(responseDto -> ResponseEntity
                                .created(URI.create("/api/v1/students/"))
                                .body(responseDto))
                        .defaultIfEmpty(ResponseEntity.notFound().build())
        );
    }
}
