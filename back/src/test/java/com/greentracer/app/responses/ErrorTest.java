package com.greentracer.app.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {

    private Error error;

    @BeforeEach
    public void setup() {
        error = new Error("testMsg", 1);
    }

    @Test
    public void setAndGetMsg() {
        String msg = "newTestMsg";
        error.setMsg(msg);
        assertEquals(msg, error.getMsg());
    }

    @Test
    public void setAndGetErrorType() {
        int errorType = 2;
        error.setErrorType(errorType);
        assertEquals(errorType, error.getErrorType());
    }

    @Test
    public void constructor_setsValues() {
        String msg = "testMsg";
        int errorType = 1;
        Error error = new Error(msg, errorType);

        assertEquals(msg, error.getMsg());
        assertEquals(errorType, error.getErrorType());
    }
}