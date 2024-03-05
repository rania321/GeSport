package service;

import entities.Activite;
import org.example.Service.IService;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteService implements IService<Activite> {

    private Connection conn;

    public ActiviteService() {
        conn = DataSource.getInstance().getCnx();
    }


    ////////////////CRUD AJOUT ACTIVITE////////////////////////
    @Override
    public void add(Activite a) {
        String requete = "insert into Activite (NomA,TypeA,DispoA,DescriA,imageA) values (?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, a.getNomA());
            pst.setString(2, a.getTypeA());
            pst.setString(3, a.getDispoA());
            pst.setString(4, a.getDescriA());
            pst.setString(5, a.getImageA());
            pst.executeUpdate();
            System.out.println("Activite ajouté!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    ////////////////CRUD Suppression ACTIVITE////////////////////////
    @Override
    public void delete(Activite a) {
        String requete = "DELETE FROM activite WHERE idA=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, a.getIdA());
            pst.executeUpdate();
            System.out.println("Activité supprimée!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteById(int idA) {
        try {
            String requete = "DELETE  FROM activite WHERE idA=?";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, idA);

            pst.executeUpdate();
            System.out.println("Activité supprimée!");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Activité non supprimée!");
        }
    }

    ////////////////////CRUD MODIFIER ACTIVITE/////////////////
    @Override
    public void update(Activite a) {
        String req = "UPDATE `activite` SET NomA=?, TypeA=?, DispoA = ?,DescriA=?, imageA=? WHERE idA = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, a.getNomA());
            ps.setString(2, a.getTypeA());
            ps.setString(3, a.getDispoA());
            ps.setString(4, a.getDescriA());
            ps.setString(5, a.getImageA());
            ps.setInt(6, a.getIdA());
            ps.executeUpdate();
            System.out.println("Acivité modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //////////////////////////CRUD AFFICHER ACTIVITE/////////////////////
    @Override
    public List<Activite> readAll() {
        String requete = "select * from activite";
        List<Activite> list = new ArrayList<>();
        try {
            Statement ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Activite a = new Activite(rs.getInt("idA"), rs.getString("NomA"), rs.getString("TypeA"), rs.getString("DispoA"), rs.getString("DescriA"), rs.getString("imageA"));
                list.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public Activite readById(int id) {
        Activite a = new Activite();
        String req = "SELECT * FROM activite WHERE idA = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                a.setIdA(rst.getInt("idA"));
                a.setNomA(rst.getString("NomA"));
                a.setTypeA(rst.getString("TypeA"));
                a.setDispoA(rst.getString("DispoA"));
                a.setDescriA(rst.getString("DescriA"));
                a.setDescriA(rst.getString("imageA"));

               /*String nom = rst.getString("nom");
                Time horaire = rst.getTime("horaire");
                String jour = rst.getString("jourseance");
                int numSalle = rst.getInt("numesalle");
                String duree= rst.getString("duree");
                a = new Activite(id, nom,horaire,jour,numSalle,duree);*/
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    // Méthode pour obtenir le nombre total d'activités
    public int getNombreTotalActivites() {
        String requete = "SELECT COUNT(*) FROM activite";
        try {
            Statement ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0; // Retourne 0 en cas d'erreur
    }



    public void addActiviteToFavoriteList(int activiteId, int userId) {
        try {
            String req = "INSERT INTO `activitefavoris`(`idA`, `idU` ) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, activiteId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            System.out.println("Activity added to fav list successfully");
            ps.close();
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de l'ajout' de l'activite au fav list : " + e.getMessage());
        }

    }

    public List<Activite> getActiviteFavList(int userId) {
        List<Activite> activiteList = new ArrayList<>();
        try {
            String query = "SELECT * FROM activite a JOIN activitefavoris f ON a.idA = f.idA WHERE f.idU=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcours du résultat de la requête
            while (resultSet.next()) {
                Activite activite = new Activite();
                activite.setIdA(resultSet.getInt("idA"));
                activite.setNomA(resultSet.getString("NomA"));
                activite.setTypeA(resultSet.getString("TypeA"));
                activite.setDispoA(resultSet.getString("DispoA"));
                activite.setDescriA(resultSet.getString("DescriA"));
                activite.setImageA(resultSet.getString("ImageA"));

                activiteList.add(activite);
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activiteList;

    }
    public int activiteInFavList(int activiteID, int userId) throws SQLException {
        String req = "SELECT * FROM `activitefavoris` where idA = ? and idU=? ";
        PreparedStatement ps = conn.prepareStatement(req);
        ps.setInt(1, activiteID);
        ps.setInt(2, userId);

        ResultSet rs = ps.executeQuery();
        int found = 0;
        if (rs.next()) {
            found = 1;
        }
        ps.close();
        return found;
    }

    public void removeActiviteFromFavoriteList(int idActivite, int userID) throws SQLException {
        String sql = "DELETE FROM activitefavoris WHERE idA = ? and idU=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idActivite);
            pstmt.setInt(2, userID);
            //pstmt.executeUpdate();
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Activity removed from fav list successfully");
            } else {
                System.out.println("Activity not found in fav list for removal");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMostFavoritedActivityName() {
        try {
            String query = "SELECT a.NomA, COUNT(f.idA) AS favoritedCount " +
                    "FROM activite a " +
                    "JOIN activitefavoris f ON a.idA = f.idA " +
                    "GROUP BY a.idA " +
                    "ORDER BY favoritedCount DESC " +
                    "LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("NomA");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getLikeCountForActivite(int activiteId) {
        String requete = "SELECT COUNT(*) FROM activitefavoris WHERE idA = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(requete);
            ps.setInt(1, activiteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0; // Retourne 0 en cas d'erreur
    }
}
