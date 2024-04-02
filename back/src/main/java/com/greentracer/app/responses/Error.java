package com.greentracer.app.responses;

/**
 * Réponses d'erreurs.
 */
public class Error implements GreenTracerResponse {
    private String msg;
    private int errorType;

    /**
     * @return le type d'erreur (int)
     */
    public int getErrorType() {
        return errorType;
    }

    /**
     * @param errorType
     */
    public void setErrorType(int errorType) {
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
