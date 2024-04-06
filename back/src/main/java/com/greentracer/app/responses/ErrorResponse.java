package com.greentracer.app.responses;

/**
 * Réponses d'erreurs.
 */
public class ErrorResponse extends GreenTracerResponse {

    private String message;
    private Integer status;
    
    /**
     * Default constructor.
     * @param message
     * @param status
     */
    public ErrorResponse(final String message, final Integer status) {
        super(message, status);
        this.message = message;
        this.status = status;
    }

    /**
     * @return le type d'erreur (int)
     */
    @Override
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }


    /**
     * @return message from the error response.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * set the message of the Error response.
     * @param message
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
