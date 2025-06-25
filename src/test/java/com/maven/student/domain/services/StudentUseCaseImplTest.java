package com.maven.student.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.openapi.generate.model.RequestDto;
import com.openapi.generate.model.ResponseDto;
import com.maven.student.domain.model.StudentEntity;
import com.maven.student.domain.repository.StudentRepositoryReactive;
import com.maven.student.infrastructure.exception.types.StudentAlreadyExistsException;
import com.maven.student.infrastructure.util.StudentMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class StudentUseCaseImplTest {

    @Mock
    private StudentRepositoryReactive repositoryReactive;
    @Mock
    private StudentMapper studentMapper;
    @InjectMocks
    private StudentUseCaseImpl studentUseCaseImpl;

    private RequestDto requestDto1;
    private ResponseDto responseDto1;
    private ResponseDto responseDto2;

    private StudentEntity student1;
    private StudentEntity student2;

    public static final int OBJ_AGE = 25;

    @BeforeEach
    void setUp() {
        requestDto1 = new RequestDto()
                .id(null)
                .document("12345678")
                .name("Juan")
                .lastName("Perez")
                .status(true)
                .age(OBJ_AGE);

        student1 = new StudentEntity(1L, "12345678",
                "Juan", "Perez", true, OBJ_AGE);
        student2 = new StudentEntity(2L, "12345679",
                "Julia", "Valencia", true, OBJ_AGE);

        responseDto1 = new ResponseDto()
                .id(1L)
                .document("12345678")
                .name("Juan")
                .lastName("Perez")
                .status(true)
                .age(OBJ_AGE);
        responseDto2 = new ResponseDto()
                .id(2L)
                .document("12345678")
                .name("Julia")
                .lastName("Romano")
                .status(true)
                .age(OBJ_AGE);
    }

    @Test
    void testGetAllStudentsActives() {
        // Arrange
        when(repositoryReactive.findByStatusTrue()).thenReturn(Flux.just(student1, student2));
        when(studentMapper.studentToResponse(any())).thenReturn(responseDto1, responseDto2);

        // Act & Assert
        StepVerifier.create(studentUseCaseImpl.getAllStudentsActives())
                .expectNext(responseDto1, responseDto2)
                .verifyComplete();
    }

    @Test
    void testCreateStudent_WhenStudentDoesNotExist() {
        // Arrange
        when(repositoryReactive.findByDocument(requestDto1.getDocument())).thenReturn(Mono.empty());
        when(studentMapper.requestToStudent(requestDto1)).thenReturn(student1);
        when(repositoryReactive.save(student1)).thenReturn(Mono.just(student1));
        when(studentMapper.studentToResponse(student1)).thenReturn(responseDto1);

        // Act & Assert
        StepVerifier.create(studentUseCaseImpl.createStudent(requestDto1))
                .expectNext(responseDto1)
                .verifyComplete();
    }

    @Test
    void testCreateStudent_WhenStudentExists() {
        // Arrange
        when(repositoryReactive.findByDocument(requestDto1.getDocument())).thenReturn(Mono.just(student1));

        // Act & Assert
        StepVerifier.create(studentUseCaseImpl.createStudent(requestDto1))
                .expectErrorMatches(throwable ->
                        throwable instanceof StudentAlreadyExistsException
                                && throwable.getMessage().contains("Student exists with document number"))
                .verify();
    }
}