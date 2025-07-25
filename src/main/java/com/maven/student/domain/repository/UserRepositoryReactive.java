package com.maven.student.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.maven.student.domain.model.UserEntity;
import reactor.core.publisher.Mono;

/**
 * UserRepositoryReactive.
 * This interface defines methods to interact with the UserEntity in a reactive manner.
 * It extends ReactiveCrudRepository to provide basic CRUD operations.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
public interface UserRepositoryReactive extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);
    Mono<UserEntity> findByEmail(String email);
    Mono<Boolean> existsByUsername(String username);
    Mono<Boolean> existsByEmail(String email);
}
