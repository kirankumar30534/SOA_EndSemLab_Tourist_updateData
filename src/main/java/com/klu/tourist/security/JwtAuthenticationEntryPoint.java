package com.klu.tourist.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String json = String.format(
                "{\"success\": false, \"status\": 401, \"error\": \"Unauthorized\", \"message\": \"Token Validation Failed: Request token is missing, invalid, or expired. Please provide Authorization: Bearer <token> in headers.\", \"path\": \"%s\", \"timestamp\": \"%s\"}",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        response.getWriter().write(json);
    }
}
