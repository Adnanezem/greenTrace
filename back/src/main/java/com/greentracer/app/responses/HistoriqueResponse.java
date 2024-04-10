package com.greentracer.app.responses;

import com.greentracer.app.models.Historique;

/**
 * Réponse pour les objets Historique.
 */
public class HistoriqueResponse extends GreenTracerResponse {

    private Historique historique;

    /**
     * Constructeur par défaut.
     * @param message
     * @param status
     * @param h
     */
    public HistoriqueResponse(String message, Integer status, Historique h) {
        super(message, status);
        this.historique = h;
    }

    /**
     * 
     * @return
     */
    public Historique getHistorique() {
        return historique;
    }

    /**
     * 
     * @param h
     */
    public void setHistorique(Historique h) {
        this.historique = h;
    }

}
