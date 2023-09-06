package com.app.securityexample.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenApiConfig() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(AUTHORIZATION, new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .description("Api Key access")
                    .in(SecurityScheme.In.HEADER)
                    .name(AUTHORIZATION)
                ))
            .security(Collections.singletonList(
                new SecurityRequirement().addList("api_key")))
            .info(new Info().title("Security Issue")
                .version("v1"));
    }
}
