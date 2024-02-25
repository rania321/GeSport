package service;

import entities.Equipe;
import entities.Joueur;

import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurService implements iService<Joueur> {
    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    @Override
    public void add(Joueur joueur) {
        String requete = "INSERT INTO joueur (joueur, idE) VALUES (?, ?)";
        try  {
            // Désactiver autocommit
            this.conn.setAutoCommit(false);

            // Préparer la requête
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, joueur.getJoueur());
            this.pst.setInt(2, joueur.getEquipe().getIdE());

            // Exécuter la requête
            this.pst.executeUpdate();

            // Valider la transaction
            this.conn.commit();

            // Réactiver autocommit
            this.conn.setAutoCommit(true);

            // Fermer le PreparedStatement
            this.pst.close();
        } catch (SQLException var4) {
            // En cas d'erreur, annuler la transaction
            try {
                this.conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors du rollback de la transaction : " + e.getMessage(), e);
            }
            throw new RuntimeException("Erreur lors de l'ajout du joueur : " + var4.getMessage(), var4);
        }
    }

    @Override
    public void delete(Joueur joueur) {

    }

    @Override
    public void update(Joueur joueur) {

    }

    @Override
    public List<Joueur> readAll() {
        return null;
    }

    @Override
    public Joueur readById(int i) {
        return null;
    }



    public void supprimerJoueursVides() {
        String requete = "DELETE FROM joueur WHERE joueur = ''";

        try {
            this.pst = this.conn.prepareStatement(requete);

            this.pst.executeUpdate();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void deleteJoueursByEquipeId(int equipeId) {

        String requete = "DELETE FROM joueur WHERE idE = ?";
        try {
            this.pst=this.conn.prepareStatement(requete);
            this.pst.setInt(1, equipeId);
            this.pst.executeUpdate();

        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }
}





