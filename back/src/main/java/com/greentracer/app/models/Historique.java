
package com.greentracer.app.models;

/**
 * Java representation of historique data row.
 */
public class Historique {

    private int idhistr;
    private String idp;
    private float historique;

    /**
     * 
     */
    public Historique() {
    }

    /**
     * 
     * @param idhistr
     * @param idp
     * @param historique
     */
    public Historique(int idhistr, String idp, float historique) {
        this.idhistr = idhistr;
        this.idp = idp;
        this.historique = historique;
    }

    /**
     * 
     * @return
     */
    public int getid() {
        return idhistr;
    }

    /**
     * 
     * @param idHist
     */
    public void setid(int idHist) {
        this.idhistr = idHist;
    }

    /**
     * 
     * @return
     */
    public String getidp() {
        return idp;
    }

    /**
     * 
     * @param idp
     */
    public void setidp(String idp) {
        this.idp = idp;
    }

    /**
     * 
     * @return
     */
    public float gethistorique() {
        return historique;
    }

    /**
     * 
     * @param historique
     */
    public void sethistorique(float historique) {
        this.historique = historique;
    }

    @Override
    public String toString() {
        return "User [id=" + idhistr + ", IDpatient=" + idp + ", historique=" + historique + "]";
    }
}
