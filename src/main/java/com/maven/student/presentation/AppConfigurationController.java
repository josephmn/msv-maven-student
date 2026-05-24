package com.maven.student.presentation;

import com.maven.student.infrastructure.config.ServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * AppConfigurationController.
 * This controller handles App Configuration values request.
 *
 * @author Joseph Magallanes
 * @since 2026-05-23
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppConfigurationController {

    private final ServiceProperties properties;

    @GetMapping("/app-config")
    public Mono<ResponseEntity<List<ServiceProperties.Company>>> getAppConfiguration() {
        List<ServiceProperties.Company> companyList = properties.getCompany();
        if (companyList != null && !companyList.isEmpty()) {
            return Mono.just(ResponseEntity.ok(companyList));
        }
        return Mono.just(ResponseEntity.notFound().build());
    }
}
