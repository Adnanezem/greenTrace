package com.greentracer.app.responses;

import java.util.List;

import com.greentracer.app.models.Journee;

/**
 * Réponse pour une liste de journées (plusieurs fois la meme date).
 */
public class JourneesResponse extends GreenTracerResponse {

    private List<Journee> journees;

    public List<Journee> getJournees() {
        return journees;
    }

    public void setJournees(List<Journee> journees) {
        this.journees = journees;
    }

    public JourneesResponse(String message, Integer status, List<Journee> journees) {
        super(message, status);
        this.journees = journees;
    }

}
