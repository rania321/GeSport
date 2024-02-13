package entities;

public class dossiermedical {
    private int idDM;
    private  int idU;
    private String poidsDM;
    private  String tailleDM;
    private  int ageDM;

    public dossiermedical() {
    }

    public dossiermedical(int idDM, int idU, String poidsDM, String tailleDM, int ageDM) {
        this.idDM = idDM;
        this.idU = idU;
        this.poidsDM = poidsDM;
        this.tailleDM = tailleDM;
        this.ageDM = ageDM;
    }

    public dossiermedical(int idU, String poidsDM, String tailleDM, int ageDM) {
        this.idU = idU;
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

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
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
        return "dossiermedical{" +
                "idDM=" + idDM +
                ", idU=" + idU +
                ", poidsDM=" + poidsDM +
                ", tailleDM=" + tailleDM +
                ", ageDM=" + ageDM +
                '}';
    }
}
