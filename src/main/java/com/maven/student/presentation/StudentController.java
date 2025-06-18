package com.maven.student.presentation;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maven.student.application.dto.RequestDto;
import com.maven.student.application.dto.ResponseDto;
import com.maven.student.application.usecases.StudentUseCase;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentUseCase studentUseCase;

    @GetMapping
    public Mono<ResponseEntity<Flux<ResponseDto>>> getAllStudentsActives() {
        return Mono.just(ResponseEntity.ok(this.studentUseCase.getAllStudentsActives()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ResponseDto>> createStudent(@Valid @RequestBody RequestDto requestDto) {
        return this.studentUseCase.createStudent(requestDto)
            .map(responseDto -> ResponseEntity
            .created(URI.create("/api/v1/students/"))
                .body(responseDto))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
