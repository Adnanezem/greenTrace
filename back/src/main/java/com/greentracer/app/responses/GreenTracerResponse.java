package com.greentracer.app.responses;

/**
 * Response Interface.
 */
public class GreenTracerResponse {

    private String message;
    private Integer status;

    public GreenTracerResponse(final String message, final Integer status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }    

}
