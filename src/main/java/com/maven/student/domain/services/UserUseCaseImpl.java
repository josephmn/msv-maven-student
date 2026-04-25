package com.maven.student.domain.services;

import static com.maven.student.infrastructure.util.ConstantsConfig.AUTH_ALREADY_EXISTS;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.maven.student.application.usecases.UserUseCase;
import com.maven.student.domain.model.UserEntity;
import com.maven.student.domain.repository.UserRepositoryReactive;
import com.maven.student.infrastructure.exception.types.AuthException;
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
    public Mono<LoginResponse> login(LoginRequest request, String ruc) {
        final Map<String, String> errors = new HashMap<>();
        return repositoryReactive.findByUsername(request.getUsername())
                .switchIfEmpty(Mono.defer(() -> {
                    errors.put("User", "Not found");
                    return Mono.error(new AuthException(errors));
                }))
                .flatMap(userEntity -> {
                    if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
                        errors.put("Password", "Incorrect");
                    }
                    if (!Objects.equals(userEntity.getRuc(), ruc)) {
                        errors.put("RUC", "Invalid");
                    }
                    if (!Boolean.TRUE.equals(userEntity.getEnabled())) {
                        errors.put("User", "Not enabled");
                    }

                    if (!errors.isEmpty()) {
                        return Mono.error(new AuthException(errors));
                    }

                    final String token = jwtUtil.generateToken(userEntity.getUsername(), userEntity.getRole());
                    final LoginResponse response = new LoginResponse();
                    response.setAccessToken(token);
                    response.setTokenType("Bearer");
                    response.setExpiresIn(jwtUtil.getExpirationMinutes());
                    response.setUsername(userEntity.getUsername());
                    response.setRoles(Collections.singletonList(userEntity.getRole()));
                    return Mono.just(response);
                });
    }

    @Override
    public Mono<ResponseUserDto> register(RegisterRequest request) {
        final Map<String, String> errors = new HashMap<>();

        return Mono.zip(
                repositoryReactive.existsByUsername(request.getUsername()),
                repositoryReactive.existsByEmail(request.getEmail()),
                repositoryReactive.existsByRuc(request.getRuc())
        ).flatMap(results -> {
            if (results.getT1()) {
                errors.put("Username", AUTH_ALREADY_EXISTS);
            }
            if (results.getT2()) {
                errors.put("Email", AUTH_ALREADY_EXISTS);
            }
            if (results.getT3()) {
                errors.put("RUC", AUTH_ALREADY_EXISTS);
            }

            if (!errors.isEmpty()) {
                return Mono.error(new AuthException(errors));
            }

            final UserEntity user = new UserEntity();
            user.setRuc(request.getRuc());
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
