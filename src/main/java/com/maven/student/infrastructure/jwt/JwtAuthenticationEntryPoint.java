package com.maven.student.infrastructure.jwt;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

/**
 * JwtAuthenticationEntryPoint.
 * This class handles unauthorized access attempts by returning a 401 Unauthorized response
 * with a JSON body containing error details.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        final var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        response.beforeCommit(() -> {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return Mono.empty();
        });

        final String body = createErrorBody();
        final DataBuffer buffer = response.bufferFactory().wrap(body.getBytes());

        return response.writeWith(Mono.just(buffer));
    }

    private String createErrorBody() {
        try {
            final Map<String, Object> errorAttributes = new HashMap<>();
            errorAttributes.put("error", "Unauthorized");
            errorAttributes.put("message", "Authentication required");
            errorAttributes.put("timestamp", System.currentTimeMillis());

            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(errorAttributes);
        }
        catch (JsonProcessingException e) {
            return "{\"error\":\"Unauthorized\",\"message\":\"Authentication required\"}";
        }
    }
}
