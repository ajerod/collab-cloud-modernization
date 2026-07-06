package com.collab.workspace_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI workspaceServiceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workspace Service API")
                        .version("v1")
                        .description("API for managing collaborative workspaces in the collab-cloud-modernization project."));
    }
}