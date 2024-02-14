package entities;

import java.text.ParseException;
import java.util.Date;

public class Reservation {

    private int idR;
    private User user;
    private Activite activite;

    private Date DateDebutR, DateFinR;
    private String statutR;

    public Reservation() {
    }

    public Reservation(User user, Activite activite, Date dateDebutR, Date dateFinR, String statutR)  {
        this.user = user;
        this.activite = activite;
       /* SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date parsedDateDebut = dateFormat.parse(String.valueOf(dateDebutR));
        Date parsedDateFin = dateFormat.parse(String.valueOf(dateFinR));
        this.DateDebutR = new Timestamp(parsedDateDebut.getTime());
        this.DateFinR = new Timestamp(parsedDateFin.getTime());*/
        DateDebutR = dateDebutR;
        DateFinR = dateFinR;
        this.statutR = statutR;
    }

    public Reservation(Activite activite, Date dateDebutR, Date dateFinR, String statutR) {
        this.activite = activite;
        DateDebutR = dateDebutR;
        DateFinR = dateFinR;
        this.statutR = statutR;
    }

    public Reservation(int idR, User user, Activite activite, Date dateDebutR, Date dateFinR, String statutR) {
        this.idR = idR;
        this.user = user;
        this.activite = activite;
        DateDebutR = dateDebutR;
        DateFinR = dateFinR;
        this.statutR = statutR;
    }

    public Reservation(int idR, Activite activite, Date dateDebutR, Date dateFinR, String statutR) {
        this.idR = idR;
        this.activite = activite;
        DateDebutR = dateDebutR;
        DateFinR = dateFinR;
        this.statutR = statutR;
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

    public Date getDateFinR() {
        return DateFinR;
    }

    public void setDateFinR(Date dateFinR) {
        DateFinR = dateFinR;
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
                ", DateFinR=" + DateFinR +
                ", statutR='" + statutR + '\'' +
                '}';
    }
}
