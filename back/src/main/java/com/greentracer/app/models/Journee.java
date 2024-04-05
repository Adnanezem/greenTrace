package com.greentracer.app.models;

import java.sql.Date;

/**
 * Java representation of journée data row.
 */

public class Journee {
    
    private int id;
    private String patientId;
    private Date date;
    private float resultat;


    public Journee() {
    }

    public Journee(int id, String patientId, Date date, float resultat) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.resultat = resultat;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getpatientId() {
        return patientId;
    }

    public void setpatientId(String patientId) {
        this.patientId = patientId;
        
    }

    public Date getdate() {
        return date;
    }

    public void setdate(Date date) {
        this.date = date;
    }

    public float getresultat() {
        return resultat;
    }

    public void setresultat(float resultat) {
        this.resultat = resultat;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", ID_patient=" + patientId + ", Date=" + date + ", Resultat=" + resultat + "]";
    }


}





