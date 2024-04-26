package com.greentracer.app.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class UrlUtilsTest {



    @Test
    public void testPrivateConstructor() {
        assertThrows(UnsupportedOperationException.class, () -> {
            try {
                Constructor<UrlUtils> constructor = UrlUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw (RuntimeException) e.getTargetException();
            }
        });
    }

    @Test
    public void testGetUrlParts() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/contextPath/part1/part2");
        request.setContextPath("/contextPath");

        String[] urlParts = UrlUtils.getUrlParts(request);

        assertArrayEquals(new String[]{"part1", "part2"}, urlParts);
    }

    @Test
    public void testGetUrlInfo() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/contextPath/part1/part2");
        request.setContextPath("/contextPath");
        request.setMethod("GET");

        String[] urlInfo = UrlUtils.getUrlInfo(request);

        assertArrayEquals(new String[]{"GET", "part1", "part2"}, urlInfo);
    }

    @Test
    public void testGetUrlEnd() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/contextPath/part1/part2");
        request.setContextPath("/contextPath");

        String urlEnd = UrlUtils.getUrlEnd(request, 1);

        assertEquals("/part2", urlEnd);
    }

    @Test
    public void testMatchRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/contextPath/part1/part2");
        request.setContextPath("/contextPath");
        request.setMethod("GET");

        assertTrue(UrlUtils.matchRequest(request, new String[]{"GET", "part1", "part2"}));
        assertFalse(UrlUtils.matchRequest(request, new String[]{"POST", "part1", "part2"}));
        assertTrue(UrlUtils.matchRequest(request, new String[]{"GET", "*", "part2"}));
        assertFalse(UrlUtils.matchRequest(request, new String[]{"GET", "part1"}));
    }
}