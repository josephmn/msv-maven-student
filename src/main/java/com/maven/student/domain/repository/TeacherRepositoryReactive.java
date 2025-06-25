package com.maven.student.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.maven.student.domain.model.TeacherEntity;
import reactor.core.publisher.Mono;

/**
 * TeacherRepositoryReactive.
 * This interface extends ReactiveCrudRepository to provide reactive CRUD operations for TeacherEntity.
 *
 * @author Joseph Magallanes
 * @since 2025-06-25
 */
@Repository
public interface TeacherRepositoryReactive extends ReactiveCrudRepository<TeacherEntity, Long> {
    Mono<TeacherEntity> findByDocument(String documentNumber);
}
