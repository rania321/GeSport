package Services;

import service.IService;
import entities.Response;
import entities.User;
import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceResponse implements IService<Response> {

    private Connection connection;

    public ServiceResponse() {
        connection = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Response response) {
        String query = "INSERT INTO response (idRec, dateRep, contenuRep) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, response.getIdRec());
            preparedStatement.setTimestamp(2, response.getDateRep());
            preparedStatement.setString(3, response.getContenuRep());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Response> readAll() {
        String req = "SELECT * FROM response";
        ArrayList<Response> responses = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                responses.add(new Response(
                        resultSet.getInt("idRep"),
                        resultSet.getInt("idRec"),
                        resultSet.getTimestamp("dateRep"),
                        resultSet.getString("contenuRep")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return responses;
    }

    @Override
    public void update(Response response) {
        String query = "UPDATE response SET dateRep=?, contenuRep=? WHERE idRep=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, response.getDateRep());
            preparedStatement.setString(2, response.getContenuRep());
            preparedStatement.setInt(3, response.getIdRep());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Response response) {
        String requete="DELETE FROM response WHERE idRep=?";
        try {
            PreparedStatement pst= connection.prepareStatement(requete);
            pst.setInt(1,response.getIdRep());
            pst.executeUpdate();
            System.out.println("Réclamation supprimée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Response readById(int idDM) {
        return null;
    }

    public Response getById(int id) {
        String query = "SELECT * FROM response WHERE idRep=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Response(
                        resultSet.getInt("idRep"),
                        resultSet.getInt("idRec"),
                        resultSet.getTimestamp("dateRep"),
                        resultSet.getString("contenuRep")
                );
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
