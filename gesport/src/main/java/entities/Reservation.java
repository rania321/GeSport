package entities;

import java.text.ParseException;
import java.util.Date;

public class Reservation {

    private int idR, idU, idA;
    private Date DateDebutR, DateFinR;
    private String statutR;

    public Reservation() {
    }

    public Reservation(int idU, int idA, Date dateDebutR, Date dateFinR, String statutR)  {
        this.idU = idU;
        this.idA = idA;
       /* SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date parsedDateDebut = dateFormat.parse(String.valueOf(dateDebutR));
        Date parsedDateFin = dateFormat.parse(String.valueOf(dateFinR));
        this.DateDebutR = new Timestamp(parsedDateDebut.getTime());
        this.DateFinR = new Timestamp(parsedDateFin.getTime());*/
        DateDebutR = dateDebutR;
        DateFinR = dateFinR;
        this.statutR = statutR;
    }

    public Reservation(int idR, int idU, int idA, Date dateDebutR, Date dateFinR, String statutR) {
        this.idR = idR;
        this.idU = idU;
        this.idA = idA;
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

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
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
                ", idU=" + idU +
                ", idA=" + idA +
                ", DateDebutR=" + DateDebutR +
                ", DateFinR=" + DateFinR +
                ", statutR='" + statutR + '\'' +
                '}';
    }
}
