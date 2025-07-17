package com.maven.student.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuración de seguridad para la aplicación.
 * Permite autenticación básica y define las rutas públicas y protegidas.
 *
 * @author Joseph Magallanes
 * @since 2025-07-17
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${app.security.username}")
    private String username;

    @Value("${app.security.password}")
    private String password;

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     * Deshabilita CSRF, permite acceso a rutas específicas y requiere autenticación para el resto.
     *
     * @param http la configuración de seguridad del servidor HTTP
     * @return la cadena de filtros de seguridad configurada
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "/actuator/health" // Si usas actuator
                        ).permitAll()
                        // Permitir acceso libre a actuator health endpoint (opcional)
                        .pathMatchers("/actuator/health").permitAll()
                        // Todas las demás rutas requieren autenticación
                        .anyExchange().authenticated()
                )
                .httpBasic(httpBasicSpec -> {
                }) // Habilitar autenticación HTTP Basic
                .build();
    }

    /**
     * Configura un servicio de detalles de usuario reactivo con un usuario predefinido.
     * Utiliza BCrypt para codificar la contraseña del usuario.
     *
     * @return un MapReactiveUserDetailsService con el usuario configurado
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        final UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .roles("USER")
                .build();

        return new MapReactiveUserDetailsService(user);
    }

    /**
     * Configura un codificador de contraseñas utilizando BCrypt.
     * Este codificador se utiliza para encriptar las contraseñas de los usuarios.
     *
     * @return un PasswordEncoder que utiliza BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
