package com.greentracer.app.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.greentracer.app.utils.JwtTokenUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtre d'authentification.
 */
@Component
@Order(1)
public class AuthFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private static final String[] WHITELIST = {"/", "/users/register", "/users/login" };
    private static final List<String> WHITELIST_URLS = Arrays.asList(WHITELIST);
    private static final String AUTH_HEADER = "Authorization";
    private static final String LOGIN_HEADER = "U-Login";

    private final JwtTokenUtil jwtHelper;

    @Autowired
    public AuthFilter(JwtTokenUtil jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI().replace(request.getContextPath(), "");
        logger.info("request URL: {}", url);

        if (isInWhiteList(url)) {
            chain.doFilter(request, response);
            return;
        }
        String authToken = request.getHeader(AUTH_HEADER);
        String login = request.getHeader(LOGIN_HEADER);

        boolean isAuthenticated = (authToken == null) ? false : jwtHelper.validateToken(authToken, login);

        if (isAuthenticated) {
            chain.doFilter(request, response);
            return;
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            return;
        }
    }

    private Boolean isInWhiteList(String url) {
        Boolean res = WHITELIST_URLS.contains(url);
        logger.debug(res.toString());
        return res;
    }
}
