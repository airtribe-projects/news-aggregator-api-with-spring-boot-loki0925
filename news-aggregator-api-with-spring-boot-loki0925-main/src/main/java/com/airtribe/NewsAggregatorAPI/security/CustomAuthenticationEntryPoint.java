package com.airtribe.NewsAggregatorAPI.security;

import com.airtribe.NewsAggregatorAPI.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        String message = "Authentication error";

        if (request.getAttribute("expiredJwtException") != null) {
            message = "JWT token has expired";
        } else if (request.getAttribute("malformedJwtException") != null) {
            message = "JWT token is malformed";
        } else if (request.getAttribute("signatureException") != null) {
            message = "JWT signature is invalid";
        } else if (request.getAttribute("illegalArgumentException") != null) {
            message = "JWT token is missing or invalid";
        } else if (request.getAttribute("generalAuthenticationException") != null) {
            message = "Authentication error";
        } else if (authException.getMessage().equals("Full authentication is required to access this resource")) {
            message = "Authentication token is missing or invalid";
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpServletResponse.SC_UNAUTHORIZED,
                Collections.singletonList(message),
                "Authentication Failed"
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }


}
