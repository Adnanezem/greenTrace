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

        String user = request.getHeader("U-Login");


        //Accessing a place where we don't need to be connected
        if(user == null){
            chain.doFilter(request, response);
            return;
        }


        String[] url = UrlUtils.getUrlParts((HttpServletRequest) request);
    
        //Dans les faits, on doit tester la même chose, en théorie, c'est peu probable
        if (Stream.of(RESOURCES_WITH_LIMITATIONS).anyMatch(pattern -> UrlUtils.matchRequest(request, pattern))) {
            switch (url[0]) {
                case "users" -> {
                    if(url[1].equals(user)) {
                        chain.doFilter(request, response);
                        return;
                    } else {
                        response.addHeader("Error", "When requesting users.");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden\nAccessing profile of other user.");
                        return;
                    }
                }

                case "carbon" -> {
                    if(url[1].equals(user) || url[1].equals("compute")) {
                        chain.doFilter(request, response);
                        return;
                    } else {
                        response.addHeader("Error", "When requesting carbon.");
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden\nAccessing profile of other user.");
                        return;
                    }
                }
                default -> {
                    break;
                }
            }
        } else {
            chain.doFilter(request, response);
            return;
        }
        response.addHeader("Error", url.toString());
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while parsing url");
    }
}
