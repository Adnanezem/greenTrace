package com.greentracer.app.filters;

import java.io.IOException;
import java.util.stream.Stream;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * An authorization filter.
 */
@Component
@Order(2)
public class AutorizationFilter implements Filter {
    private static final String[][] RESOURCES_WITH_LIMITATIONS = {
        {"GET", "users", "*"},
        {"PUT", "users", "*"},
        {"POST", "carbon", "compute"},
        {"GET", "carbon", "*"},
};

    private final JwtTokenUtil jwtHelper;

    @Autowired
    public AutorizationFilter(JwtTokenUtil jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getAttribute("user") == null) {
            chain.doFilter(request, response);
            return;
        }


        String token = request.getHeader("Authorization").split(" ")[1];
        String[] url = UrlUtils.getUrlParts((HttpServletRequest) request);
    

        if (Stream.of(RESOURCES_WITH_LIMITATIONS).anyMatch(pattern -> UrlUtils.matchRequest(request, pattern))) {
            if(url[0].equals("users")  && !url[1].equals(jwtHelper.getUsernameFromToken(token))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden\nAccessing profile of other user.");
                return; 
            } else {
                chain.doFilter(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while parsing url");
    }
}
