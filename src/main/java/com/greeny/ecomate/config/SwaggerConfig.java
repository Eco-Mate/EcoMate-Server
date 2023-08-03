package com.greeny.ecomate.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "EcoMate API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "EcoMate API 명세서";

    @Bean
    public OpenAPI swaggerSpec() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");

        SecurityRequirement schemeRequirement = new SecurityRequirement().addList("bearerAuth");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(schemeRequirement))
                .info(new Info().title(API_NAME).version(API_VERSION).description(API_DESCRIPTION));
    }

}
