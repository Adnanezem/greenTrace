package com.greentracer.app.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.greentracer.app.databd.Gestion;
import com.greentracer.app.utils.JwtTokenUtil;
import com.greentracer.app.utils.UrlUtils;

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
    private static final String[] WHITELIST = {"/", "/users/register", "/users/login"};
    private static final List<String> WHITELIST_URLS = Arrays.asList(WHITELIST);
    private static final String AUTH_HEADER = "Authorization";

    private JwtTokenUtil jwtHelper = new JwtTokenUtil();


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

        // Valide le token TODO

        // String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        // logger.info("request body: {}", body);
        boolean isAuthenticated = (authToken == null) ? false : jwtHelper.validateToken(authToken, null);

        if (true /* utiliser isAuth à la place bien sur */) {
            chain.doFilter(request, response);
            return;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isInWhiteList(String url) {
        return WHITELIST_URLS.contains(url);
    }
}
