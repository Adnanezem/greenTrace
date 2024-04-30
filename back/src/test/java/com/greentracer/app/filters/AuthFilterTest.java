package com.greentracer.app.filters;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.greentracer.app.utils.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private JwtTokenUtil jwtHelper;

    @InjectMocks
    private AuthFilter authFilter;

    @Test
    void whenUrlIsInWhiteList_thenContinueChain() throws Exception {
        when(request.getRequestURI()).thenReturn("/users/login");
        when(request.getContextPath()).thenReturn("");

        authFilter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(response, never()).sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }

    @Test
    void whenAuthTokenMissing_thenForbidden() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/data");
        when(request.getContextPath()).thenReturn("");
        when(request.getHeader("Authorization")).thenReturn(null);

        authFilter.doFilter(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void whenAuthTokenInvalid_thenForbidden() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/data");
        when(request.getContextPath()).thenReturn("");
        when(request.getHeader("Authorization")).thenReturn("invalid-token");
        when(request.getHeader("U-Login")).thenReturn("user");
        when(jwtHelper.validateToken("invalid-token", "user")).thenReturn(false);

        authFilter.doFilter(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void whenAuthTokenValid_thenContinueChain() throws Exception {
        when(request.getRequestURI()).thenReturn("/api/data");
        when(request.getContextPath()).thenReturn("");
        when(request.getHeader("Authorization")).thenReturn("valid-token");
        when(request.getHeader("U-Login")).thenReturn("user");
        when(jwtHelper.validateToken("valid-token", "user")).thenReturn(true);

        authFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }
}
