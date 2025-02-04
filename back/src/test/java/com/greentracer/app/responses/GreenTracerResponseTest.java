package com.greentracer.app.responses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GreenTracerResponseTest {

    @Test
    void testGreenTracerResponseConstructorAndGetters() {
        GreenTracerResponse greenTracerResponse = new GreenTracerResponse("Test Message", 200);

        assertEquals("Test Message", greenTracerResponse.getMessage());
        assertEquals(200, greenTracerResponse.getStatus());
    }

    @Test
    void testGreenTracerResponseSetters() {
        GreenTracerResponse greenTracerResponse = new GreenTracerResponse("Test Message", 200);
        greenTracerResponse.setMessage("New Test Message");
        greenTracerResponse.setStatus(404);

        assertEquals("New Test Message", greenTracerResponse.getMessage());
        assertEquals(404, greenTracerResponse.getStatus());
    }
}