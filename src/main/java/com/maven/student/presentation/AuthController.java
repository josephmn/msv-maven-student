package com.maven.student.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import com.maven.student.application.usecases.UserUseCase;
import com.openapi.generate.api.AuthApi;
import com.openapi.generate.model.LoginRequest;
import com.openapi.generate.model.LoginResponse;
import com.openapi.generate.model.RegisterRequest;
import com.openapi.generate.model.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * AuthController.
 * This controller handles authentication-related requests such as user login and registration.
 * It implements the AuthApi interface generated from OpenAPI specifications.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserUseCase userUseCase;

    @Override
    public Mono<ResponseEntity<LoginResponse>> loginUser(
            Mono<LoginRequest> loginRequest, ServerWebExchange exchange) {
        return loginRequest.flatMap(dto ->
                this.userUseCase.login(dto)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
        );
    }

    @Override
    public Mono<ResponseEntity<ResponseUserDto>> registerUser(
            Mono<RegisterRequest> registerRequest, ServerWebExchange exchange) {
        return registerRequest.flatMap(dto ->
                this.userUseCase.register(dto)
                .map(userEntity -> ResponseEntity.status(HttpStatus.CREATED).body(userEntity))
                .onErrorReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build())
        );
    }
}
