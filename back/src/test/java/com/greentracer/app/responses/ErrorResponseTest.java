package com.greentracer.app.responses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTest {

    @Test
    public void testErrorResponseConstructorAndGetters() {
        ErrorResponse errorResponse = new ErrorResponse("Test Error", 404);

        assertEquals("Test Error", errorResponse.getMessage());
        assertEquals(404, errorResponse.getStatus());
    }

    @Test
    public void testErrorResponseSetters() {
        ErrorResponse errorResponse = new ErrorResponse("Test Error", 404);
        errorResponse.setMessage("New Test Error");
        errorResponse.setStatus(500);

        assertEquals("New Test Error", errorResponse.getMessage());
        assertEquals(500, errorResponse.getStatus());
    }
}