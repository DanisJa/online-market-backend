package com.onlinemarket.rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "${configuration.swagger.app}", version = "1.0.0", description = "${configuration.swagger.description}"),
        security = {@SecurityRequirement(name="key")},
        servers = {@Server(url = "/", description = "Default Server URL")}
)
public class SwaggerConfig {}