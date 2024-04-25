
package com.greentracer.app.models;

/**
 * Java representation of historique data row.
 */
public class Historique {

    private int idHistr;
    private String idP;
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
        this.idHistr = idhistr;
        this.idP = idp;
        this.historique = historique;
    }

    /**
     * 
     * @return
     */
    public int getid() {
        return idHistr;
    }

    /**
     * 
     * @param idHist
     */
    public void setid(int idHist) {
        this.idHistr = idHist;
    }

    /**
     * 
     * @return
     */
    public String getidp() {
        return idP;
    }

    /**
     * 
     * @param idp
     */
    public void setidp(String idp) {
        this.idP = idp;
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
        return "User [id=" + idHistr + ", IDpatient=" + idP + ", historique=" + historique + "]";
    }
}
