package io.library.library_3.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SecurityScheme(name = "Authorization", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@OpenAPIDefinition(info = @Info(title = "Library", description = "OpenAPI documentation for Library 3.0 project."
        + "\n\n Authorization is done through Jwt bearer tokens.", version = "3.0"), servers = @Server(url = "http://localhost:8080", description = "Dev ENV"))
public class OpenApiConfig {

}
