package com.maven.student.infrastructure.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

/**
 * OpenApiConfig.
 *
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Configuration
@RequiredArgsConstructor
//@OpenAPIDefinition(security = @SecurityRequirement(name = "bearerAuth"))
//@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP,
//        description = "JWT Bearer authentication", bearerFormat = "JWT")
public class OpenApiConfig {

    private final ApplicationProperties applicationProperties;

    /**
     * Configures the OpenAPI documentation for the application.
     *
     * @return OpenAPI object with the configuration.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("School API - OpenAPI 3.0")
                        .description("API gestion school, this API is a simple example of how "
                                + "to use OpenAPI 3.0 with Spring Boot.\n"
                                + "\nSome useful links:\n"
                                + "\n- [The repository in GitHub]("
                                + applicationProperties.getGithub().getUrl() + ")"
                                + "\n- [The source API definition for Banking Customer]("
                                + applicationProperties.getGithub().getDefinition() + ")"
                        )
                        .termsOfService(applicationProperties.getTerms())
                        .contact(new Contact()
                                .name(applicationProperties.getContact().getName())
                                .email(applicationProperties.getContact().getEmail())
                                .url(applicationProperties.getContact().getUrl())
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Find out more about Swagger")
                        .url("http://swagger.io"))
                .servers(
                        Collections.singletonList(
                                new Server().url(applicationProperties.getServers().getUrl()).
                                        description(applicationProperties.getServers().getDescription())
                        )
                )
//                .security(Collections.singletonList(
//                        new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearerAuth")))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Bearer authentication")
                        )
                );
    }
}
