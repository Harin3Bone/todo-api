package com.mock.todo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;


@Configuration
@SecurityScheme(
        paramName = "userkey",
        description = "user key to authenticate",
        name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.APIKEY)
@OpenAPIDefinition(info = @Info(
        title = "Todo Application",
        description = "Documentation for Todo Application",
        version = "0.1.0"),
        security = { @SecurityRequirement(name = "Authorization") })
public class OpenAPIConfig {
}
