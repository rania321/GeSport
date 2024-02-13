package entities;

import java.util.Date;

public class Vente {

    int idV, idU, idP, QuantitéV;
    Date DateV;
    Float MontantV;

    public Vente() {
    }

    public Vente(int idV, int idU, int idP, int quantitéV, Date dateV, Float montantV) {
        this.idV = idV;
        this.idU = idU;
        this.idP = idP;
        QuantitéV = quantitéV;
        DateV = dateV;
        MontantV = montantV;
    }

    public Vente(int idU, int idP, int quantitéV, Date dateV, Float montantV) {
        this.idU = idU;
        this.idP = idP;
        QuantitéV = quantitéV;
        DateV = dateV;
        MontantV = montantV;
    }

    public int getIdV() {
        return idV;
    }

    public int getIdU() {
        return idU;
    }

    public int getIdP() {
        return idP;
    }

    public int getQuantitéV() {
        return QuantitéV;
    }

    public java.sql.Date getDateV() {
        return (java.sql.Date) DateV;
    }

    public Float getMontantV() {
        return MontantV;
    }

    public void setIdV(int idV) {
        this.idV = idV;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public void setQuantitéV(int quantitéV) {
        QuantitéV = quantitéV;
    }

    public void setDateV(Date dateV) {
        DateV = dateV;
    }

    public void setMontantV(Float montantV) {
        MontantV = montantV;
    }

    @Override
    public String toString() {
        return "Vente{" +
                "idV=" + idV +
                ", idU=" + idU +
                ", idP=" + idP +
                ", QuantitéV=" + QuantitéV +
                ", DateV=" + DateV +
                ", MontantV=" + MontantV +
                '}';
    }
}
