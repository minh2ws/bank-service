package com.minhtn.bankservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Bean
    public OpenAPI customOpenApi() {
        String securitySchemeName = "bearerAuth";

        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("https://api.bankservice.site"));
        if (!activeProfile.equals("prod")) {
            servers.add(new Server().url("http://localhost:8080"));
        }

        return new OpenAPI()
                .servers(servers)
                .info(new Info().title("Bank Service")
                        .description("Open API 3.0")
                        .contact(new Contact()
                                .url("https://bankservice.site"))
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
