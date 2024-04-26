package com.greentracer.app.responses;

import java.util.List;

import com.greentracer.app.models.Journee;

/**
 * Response pour Journee.
 */
public class JourneeResponse extends GreenTracerResponse {

    private Journee journee;

    /**
     * Constructeur par defaut.
     * @return
     */
    public Journee getJournee() {
        return journee;
    }

    /**
     * 
     * @param journee
     */
    public void setJournee(Journee journee) {
        this.journee = journee;
    }

    /**
     * 
     * @param message
     * @param status
     * @param journee
     */
    public JourneeResponse(String message, Integer status, Journee journee) {
        super(message, status);
        this.journee = journee;
    }
    
}
