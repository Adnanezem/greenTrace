package com.greentracer.app.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {

    private ErrorResponse error;

    @BeforeEach
    public void setup() {
        error = new ErrorResponse("testMsg", 1);
    }

    @Test
    public void setAndGetMsg() {
        String msg = "newTestMsg";
        error.setMessage(msg);
        assertEquals(msg, error.getMessage());
    }

    @Test
    public void setAndGetErrorType() {
        int errorType = 2;
        error.setStatus(errorType);
        assertEquals(errorType, error.getStatus());
    }

    @Test
    public void constructor_setsValues() {
        String msg = "testMsg";
        int errorType = 1;
        ErrorResponse error = new ErrorResponse(msg, errorType);

        assertEquals(msg, error.getMessage());
        assertEquals(errorType, error.getStatus());
    }
}