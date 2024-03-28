package com.greentracer.app.responses;

/**
 * Error response
 */
public class Error implements GreenTracerResponse {
    String msg;
    int errorType;

    public int getError_type() {
        return errorType;
    }

    public void setError_type(int errorType) {
        this.errorType = errorType;
    }

    /**
     * Default constructor.
     * @param msg
     * @param errorType
     */
    public Error(final String msg, final int errorType) {
        this.msg = msg;
        this.errorType = errorType;
    }

    /**
     * @return message from the error response.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * set the message of the Error response.
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
