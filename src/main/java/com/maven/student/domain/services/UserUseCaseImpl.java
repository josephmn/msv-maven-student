package com.maven.student.domain.services;

import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.maven.student.application.usecases.UserUseCase;
import com.maven.student.domain.model.UserEntity;
import com.maven.student.domain.repository.UserRepositoryReactive;
import com.maven.student.infrastructure.jwt.JwtUtil;
import com.maven.student.infrastructure.util.UserMapper;
import com.openapi.generate.model.LoginRequest;
import com.openapi.generate.model.LoginResponse;
import com.openapi.generate.model.RegisterRequest;
import com.openapi.generate.model.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para la gestión de usuarios.
 * Proporciona métodos para iniciar sesión, registrar un nuevo usuario y buscar un usuario por nombre de usuario.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepositoryReactive repositoryReactive;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    public Mono<LoginResponse> login(LoginRequest request) {
        return repositoryReactive.findByUsername(request.getUsername())
                .filter(userEntity -> passwordEncoder.matches(request.getPassword(), userEntity.getPassword()))
                .filter(UserEntity::getEnabled)
                .map(userEntity -> {
                    final String token = jwtUtil.generateToken(userEntity.getUsername(), userEntity.getRole());
                    final LoginResponse response = new LoginResponse();
                    response.setToken(token);
                    response.setTokenType("Bearer");
                    response.setUsername(userEntity.getUsername());
                    response.setRoles(Collections.singletonList(userEntity.getRole()));
                    return response;
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }

    @Override
    public Mono<ResponseUserDto> register(RegisterRequest request) {
        return repositoryReactive.existsByUsername(request.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Username already exists"));
                    }
                    return repositoryReactive.existsByEmail(request.getEmail());
                })
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Email already exists"));
                    }

                    final UserEntity user = new UserEntity();
                    user.setUsername(request.getUsername());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setEmail(request.getEmail());
                    user.setRole(request.getRole());
                    user.setEnabled(true);

                    return repositoryReactive.save(user)
                            .map(userMapper::userToResponse);
                });
    }

    @Override
    public Mono<ResponseUserDto> findByUsername(String username) {
        return repositoryReactive.findByUsername(username)
                .map(userMapper::userToResponse);
    }
}
