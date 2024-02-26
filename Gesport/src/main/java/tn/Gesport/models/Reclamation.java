package tn.Gesport.models;

import java.sql.Date;
import java.sql.Timestamp;

import tn.Gesport.models.User;

public class Reclamation {
    private int idRec;
    private String name;
    private String lastName;
    private String descriRec;
    private Timestamp DateRec;
    private String CategorieRec;
    private String StatutRec;
    private int userId; // Reference to the user who created the reclamation

    public Reclamation() {}

    public Reclamation(int idRec, String name, String lastName, String descriRec, Timestamp timestamp, String categorieRec, String statutRec, int userId) {
        this.idRec = idRec;
        this.name = name;
        this.lastName = lastName;
        this.descriRec = descriRec;
        this.DateRec = timestamp;
        this.CategorieRec = categorieRec;
        this.StatutRec = statutRec;
        this.userId = userId; // Set the user who created the reclamation
    }

    // Getters and setters for user
    public int getUserId() {
        return userId;
    }

    public void setUserId(int user) {
        this.userId = userId;
    }

    // Constructor with Timestamp parameter
    public Reclamation(int idRec, String name, String lastName, String descriRec, Timestamp timestamp, String categorieRec, String statutRec) {
        this.idRec = idRec;
        this.name = name;
        this.lastName = lastName;
        this.descriRec = descriRec;
        this.DateRec = timestamp;
        this.CategorieRec = categorieRec;
        this.StatutRec = statutRec;
    }





    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescriRec() {
        return descriRec;
    }

    public void setDescriRec(String descriRec) {
        this.descriRec = descriRec;
    }

    public Timestamp getDateRec() {
        return DateRec;
    }

    public void setDateRec(Timestamp dateRec) {
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
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", descriRec='" + descriRec + '\'' +
                ", DateRec=" + DateRec +
                ", CategorieRec='" + CategorieRec + '\'' +
                ", StatutRec='" + StatutRec + '\'' +
                '}';
    }
}
