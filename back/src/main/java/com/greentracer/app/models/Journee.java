package com.greentracer.app.models;

import java.sql.Date;

/**
 * Java representation of journée data row.
 */

public class Journee {
    
    private String id;
    private String patientId;
    private Date date;
    private String resultat;



    public Journee() {
    }

    public Journee(String id, String patientId, Date date, String resultat) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.resultat = resultat;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
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

    public String getresultat() {
        return resultat;
    }

    public void setresultat(String resultat) {
        this.resultat = resultat;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", ID_patient=" + patientId + ", Date=" + date + ", Resultat=" + resultat + "]";
    }


}





