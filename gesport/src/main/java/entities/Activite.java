package entities;

public class Activite {
    private int idA;
    private String NomA,TypeA,DispoA,DescriA;

    public Activite() {
    }

    public Activite(int idA, String nomA, String typeA, String dispoA, String descriA) {
        this.idA = idA;
        NomA = nomA;
        TypeA = typeA;
        DispoA = dispoA;
        DescriA = descriA;
    }

    public Activite(String nomA, String typeA, String dispoA, String descriA) {
        NomA = nomA;
        TypeA = typeA;
        DispoA = dispoA;
        DescriA = descriA;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public String getNomA() {
        return NomA;
    }

    public void setNomA(String nomA) {
        NomA = nomA;
    }

    public String getTypeA() {
        return TypeA;
    }

    public void setTypeA(String typeA) {
        TypeA = typeA;
    }

    public String getDispoA() {
        return DispoA;
    }

    public void setDispoA(String dispoA) {
        DispoA = dispoA;
    }

    public String getDescriA() {
        return DescriA;
    }

    public void setDescriA(String descriA) {
        DescriA = descriA;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "idA=" + idA +
                ", NomA='" + NomA + '\'' +
                ", TypeA='" + TypeA + '\'' +
                ", DispoA='" + DispoA + '\'' +
                ", DescriA='" + DescriA + '\'' +
                '}';
    }
}
