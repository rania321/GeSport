package service;

import Services.UserService;
import entities.Equipe;
import entities.Tournoi;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;

public class EquipeService implements IService<Equipe> {
    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    public EquipeService() {
    }

    public void add(Equipe equipe) {
        String requete = "insert into equipe (nomE, idT, idU, statutE) values (?, ?, ?, ?)";

        try {
            this.pst = this.conn.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            this.pst.setString(1, equipe.getNomE());
            this.pst.setInt(2, equipe.getTournoi().getIdT());
            this.pst.setInt(3, equipe.getUser().getIdU());
            this.pst.setString(4, equipe.getStatutE());
            this.pst.executeUpdate();

            //recupererr l'id de l eq aj
            ResultSet rs = pst.getGeneratedKeys();
            int equipeId = -1;
            if (rs.next()) {
                equipeId = rs.getInt(1);
            }

            if (equipeId != -1) {
                // Insérer une entrée dans la table inscritournoi
                String requeteIT = "INSERT INTO inscritournoi (idT, idE) VALUES (?, ?)";
                this.pst = this.conn.prepareStatement(requeteIT);
                this.pst.setInt(1, equipe.getTournoi().getIdT());
                this.pst.setInt(2, equipeId);
                this.pst.executeUpdate();
            }


        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void delete(Equipe equipe) {
        try {
            // Supprimer  dans la table inscritournoi
            String deleteInscriTournoi = "DELETE FROM inscritournoi WHERE idE=?";
            this.pst = this.conn.prepareStatement(deleteInscriTournoi);
            this.pst.setInt(1, equipe.getIdE());
            this.pst.executeUpdate();
            //Supprimer de la table joueur
            String deleteJoueur = "DELETE FROM joueur WHERE idE=?";
            this.pst = this.conn.prepareStatement(deleteJoueur);
            this.pst.setInt(1, equipe.getIdE());
            this.pst.executeUpdate();


            // Supprimer l'équipe de la table equipe
            String requete = "DELETE FROM equipe WHERE idE=?";


            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, equipe.getIdE());
            this.pst.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Equipe equipe) {

        try{
            // maj de la table inscritournoi
            String updateInscriTournoi = "UPDATE inscritournoi SET idT=? WHERE idE=?";
            this.pst = conn.prepareStatement(updateInscriTournoi);
            this.pst.setInt(1, equipe.getTournoi().getIdT());
            this.pst.setInt(2, equipe.getIdE());
            this.pst.executeUpdate();

            // maj équipe  equipe

            String requete = "UPDATE equipe SET nomE=?, idT=?, idU=?, statutE=? WHERE idE=?";

            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, equipe.getNomE());
            this.pst.setInt(2, equipe.getTournoi().getIdT());
            this.pst.setInt(3, equipe.getUser().getIdU());
            this.pst.setString(4, equipe.getStatutE());
            this.pst.setInt(5, equipe.getIdE());
            this.pst.executeUpdate();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public List<Equipe> readAll() {
        String requete = "select * from equipe";
        List<Equipe> list = new ArrayList();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);

            while(rs.next()) {
                int idT = rs.getInt("idT");
                TournoiService tournoiService = new TournoiService();
                Tournoi tournoi = tournoiService.readById(idT);
                UserService us = new UserService();
                new User();
                User user = us.readById(rs.getInt("idU"));
                Equipe equipe = new Equipe(rs.getInt("idE"), rs.getString("nomE"), tournoi, user, rs.getString("statutE"));
                list.add(equipe);
            }

            return list;
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        }
    }

    public Equipe readById(int id) {
        String requete = "select * from equipe where idE=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, id);
            ResultSet rs = this.pst.executeQuery();
            if (rs.next()) {
                int idT = rs.getInt("idT");
                TournoiService tournoiService = new TournoiService();
                Tournoi tournoi = tournoiService.readById(idT);
                int IdU = rs.getInt("idU");
                UserService us = new UserService();
                User user = us.readById(IdU);
                return new Equipe(rs.getInt(1), rs.getString(2), tournoi, user, rs.getString(5));
            } else {
                return null;
            }
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        }
    }
   /* public  int getIdByName(String nomEquipe) {
        String requete = "SELECT idE FROM equipe WHERE nomE = ?";
        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, nomEquipe);
            ResultSet rs = this.pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("idE");
            }
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
        return -1; //  retournez une valeur qui n'est pas un ID valide
    }*/

    public List<Equipe> readAllEquipesByTournoiId(int idT) {
        List<Equipe> equipes = new ArrayList<>();

        try {
            String query = "SELECT e.* FROM InscriTournoi it " +
                    "JOIN Equipe e ON it.idE = e.idE " +
                    "WHERE it.idT = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, idT);

            ResultSet rs = pst.executeQuery();

            // Parcourir les résultats de la requête et ajouter les équipes à la liste
            while (rs.next()) {
                // Créer un nouvel objet Equipe
                Equipe equipe = new Equipe();
                // Définir les propriétés de l'équipe à partir des données du ResultSet
                equipe.setIdE(rs.getInt("idE"));
                equipe.setNomE(rs.getString("nomE"));
                // Ajouter l'équipe à la liste
                equipes.add(equipe);
            }

            // Afficher les équipes récupérées (si besoin)
            for (Equipe e : equipes) {
                System.out.println("Equipe: " + e);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return equipes;
    }



}
