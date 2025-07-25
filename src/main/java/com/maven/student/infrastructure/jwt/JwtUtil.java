package com.maven.student.infrastructure.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

/**
 * JwtUtil.
 * This utility class provides methods for generating and validating JWT tokens.
 * It uses a secret key and expiration time defined in the application properties.
 *
 * @author Joseph Magallanes
 * @since 2025-07-18
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generates a JWT token for the given username and role.
     *
     * @param username the username for which the token is generated
     * @param role the role associated with the user
     * @return a JWT token as a String
     */
    public String generateToken(String username, String role) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param claims the username for which the token is generated
     * @param subject the subject of the token (usually the username)
     * @return a JWT token as a String
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Mono<String> getUsernameFromToken(String token) {
        return Mono.fromCallable(() -> getClaimFromToken(token, Claims::getSubject));
    }

    public Mono<String> getRoleFromToken(String token) {
        return Mono.fromCallable(() -> getClaimFromToken(token, claims -> claims.get("role", String.class)));
    }

    public Mono<Date> getExpirationDateFromToken(String token) {
        return Mono.fromCallable(() -> getClaimFromToken(token, Claims::getExpiration));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Mono<Boolean> isTokenExpired(String token) {
        return getExpirationDateFromToken(token)
                .map(expiration -> expiration.before(new Date()));
    }

    /**
     * Validates the JWT token by checking if it is expired and if the username matches.
     *
     * @param token the JWT token to validate
     * @param username the username to check against the token
     * @return a Mono that emits true if the token is valid, false otherwise
     */
    public Mono<Boolean> validateToken(String token, String username) {
        return getUsernameFromToken(token)
                .flatMap(tokenUsername ->
                        isTokenExpired(token)
                                .map(expired -> tokenUsername.equals(username) && !expired)
                );
    }
}
