package tn.Gesport.models;

import java.sql.Timestamp;

public class Response {
    private int idRep;
    private int idRec;
    private Timestamp dateRep;
    private String contenuRep;
    private User user; // Reference to the user who created the response

    public Response(int idRep, int idRec, Timestamp dateRep, String contenuRep, User user) {
        this.idRep = idRep;
        this.idRec = idRec;
        this.dateRep = dateRep;
        this.contenuRep = contenuRep;
        this.user = user; // Set the user who created the response
    }
    public Response(int idRep, int idRec, Timestamp dateRep, String contenuRep) {
        this.idRep = idRep;
        this.idRec = idRec;
        this.dateRep = dateRep;
        this.contenuRep = contenuRep;

    }

    public Response() {

    }

    // Getters and setters for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Timestamp getDateRep() {
        return dateRep;
    }

    public void setDateRep(Timestamp dateRep) {
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
