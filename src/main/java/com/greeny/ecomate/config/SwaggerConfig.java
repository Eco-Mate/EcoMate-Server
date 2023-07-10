package com.greeny.ecomate.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "EcoMate API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "EcoMate API 명세서";

    @Bean
    public OpenAPI swaggerSpec() {
        return new OpenAPI()
                .info(new Info().title(API_NAME).version(API_VERSION).description(API_DESCRIPTION));
    }

}
