package tn.Gesport.models;

import java.sql.Date;

public class Reponse {
    private int idRep;
    private int idRec;
    private Date dateRep;
    private String contenuRep;

    public Reponse(int idRep, int idRec, Date dateRep, String contenuRep) {
        this.idRep = idRep;
        this.idRec = idRec;
        this.dateRep = dateRep;
        this.contenuRep = contenuRep;
    }

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public Date getDateRep() {
        return dateRep;
    }

    public void setDateRep(Date dateRep) {
        this.dateRep = dateRep;
    }

    public String getContenuRep() {
        return contenuRep;
    }

    public void setContenuRep(String contenuRep) {
        this.contenuRep = contenuRep;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "idRep=" + idRep +
                ", idRec=" + idRec +
                ", dateRep=" + dateRep +
                ", contenuRep='" + contenuRep + '\'' +
                '}';
    }
}
