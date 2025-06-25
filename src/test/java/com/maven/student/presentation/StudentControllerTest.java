package com.maven.student.presentation;


import com.maven.student.application.usecases.StudentUseCase;
import com.openapi.generate.model.RequestDto;
import com.openapi.generate.model.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class StudentControllerTest {

    private static final String STUDENT_API = "/api/v1/students";
    public static final int OBJ_AGE = 25;

    private RequestDto requestDto1;
    private RequestDto requestDto2;
    private ResponseDto responseDto1;
    private ResponseDto responseDto2;

    private WebTestClient webTestClient;
    private Flux<ResponseDto> studentResponseFlux;
    private StudentUseCase studentUseCase;

    @BeforeEach
    void setUp() {
        studentUseCase = mock(StudentUseCase.class);

        final StudentController studentController = new StudentController(studentUseCase);

        webTestClient = WebTestClient.bindToController(studentController)
                .build();

        requestDto1 = new RequestDto()
                .id(1L)
                .document("12345678")
                .name("Juan")
                .lastName("Perez")
                .status(true)
                .age(OBJ_AGE);
        requestDto2 = new RequestDto()
                .id(2L)
                .document("87654321")
                .name("Maria")
                .lastName("Lopez")
                .status(true)
                .age(OBJ_AGE);

        responseDto1 = new ResponseDto()
                .id(1L)
                .document("12345678")
                .name("Juan")
                .lastName("Perez")
                .status(true)
                .age(OBJ_AGE);
        responseDto2 = new ResponseDto()
                .id(2L)
                .document("87654321")
                .name("Maria")
                .lastName("Lopez")
                .status(true)
                .age(OBJ_AGE);
        studentResponseFlux = Flux.just(responseDto1, responseDto2);
    }

    @Test
    @DisplayName("method get all student actives test")
    void getAllStudentTest() {
        when(studentUseCase.getAllStudentsActives()).thenReturn(studentResponseFlux);

        webTestClient.get().uri(STUDENT_API)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResponseDto.class)
                .hasSize(2)
                .contains(responseDto1, responseDto2);

        verify(studentUseCase).getAllStudentsActives();
    }

    @Test
    @DisplayName("method create student 1 test")
    void postCreateStudentTest() {
        when(studentUseCase.createStudent(requestDto1)).thenReturn(Mono.just(responseDto1));
        webTestClient.post().uri(STUDENT_API)
                .bodyValue(requestDto1)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ResponseDto.class)
                .isEqualTo(responseDto1);

        verify(studentUseCase).createStudent(requestDto1);
    }

    @Test
    @DisplayName("method create student 2 test")
    void postCreateStudent2Test() {
        when(studentUseCase.createStudent(requestDto2)).thenReturn(Mono.just(responseDto2));
        webTestClient.post().uri(STUDENT_API)
                .bodyValue(requestDto2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ResponseDto.class)
                .isEqualTo(responseDto2);

        verify(studentUseCase).createStudent(requestDto2);
    }
}