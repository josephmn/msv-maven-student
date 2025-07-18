package com.maven.student.infrastructure.jwt;

import static com.maven.student.infrastructure.util.ConstantsConfig.LENGTH_SUBSTRING_TOKEN;

import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import com.maven.student.application.usecases.UserUseCase;
import reactor.core.publisher.Mono;

/**
 * JwtAuthenticationFilter.
 * This filter intercepts requests to authenticate users based on JWT tokens.
 * It extracts the token from the request, validates it, and sets the authentication context.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final UserUseCase userUseCase;

    /**
     * Constructor for JwtAuthenticationFilter.
     *
     * @param jwtUtil the utility class for JWT operations
     * @param userUseCase the use case for user operations
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, @Lazy UserUseCase userUseCase) {
        this.jwtUtil = jwtUtil;
        this.userUseCase = userUseCase;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final String token = getTokenFromRequest(exchange);

        if (token == null) {
            return chain.filter(exchange);
        }

        return jwtUtil.getUsernameFromToken(token)
                .flatMap(username -> userUseCase.findByUsername(username)
                        .flatMap(user -> jwtUtil.validateToken(token, username)
                                .filter(Boolean::booleanValue)
                                .flatMap(valid -> jwtUtil.getRoleFromToken(token)
                                        .map(role -> {
                                            final var authorities = List.of(
                                                    new SimpleGrantedAuthority("ROLE_" + role));
                                            final var authentication = new UsernamePasswordAuthenticationToken(
                                                    username, null, authorities);
                                            return ReactiveSecurityContextHolder
                                                    .withAuthentication(authentication);
                                        })
                                )
                        )
                )
                .flatMap(authContext -> chain.filter(exchange).contextWrite(authContext))
                .switchIfEmpty(chain.filter(exchange))
                .onErrorResume(error -> chain.filter(exchange));
    }

    private String getTokenFromRequest(ServerWebExchange exchange) {
        final String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(LENGTH_SUBSTRING_TOKEN);
        }
        return null;
    }
}
