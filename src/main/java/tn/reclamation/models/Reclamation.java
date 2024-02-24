package tn.Gesport.models;

import java.sql.Date;

public class Reclamation {
    private int idRec;
    private int idU;
    private String descriRec;
    private Date DateRec;
    private String CategorieRec;
    private String StatutRec;

    public Reclamation() {

    }

    public Reclamation(int idRec, int idU, String descriRec, Date dateRec, String categorieRec, String statutRec) {
        this.idRec = idRec;
        this.idU = idU;
        this.descriRec = descriRec;
        DateRec = dateRec;
        CategorieRec = categorieRec;
        StatutRec = statutRec;
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getDescriRec() {
        return descriRec;
    }

    public void setDescriRec(String descriRec) {
        this.descriRec = descriRec;
    }

    public Date getDateRec() {
        return DateRec;
    }

    public void setDateRec(Date dateRec) {
        DateRec = dateRec;
    }

    public String getCategorieRec() {
        return CategorieRec;
    }

    public void setCategorieRec(String categorieRec) {
        CategorieRec = categorieRec;
    }

    public String getStatutRec() {
        return StatutRec;
    }

    public void setStatutRec(String statutRec) {
        StatutRec = statutRec;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idRec=" + idRec +
                ", idU=" + idU +
                ", descriRec='" + descriRec + '\'' +
                ", DateRec=" + DateRec +
                ", CategorieRec='" + CategorieRec + '\'' +
                ", StatutRec='" + StatutRec + '\'' +
                '}';
    }
}
