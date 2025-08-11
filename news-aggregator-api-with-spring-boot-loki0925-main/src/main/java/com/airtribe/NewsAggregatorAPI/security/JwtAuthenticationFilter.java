package com.airtribe.NewsAggregatorAPI.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.lang.IllegalArgumentException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.util.annotation.NonNull;
import java.io.IOException;
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException ex) {
            SecurityContextHolder.clearContext();
            request.setAttribute("expiredJwtException", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token has expired");
            return;
        } catch (MalformedJwtException ex) {
            SecurityContextHolder.clearContext();
            request.setAttribute("malformedJwtException", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is malformed");
            return;
        } catch (SignatureException ex) {
            SecurityContextHolder.clearContext();
            request.setAttribute("signatureException", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT signature is invalid");
            return;
        } catch (IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            request.setAttribute("illegalArgumentException", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is missing or invalid");
            return;
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            request.setAttribute("generalAuthenticationException", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
            return;
        }

        filterChain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
