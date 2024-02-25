package entities;

public class Joueur {
    private int idJoueur;
    private String joueur;
    private Equipe equipe;


    public Joueur() {
    }

    public Joueur(int idJoueur, String joueur, Equipe equipe) {
        this.idJoueur = idJoueur;
        this.joueur = joueur;
        this.equipe = equipe;
    }

    public Joueur(String joueur, Equipe equipe) {
        this.joueur = joueur;
        this.equipe = equipe;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "idJoueur=" + idJoueur +
                ", joueur='" + joueur + '\'' +
                ", equipe=" + equipe +
                '}';
    }
}

