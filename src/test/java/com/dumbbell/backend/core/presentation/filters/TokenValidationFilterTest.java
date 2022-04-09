package com.dumbbell.backend.core.presentation.filters;

import com.dumbbell.backend.core.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TokenValidationFilterTest {
    private static final String BEARER_TOKEN = "Bearer token";

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private TokenValidationFilter sut;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void doFilterInternal_givenNoAuthorization_shouldNotAddAttribute() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        sut.doFilterInternal(request, response, filterChain);

        verify(request, never()).setAttribute(anyString(), any());
    }

    @Test
    void doFilterInternal_givenNonBearerAuthorization_shouldNotAddAttribute() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("wrong token");

        sut.doFilterInternal(request, response, filterChain);

        verify(request, never()).setAttribute(anyString(), any());
    }

    @Test
    void doFilterInternal_givenNonValid_shouldNotAddAttribute() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(BEARER_TOKEN);
        when(jwtUtils.validateToken("token")).thenReturn(false);

        sut.doFilterInternal(request, response, filterChain);

        verify(request, never()).setAttribute(anyString(), any());
    }

    @Test
    void doFilterInternal_givenAValid_shouldAddAttribute() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(BEARER_TOKEN);
        when(jwtUtils.validateToken("token")).thenReturn(true);

        sut.doFilterInternal(request, response, filterChain);

        verify(request).setAttribute("AuthorizationToken", "token");
    }
}