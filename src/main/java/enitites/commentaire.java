package enitites;

import java.util.Date;

public class commentaire {
    private int idC;
    private int idrec;
    private Date DateC;
    private String ContenuC;

    public commentaire() {
    }

    public commentaire(int idC, int idrec, Date dateC, String contenuC) {
        this.idC = idC;
        this.idrec = idrec;
        DateC = dateC;
        ContenuC = contenuC;
    }

    public commentaire(int idrec, Date dateC, String contenuC) {
        this.idrec = idrec;
        DateC = dateC;
        ContenuC = contenuC;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public int getIdrec() {
        return idrec;
    }

    public void setIdrec(int idrec) {
        this.idrec = idrec;
    }

    public Date getDateC() {
        return DateC;
    }

    public void setDateC(Date dateC) {
        DateC = dateC;
    }

    public String getContenuC() {
        return ContenuC;
    }

    public void setContenuC(String contenuC) {
        ContenuC = contenuC;
    }

    @Override
    public String toString() {
        return "commenatire{" +
                "idC=" + idC +
                ", idrec=" + idrec +
                ", DateC=" + DateC +
                ", ContenuC='" + ContenuC + '\'' +
                '}';
    }
}
