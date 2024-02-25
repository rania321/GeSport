package entities;

public class InscriTournoi {
    public int idI;
    public Tournoi T;
    public Equipe E;

    public InscriTournoi() {
    }

    public InscriTournoi(int idI, Tournoi t, Equipe e) {
        this.idI = idI;
        T = t;
        E = e;
    }

    public InscriTournoi(Tournoi t, Equipe e) {
        T = t;
        E = e;
    }

    public int getIdI() {
        return idI;
    }

    public void setIdI(int idI) {
        this.idI = idI;
    }

    public Tournoi getT() {
        return T;
    }

    public void setT(Tournoi t) {
        T = t;
    }

    public Equipe getE() {
        return E;
    }

    public void setE(Equipe e) {
        E = e;
    }

    @Override
    public String toString() {
        return "InscriTournoi{" +
                "idI=" + idI +
                ", T=" + T +
                ", E=" + E +
                '}';
    }


}

