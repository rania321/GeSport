package entities;

public class Equipe {
    private int idE;
    private String nomE;
    private Tournoi tournoi;
    private User user;
    private String statutE;

    public Equipe() {
    }

    public Equipe(int idE, String nomE, Tournoi tournoi, User user, String statutE) {
        this.idE = idE;
        this.nomE = nomE;
        this.tournoi = tournoi;
        this.user = user;
        this.statutE = statutE;
    }

    public Equipe(String nomE, Tournoi tournoi, User user, String statutE) {
        this.nomE = nomE;
        this.tournoi = tournoi;
        this.user = user;
        this.statutE = statutE;
    }

    public int getIdE() {
        return this.idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    public String getNomE() {
        return this.nomE;
    }

    public void setNomE(String nomE) {
        this.nomE = nomE;
    }

    public Tournoi getTournoi() {
        return this.tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatutE() {
        return this.statutE;
    }

    public void setStatutE(String statutE) {
        this.statutE = statutE;
    }

    public String toString() {
        int var10000 = this.idE;
        return "Equipe{idE=" + var10000 + ", nomE='" + this.nomE + "', tournoi=" + String.valueOf(this.tournoi) + ", user=" + String.valueOf(this.user) + ", statutE='" + this.statutE + "'}";
    }
}
