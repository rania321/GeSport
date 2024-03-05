package Services;

import service.IService;
import entities.Reclamation;
import entities.User;
import util.DataSource;
import API.FilterBadWord;

import java.sql.*;
import java.util.ArrayList;

public class ServiceReclamation implements IService<Reclamation> {

    private Connection connection;

    public ServiceReclamation() {
        connection = DataSource.getInstance().getCnx();;
    }

    @Override
    public void add(Reclamation reclamation) {
        String query = "INSERT INTO reclamation (name, lastName, descriRec, DateRec, CategorieRec, StatutRec) VALUES (?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, FilterBadWord.filter(reclamation.getName()));
            preparedStatement.setString(2, FilterBadWord.filter(reclamation.getLastName()));
            preparedStatement.setString(3, FilterBadWord.filter(reclamation.getDescriRec()));
            preparedStatement.setTimestamp(4, reclamation.getDateRec());
            preparedStatement.setString(5, reclamation.getCategorieRec());
            preparedStatement.setString(6, reclamation.getStatutRec());


            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Reclamation> readAll() {
        String req = "SELECT * FROM reclamation";
        ArrayList<Reclamation> reclamations = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(req);

            while (resultSet.next()) {
                reclamations.add(new Reclamation(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reclamations;
    }

    @Override
    public void update(Reclamation reclamation) {
        String query = "UPDATE reclamation SET name=?, lastName=?, descriRec=?, DateRec=?, CategorieRec=?, StatutRec=? WHERE idRec=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, FilterBadWord.filter(reclamation.getName()));
            preparedStatement.setString(2, FilterBadWord.filter(reclamation.getLastName()));
            preparedStatement.setString(3, FilterBadWord.filter(reclamation.getDescriRec()));
            preparedStatement.setTimestamp(4, reclamation.getDateRec());
            preparedStatement.setString(5, reclamation.getCategorieRec());
            preparedStatement.setString(6, reclamation.getStatutRec());
            preparedStatement.setInt(7, reclamation.getIdRec());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("updated");
            } else {
                System.out.println("not updated");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Reclamation getById(int id) {
        String query = "SELECT * FROM reclamation WHERE idRec=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Reclamation found, create and return the object
                return new Reclamation(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5),
                        resultSet.getString(6),
                        resultSet.getString(7));
            } else {
                // Reclamation not found, handle this case
                System.out.println("Reclamation with ID " + id + " not found.");
                return null; // Or throw an exception to indicate the reclamation was not found
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public void delete(Reclamation reclamation) {
        String requete="DELETE FROM reclamation WHERE idReC=?";
        try {
            PreparedStatement pst= connection.prepareStatement(requete);
            pst.setInt(1,reclamation.getIdRec());
            pst.executeUpdate();
            System.out.println("Réclamation supprimée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Reclamation readById(int idDM) {
        return null;
    }

    public String getRecFromIdRec(int id){
        String rec = null;

        String req = "SELECT * FROM reclamation WHERE idRec = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                rec = rst.getString("descriRec");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rec;
    }
}
