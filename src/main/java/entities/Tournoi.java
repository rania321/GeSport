package entities;

import java.util.Date;

public class Tournoi {
    private int idT;
    private String nomT;
    private Date DateDebutT;
    private Date DateFinT;
    private String DescriT;
    private String statutT;

    public Tournoi() {
    }

    public Tournoi(int idT, String nomT, String statutT) {
        this.idT = idT;
        this.nomT = nomT;
        this.statutT = statutT;
    }

    public Tournoi(int idT, String nomT, Date dateDebutT, Date dateFinT, String descriT, String statutT) {
        this.idT = idT;
        this.nomT = nomT;
        this.DateDebutT = dateDebutT;
        this.DateFinT = dateFinT;
        this.DescriT = descriT;
        this.statutT = statutT;
    }

    public Tournoi(String nomT, Date dateDebutT, Date dateFinT, String descriT, String statutT) {
        this.nomT = nomT;
        this.DateDebutT = dateDebutT;
        this.DateFinT = dateFinT;
        this.DescriT = descriT;
        this.statutT = statutT;
    }

    public int getIdT() {
        return this.idT;
    }

    public void setIdT(int idT) {
        this.idT = idT;
    }

    public String getNomT() {
        return this.nomT;
    }

    public void setNomT(String nomT) {
        this.nomT = nomT;
    }

    public Date getDateDebutT() {
        return this.DateDebutT;
    }

    public void setDateDebutT(Date dateDebutT) {
        this.DateDebutT = dateDebutT;
    }

    public Date getDateFinT() {
        return this.DateFinT;
    }

    public void setDateFinT(Date dateFinT) {
        this.DateFinT = dateFinT;
    }

    public String getDescriT() {
        return this.DescriT;
    }

    public void setDescriT(String descriT) {
        this.DescriT = descriT;
    }

    public String getStatutT() {
        return this.statutT;
    }

    public void setStatutT(String statutT) {
        this.statutT = statutT;
    }

    public String toString() {
        int var10000 = this.idT;
        return "Tournoi{idT=" + var10000 + ", nomT='" + this.nomT + "', DateDebutT=" + String.valueOf(this.DateDebutT) + ", DateFinT=" + String.valueOf(this.DateFinT) + ", DescriT='" + this.DescriT + "', statutT='" + this.statutT + "'}";
    }

}
