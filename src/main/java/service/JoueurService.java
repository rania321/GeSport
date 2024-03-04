package service;

import entities.Equipe;
import entities.Joueur;

import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurService implements IService<Joueur> {
    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    @Override
    public void add(Joueur joueur) {
        String requete = "INSERT INTO joueur (joueur, idE) VALUES (?, ?)";
        try  {
            // Désactiver autocommit
            this.conn.setAutoCommit(false);

            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, joueur.getJoueur());
            this.pst.setInt(2, joueur.getEquipe().getIdE());

            this.pst.executeUpdate();

            // Valider la transaction
            this.conn.commit();

            // Réactiver autocommit
            this.conn.setAutoCommit(true);

            this.pst.close();
        } catch (SQLException ex) {
            // En cas d'erreur, annuler la transaction
            try {
                this.conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur  : " + e.getMessage(), e);
            }
            throw new RuntimeException("Erreur lors de l'ajout du joueur : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(Joueur joueur) {
        String requete = "DELETE FROM joueur WHERE idJ = ?";
        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, joueur.getIdJoueur());

            this.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Joueur joueur) {

    }

    @Override
    public List<Joueur> readAll() {
        String requete = "select * from joueur";
        List<Joueur> list = new ArrayList();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);

            while (rs.next()) {
                int idJoueur = rs.getInt("idJ");
                String nomJoueur = rs.getString("joueur");
                int idE = rs.getInt("idE");
                EquipeService equipeService = new EquipeService();
                Equipe equipe = equipeService.readById(idE);
                Joueur joueur = new Joueur(rs.getInt("idJ"),rs.getString("joueur"),equipe);
                list.add(joueur);

            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Joueur readById(int id) {
        String requete = "SELECT * FROM joueur WHERE idJ = ?";
        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, id);
            ResultSet rs = this.pst.executeQuery();
            if (rs.next()) {
                int idJoueur = rs.getInt("idJ");
                String nomJoueur = rs.getString("joueur");
                int idE = rs.getInt("idE");
                EquipeService equipeService = new EquipeService();
                Equipe equipe = equipeService.readById(idE);
                return new Joueur(idJoueur, nomJoueur, equipe);
            } else {
                throw new RuntimeException("Aucun joueur trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du joueur avec l'ID " + id + " : " + e.getMessage(), e);
        }
    }



    public void supprimerJoueursVides() {
        String requete = "DELETE FROM joueur WHERE joueur = ''";

        try {
            this.pst = this.conn.prepareStatement(requete);

            this.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteJoueursByEquipeId(int equipeId) {

        String requete = "DELETE FROM joueur WHERE idE = ?";
        try {
            this.pst=this.conn.prepareStatement(requete);
            this.pst.setInt(1, equipeId);
            this.pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Joueur> getJoueurbyEquipe(int equipeId) {
        List<Joueur> joueurbyequipe = new ArrayList<>();
        List<Joueur> alljoueurs = readAll();
        for ( Joueur J : alljoueurs) {
            if (J.getEquipe().getIdE()==equipeId) {
                joueurbyequipe.add(J);
            }
        }
        return joueurbyequipe;
    }

  /*  public void deleteById(int joueurId) {
        String requete = "DELETE FROM joueur WHERE idJ = ?";
        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, joueurId);
            this.pst.executeUpdate();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

   */
}







