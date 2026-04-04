package com.vishal.finance_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    private static final String BASIC_SCHEME = "basicAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Finance Backend API")
                        .description("Use Authorize and enter username/password (HTTP Basic).")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList(BASIC_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(BASIC_SCHEME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Spring Security HTTP Basic (same as Postman Basic Auth).")));
    }
}
