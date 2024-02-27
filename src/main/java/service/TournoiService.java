package service;

import entities.Tournoi;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;

public class TournoiService implements iService<Tournoi> {
    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    public TournoiService() {
    }

    public void add(Tournoi t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebutStr = sdf.format(t.getDateDebutT());
        String dateFinStr = sdf.format(t.getDateFinT());
        String requete = "insert into tournoi (idT,nomT,DateDebutT,DateFinT,DescriT,statutT) values ('" + t.getIdT() + "' , '" + t.getNomT() + "' , '" + dateDebutStr + "' , '" + dateFinStr + "' , '" + t.getDescriT() + "' , '" + t.getStatutT() + "')";

        try {
            this.ste = this.conn.createStatement();
            this.ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPst(Tournoi t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebutStr = sdf.format(t.getDateDebutT());
        String dateFinStr = sdf.format(t.getDateFinT());
        String requete = "insert into tournoi (nomT,DateDebutT,DateFinT,DescriT,statutT) values (?,?,?,?,?)";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, t.getNomT());
            this.pst.setString(2, dateDebutStr);
            this.pst.setString(3, dateFinStr);
            this.pst.setString(4, t.getDescriT());
            this.pst.setString(5, t.getStatutT());
            this.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Tournoi tournoi) {
        String requete = "DELETE FROM tournoi WHERE idT=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, tournoi.getIdT());
            this.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Tournoi tournoi) {
        String requete = "UPDATE tournoi SET nomT=?, DateDebutT=?, DateFinT=?, DescriT=?, statutT=? WHERE idT=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, tournoi.getNomT());
            this.pst.setDate(2, new Date(tournoi.getDateDebutT().getTime()));
            this.pst.setDate(3, new Date(tournoi.getDateFinT().getTime()));
            this.pst.setString(4, tournoi.getDescriT());
            this.pst.setString(5, tournoi.getStatutT());
            this.pst.setInt(6, tournoi.getIdT());
            this.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tournoi> readAll() {
        String requete = "select * from tournoi";
        List<Tournoi> list = new ArrayList();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);

            while(rs.next()) {
                list.add(new Tournoi(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getString(6)));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tournoi readById(int id) {
        String requete = "select * from tournoi where idT=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, id);
            ResultSet rs = this.pst.executeQuery();
            return rs.next() ? new Tournoi(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getString(6)) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> readAllNames() {
        String requete = "SELECT nomT FROM tournoi";
        List<String> namesList = new ArrayList<>();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);

            while(rs.next()) {
                namesList.add(rs.getString(1));
            }

            return namesList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdByName(String nom) {
        int id = -1; // Valeur par défaut si aucun tournoi avec ce nom n'est trouvé

        String query = "SELECT idT FROM tournoi WHERE nomT = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("idT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public Tournoi readByName(String nom) {
        String requete = "SELECT * FROM tournoi WHERE nomT = ?";
        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, nom);
            ResultSet rs = this.pst.executeQuery();
            if (rs.next()) {
                int idTournoi = rs.getInt("idT");
                String nomTournoi = rs.getString("nomT");
                String statutTournoi = rs.getString("statutT");

                return new Tournoi(idTournoi, nomTournoi, statutTournoi);
            } else {
                throw new RuntimeException("Aucun tournoi trouvé avec le nom : " + nom);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du tournoi avec le nom " + nom + " : " + e.getMessage(), e);
        }
    }

    public boolean Tournoitermine(String tournoiName) {
        String query = "SELECT statutT FROM tournoi WHERE nomT = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, tournoiName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String status = resultSet.getString("statutT");
                return status.equalsIgnoreCase("terminé");
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
