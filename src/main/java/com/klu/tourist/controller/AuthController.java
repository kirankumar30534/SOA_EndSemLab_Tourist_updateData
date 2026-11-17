package com.klu.tourist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klu.tourist.dto.LoginRequest;
import com.klu.tourist.dto.LoginResponse;
import com.klu.tourist.security.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Authentication & Token Generation", description = "Public endpoint to authenticate and obtain a JWT Bearer token for accessing protected APIs")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @SecurityRequirements // Public endpoint, overrides global security in Swagger UI
    @Operation(
        summary = "Generate JWT Token",
        description = "Authenticates user credentials and generates a signed JWT token valid for 60 minutes. Use credentials: username='admin', password='admin123' (or 'hotel_staff'/'password123'). Copy the returned token into Swagger UI's Authorize dialog."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token generated successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        // Valid demo credentials for lab evaluation
        boolean isValidUser = ("admin".equals(request.getUsername()) && "admin123".equals(request.getPassword()))
                || ("hotel_staff".equals(request.getUsername()) && "password123".equals(request.getPassword()));

        if (isValidUser) {
            String token = jwtService.generateToken(request.getUsername());
            LoginResponse response = new LoginResponse(
                    "Login successful! Copy this token and click 'Authorize' in Swagger UI.",
                    token,
                    request.getUsername(),
                    jwtService.getExpirationTime()
            );
            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("{\"success\": false, \"message\": \"Invalid username or password. Use admin/admin123\"}");
    }
}
