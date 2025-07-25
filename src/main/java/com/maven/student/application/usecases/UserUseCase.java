package com.maven.student.application.usecases;

import com.openapi.generate.model.LoginRequest;
import com.openapi.generate.model.LoginResponse;
import com.openapi.generate.model.RegisterRequest;
import com.openapi.generate.model.ResponseUserDto;
import reactor.core.publisher.Mono;

/**
 * UserUseCase.
 * This interface defines methods for user authentication and registration.
 * It uses reactive programming with Project Reactor's Mono type.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
public interface UserUseCase {
    Mono<LoginResponse> login(LoginRequest request);
    Mono<ResponseUserDto> register(RegisterRequest request);
    Mono<ResponseUserDto> findByUsername(String username);
}
