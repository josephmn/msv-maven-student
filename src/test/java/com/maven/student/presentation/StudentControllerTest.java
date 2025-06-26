package com.maven.student.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.maven.student.application.usecases.StudentUseCase;
import com.openapi.generate.model.RequestStudentDto;
import com.openapi.generate.model.ResponseStudentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class StudentControllerTest {

    private static final String STUDENT_API = "/api/v1/students";
    public static final int OBJ_AGE = 25;

    private RequestStudentDto requestDto1;
    private RequestStudentDto requestDto2;
    private ResponseStudentDto responseDto1;
    private ResponseStudentDto responseDto2;

    private WebTestClient webTestClient;
    private Flux<ResponseStudentDto> studentResponseFlux;
    private StudentUseCase studentUseCase;

    @BeforeEach
    void setUp() {
        studentUseCase = mock(StudentUseCase.class);

        final StudentController studentController = new StudentController(studentUseCase);

        webTestClient = WebTestClient.bindToController(studentController)
                .build();

        requestDto1 = new RequestStudentDto()
                .id(1L)
                .document("12345678")
                .name("Juan")
                .lastName("Perez")
                .status(true)
                .age(OBJ_AGE);
        requestDto2 = new RequestStudentDto()
                .id(2L)
                .document("87654321")
                .name("Maria")
                .lastName("Lopez")
                .status(true)
                .age(OBJ_AGE);

        responseDto1 = new ResponseStudentDto()
                .id(1L)
                .document("12345678")
                .name("Juan")
                .lastName("Perez")
                .status(true)
                .age(OBJ_AGE);
        responseDto2 = new ResponseStudentDto()
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
    void getAllStudent() {
        when(studentUseCase.getAllStudents()).thenReturn(studentResponseFlux);

        webTestClient.get().uri(STUDENT_API)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResponseStudentDto.class)
                .hasSize(2)
                .contains(responseDto1, responseDto2);

        verify(studentUseCase).getAllStudents();
    }

    @Test
    @DisplayName("method get all actives students test")
    void getAllStudentActivesTest() {
        when(studentUseCase.getAllStudentsActives()).thenReturn(studentResponseFlux);

        webTestClient.get().uri(STUDENT_API  + "/actives")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResponseStudentDto.class)
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
                .expectBody(ResponseStudentDto.class)
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
                .expectBody(ResponseStudentDto.class)
                .isEqualTo(responseDto2);

        verify(studentUseCase).createStudent(requestDto2);
    }
}