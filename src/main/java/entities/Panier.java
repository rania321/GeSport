package entities;

public class Panier {
    int idV,idP, quantiteP;
    float totalPa;

    public Panier() {
    }

    public Panier(int idV, int idP, int quantiteP, float totalPa) {
        this.idV = idV;
        this.idP = idP;
        this.quantiteP = quantiteP;
        this.totalPa = totalPa;
    }

    public int getIdV() {
        return idV;
    }

    public void setIdV(int idV) {
        this.idV = idV;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getQuantiteP() {
        return quantiteP;
    }

    public void setQuantitéP(int quantiteP) {
        this.quantiteP = quantiteP;
    }

    public float getTotalPa() {
        return totalPa;
    }

    public void setTotalPa(float totalPa) {
        this.totalPa = totalPa;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idV=" + idV +
                ", idP=" + idP +
                ", quantitéP=" + quantiteP +
                ", totalPa=" + totalPa +
                '}';
    }
}
