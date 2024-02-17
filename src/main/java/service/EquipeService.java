package service;

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

public class EquipeService implements iService<Equipe> {
    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    public EquipeService() {
    }

    public void add(Equipe equipe) {
        String requete = "insert into equipe (nomE, idT, idU, statutE) values (?, ?, ?, ?)";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, equipe.getNomE());
            this.pst.setInt(2, equipe.getTournoi().getIdT());
            this.pst.setInt(3, equipe.getUser().getIdU());
            this.pst.setString(4, equipe.getStatutE());
            this.pst.executeUpdate();
            this.pst = this.conn.prepareStatement(requete);
            this.pst.close();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void delete(Equipe equipe) {
        String requete = "DELETE FROM equipe WHERE idE=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, equipe.getIdE());
            this.pst.executeUpdate();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void update(Equipe equipe) {
        String requete = "UPDATE equipe SET nomE=?, idT=?, idU=?, statutE=? WHERE idE=?";

        try {
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
}
