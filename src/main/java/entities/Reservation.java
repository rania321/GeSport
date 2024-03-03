package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {

    private int idR;
    private User user;
    private Activite activite;

    private Date DateDebutR;
    private String HeureR, statutR;

    public Reservation() {
    }

    public Reservation(User user, Activite activite, Date dateDebutR, String heureR, String statutR)  {
        this.user = user;
        this.activite = activite;
        DateDebutR = dateDebutR;
        HeureR = heureR;
        this.statutR = statutR;
    }

    public Reservation(Activite activite, Date dateDebutR, String heureR, String statutR) {
        this.activite = activite;
        DateDebutR = dateDebutR;
        HeureR = heureR;
        this.statutR = statutR;
    }

    public Reservation(int idR, User user, Activite activite, Date dateDebutR, String heureR, String statutR) {
        this.idR = idR;
        this.user = user;
        this.activite = activite;
        DateDebutR = dateDebutR;
        HeureR = heureR;
        this.statutR = statutR;
    }

    public Reservation(int idR, Activite activite, Date dateDebutR, String heureR, String statutR) {
        this.idR = idR;
        this.activite = activite;
        DateDebutR = dateDebutR;
        HeureR = heureR;
        this.statutR = statutR;
    }

    public Reservation(User u, Activite activite, LocalDate date, String heure, String enCours) {
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateDebutR() {
        return DateDebutR;
    }

    public void setDateDebutR(Date dateDebutR) {
        DateDebutR = dateDebutR;
    }

    public String getHeureR() {
        return HeureR;
    }

    public void setHeureR(String heureR) {
        HeureR = heureR;
    }

    public String getStatutR() {
        return statutR;
    }

    public void setStatutR(String statutR) {
        this.statutR = statutR;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idR=" + idR +
                ", PrénomU=" + user.getPrenomU() +
                ", activite=" + activite.getNomA() +
                ", DateDebutR=" + DateDebutR +
                ", HeureR=" + HeureR +
                ", statutR='" + statutR + '\'' +
                '}';
    }

    public String getActiviteNom() {
        return activite != null ? activite.getNomA() : null;
    }

    // Ajoutez un accesseur pour la propriété activiteNom
    public StringProperty activiteNomProperty() {
        return new SimpleStringProperty(getActiviteNom());
    }

    public String getUserNom() {
        return user != null ? user.getNomU() : null;
    }

    // Ajoutez un accesseur pour la propriété activiteNom
    public StringProperty userNomProperty() {
        return new SimpleStringProperty(getUserNom());
    }
}
