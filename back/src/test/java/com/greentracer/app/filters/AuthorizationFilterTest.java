package com.greentracer.app.filters;

import com.greentracer.app.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class AuthorizationFilterTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private AutorizationFilter autorizationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        autorizationFilter = new AutorizationFilter(jwtTokenUtil);
    }

    @Test
    void doFilter_withNoUserAttribute_shouldNotCheckAuthorization() throws Exception {
        when(request.getAttribute("user")).thenReturn(null);

        autorizationFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(request, never()).getHeader("Authorization");
    }

    @Test
    void whenUrlIsUsersAndSecondPartEqualsUser_thenContinueChain() throws Exception {
        when(request.getRequestURI()).thenReturn("/users/user");
        when(request.getHeader("U-Login")).thenReturn("user");

        autorizationFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }

    @Test
    void whenUrlIsCarbonAndSecondPartEqualsUserOrCompute_thenContinueChain() throws Exception {
        when(request.getRequestURI()).thenReturn("/carbon/user");
        when(request.getHeader("U-Login")).thenReturn("user");

        autorizationFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }


}