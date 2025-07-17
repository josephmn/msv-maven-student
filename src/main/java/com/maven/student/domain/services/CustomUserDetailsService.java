//package com.maven.student.domain.services;
//
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class CustomUserDetailsService implements ReactiveUserDetailsService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final Map<String, UserDetails> users;
//
//    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//        this.users = new HashMap<>();
//        initializeUsers();
//    }
//
//    private void initializeUsers() {
//        // Admin user
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin123"))
//                .roles("ADMIN", "USER")
//                .build();
//
//        // Regular user
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("user123"))
//                .roles("USER")
//                .build();
//
//        // Student user
//        UserDetails student = User.builder()
//                .username("student")
//                .password(passwordEncoder.encode("student123"))
//                .roles("STUDENT")
//                .build();
//
//        users.put("admin", admin);
//        users.put("user", user);
//        users.put("student", student);
//    }
//
//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        UserDetails user = users.get(username);
//        if (user != null) {
//            return Mono.just(user);
//        }
//        return Mono.error(new UsernameNotFoundException("User not found: " + username));
//    }
//
//    // Método para agregar usuarios dinámicamente
//    public Mono<Void> addUser(String username, String password, String... roles) {
//        UserDetails newUser = User.builder()
//                .username(username)
//                .password(passwordEncoder.encode(password))
//                .roles(roles)
//                .build();
//
//        users.put(username, newUser);
//        return Mono.empty();
//    }
//
//    // Método para eliminar usuarios
//    public Mono<Void> removeUser(String username) {
//        users.remove(username);
//        return Mono.empty();
//    }
//}
