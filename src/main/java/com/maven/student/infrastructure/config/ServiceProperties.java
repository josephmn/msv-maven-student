package com.maven.student.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "service")
@Getter
@Setter
public class ServiceProperties {

    private List<Company> company;

    @Getter
    @Setter
    public static class Company {
        private String name;
        private String code;
        private String ruc;
        private String address;
    }
}
