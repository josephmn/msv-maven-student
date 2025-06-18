package com.maven.student.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.maven.student.domain.model.StudentEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * StudentRepositoryReactive.
 *
 * @author Joseph Magallanes
 * @since 2025-06-16
 */
@Repository
public interface StudentRepositoryReactive extends ReactiveCrudRepository<StudentEntity, Long> {
    Flux<StudentEntity> findByStatusTrue();
    Mono<StudentEntity> findByDocument(String documentNumber);
}
