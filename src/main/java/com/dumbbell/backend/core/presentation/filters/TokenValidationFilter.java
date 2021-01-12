package com.dumbbell.backend.core.presentation.filters;

import com.dumbbell.backend.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenValidationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        if (isValidBearer(authorizationHeader)) {
            final String token = authorizationHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                request.setAttribute("AuthorizationToken", token);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidBearer(String header) {
        return header != null && header.startsWith("Bearer ");
    }
}
