package entities;

public class dossiermedical {
    private User use;
    private int idDM;

    private String poidsDM;
    private  String tailleDM;
    private  int ageDM;

    public dossiermedical() {
    }

    public dossiermedical(int idDM, User use, String poidsDM, String tailleDM, int ageDM) {
        this.idDM = idDM;
        this.use = use;
        this.poidsDM = poidsDM;
        this.tailleDM = tailleDM;
        this.ageDM = ageDM;
    }

    public dossiermedical(User use, String poidsDM, String tailleDM, int ageDM) {
        this.use = use;
        this.poidsDM = poidsDM;
        this.tailleDM = tailleDM;
        this.ageDM = ageDM;
    }

    public int getIdDM() {
        return idDM;
    }

    public void setIdDM(int idDM) {
        this.idDM = idDM;
    }

    public User getUse() {
        return use;
    }

    public void setuse(User use) {
        this.use= use;
    }

    public String getPoidsDM() {
        return poidsDM;
    }

    public void setPoidsDM(String poidsDM) {
        this.poidsDM = poidsDM;
    }

    public String getTailleDM() {
        return tailleDM;
    }

    public void setTailleDM(String tailleDM) {
        this.tailleDM = tailleDM;
    }

    public int getAgeDM() {
        return ageDM;
    }

    public void setAgeDM(int ageDM) {
        this.ageDM = ageDM;
    }

    @Override
    public String toString() {
        return "DossierMedical{" +
                "idDM=" + idDM +
                ", use=" + use +
                ", poidsDM='" + poidsDM + '\'' +
                ", tailleDM='" + tailleDM + '\'' +
                ", ageDM=" + ageDM +
                '}';
    }
}
