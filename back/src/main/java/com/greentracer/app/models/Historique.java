
package com.greentracer.app.models;

/**
 * Java representation of historique data row.
 */

public class Historique {
    
    private int id_histr;
    private String id_p;
    private float historique;

    public Historique() {
    }

    public  Historique (int id_histr, String id_p, float historique) {
        this.id_histr = id_histr;
        this.id_p = id_p;
        this.historique = historique;
    }
    public  int getid() {
        return id_histr;
    }

    public void setid(int id_hist) {
        this.id_histr = id_histr;
    }

    public String getid_p() {
        return id_p;
    }

    public void setid_p(String id_p) {
        this.id_p = id_p;
    }

    public float gethistorique() {
        return historique;
    }

    public void sethistorique(float historique) {
        this.historique = historique;
    }


    @Override
    public String toString() {
        return "User [id=" + id_histr + ", ID_patient=" + id_p + ", historique=" + historique + "]";
    }




}
