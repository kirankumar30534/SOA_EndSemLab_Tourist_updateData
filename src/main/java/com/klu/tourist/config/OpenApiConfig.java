package com.klu.tourist.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Tourist Records & Hotel Check-In Management System API",
        version = "1.0.0",
        description = "End Semester Lab Exam Project: SOA / Cloud & Distributed Systems. Demonstrates Token Validation, Hotel Check-In System, Updating Tourist Destination & Budget, and Testing via Swagger UI.",
        contact = @Contact(
            name = "SOA End Lab Exam Evaluation",
            email = "student@klu.edu.in"
        )
    ),
    security = @SecurityRequirement(name = "BearerAuth")
)
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Enter JWT Bearer token obtained from POST /api/auth/login endpoint. Example format: Bearer <your_jwt_token>"
)
public class OpenApiConfig {
}
