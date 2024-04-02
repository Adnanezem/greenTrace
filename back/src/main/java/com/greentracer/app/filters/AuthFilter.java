package com.greentracer.app.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.greentracer.app.databd.Gestion;

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
    private static Logger logger = LoggerFactory.getLogger(Gestion.class);
    private static final String[] WHITELIST = {"/", "/users/register", "/users/login"};
    private static final List<String> WHITELIST_URLS = Arrays.asList(WHITELIST);
    private static final String AUTH_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        logger.info("request URI: {}", request.getRequestURI());
        if (isInWhiteList(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        String authToken = request.getHeader(AUTH_HEADER);

        // Valide le token TODO
        boolean isAuthenticated = isValidAuthToken(authToken);

        if (isAuthenticated) {
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isInWhiteList(String url) {
        return WHITELIST_URLS.contains(url);
    }

    // Méthode factice pour valider le token (à remplacer par votre logique
    // d'authentification réelle)
    private boolean isValidAuthToken(String authToken) {
        return true;
        // return authToken != null && !authToken.isEmpty();
    }
}
