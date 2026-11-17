package com.klu.tourist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing JWT token")
public class LoginResponse {

    @Schema(description = "Status message", example = "Authentication successful")
    private String message;

    @Schema(description = "Generated JWT Token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "Token Type", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "Authenticated Username", example = "admin")
    private String username;

    @Schema(description = "Token validity in milliseconds", example = "3600000")
    private long expiresInMs;

    public LoginResponse() {
    }

    public LoginResponse(String message, String token, String username, long expiresInMs) {
        this.message = message;
        this.token = token;
        this.username = username;
        this.expiresInMs = expiresInMs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExpiresInMs() {
        return expiresInMs;
    }

    public void setExpiresInMs(long expiresInMs) {
        this.expiresInMs = expiresInMs;
    }
}
