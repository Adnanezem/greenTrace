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


    /**
     * 
     */
    public Journee() {
    }

    /**
     * 
     * @param id
     * @param patientId
     * @param date
     * @param resultat
     */
    public Journee(int id, String patientId, Date date, float resultat) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.resultat = resultat;
    }

    /**
     * 
     * @return
     */
    public int getid() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setid(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     */
    public String getpatientId() {
        return patientId;
    }

    /**
     * 
     * @param patientId
     */
    public void setpatientId(String patientId) {
        this.patientId = patientId;
        
    }

    /**
     * 
     * @return
     */
    public Date getdate() {
        return date;
    }

    /**
     * 
     * @param date
     */
    public void setdate(Date date) {
        this.date = date;
    }

    /**
     * 
     * @return
     */
    public float getresultat() {
        return resultat;
    }

    /**
     * 
     * @param resultat
     */
    public void setresultat(float resultat) {
        this.resultat = resultat;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", ID_patient=" + patientId + ", Date=" + date + ", Resultat=" + resultat + "]";
    }


}





