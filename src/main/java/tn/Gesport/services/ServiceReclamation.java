package tn.Gesport.services;

import tn.Gesport.iservices.IService;
import tn.Gesport.models.Reclamation;
import tn.Gesport.utils.Database;

import java.sql.*;
import java.util.ArrayList;

public class ServiceReclamation implements IService<Reclamation> {


    private Connection connection;

    public ServiceReclamation() {
        connection = Database.getInstance().getConnection();
    }
    @Override
    public void add(Reclamation reclamation) {
        String query = "INSERT INTO reclamation (idU, descriRec, DateRec, CategorieRec, StatutRec) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, reclamation.getIdU());
            preparedStatement.setString(2, reclamation.getDescriRec());
            preparedStatement.setDate(3, reclamation.getDateRec());
            preparedStatement.setString(4, reclamation.getCategorieRec());
            preparedStatement.setString(5, reclamation.getStatutRec());

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Reclamation> getAll() {
        String req="SELECT * FROM `reclamation`";
        ArrayList<Reclamation> reclamations = new ArrayList<>();
        try{
            Statement st= connection.createStatement();
            ResultSet resultSet =st.executeQuery(req);

            while(resultSet.next()){
                reclamations.add(new Reclamation(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getNString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return reclamations;
    }


    @Override
    public void update(Reclamation reclamation) {

        String query= "UPDATE `reclamation` SET `idU`=?,`descriRec`=?,`DateRec`=?,`CategorieRec`=?,`StatutRec`=? WHERE `idRec`=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,reclamation.getIdU());
            preparedStatement.setString(2,reclamation.getDescriRec());
            preparedStatement.setDate(3, reclamation.getDateRec());
            preparedStatement.setString(4,reclamation.getCategorieRec());
            preparedStatement.setString(5,reclamation.getStatutRec());
            preparedStatement.setInt(6,reclamation.getIdRec());

            int rows = preparedStatement.executeUpdate();
            if(rows > 0) {
                System.out.println("updated");
            } else {
                System.out.println("not updated");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public Reclamation getById(int id) {
        String query = "SELECT * from `reclamation` WHERE `idRec`=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return new Reclamation(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getNString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
            }
            return null;

        } catch (SQLException exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(Reclamation reclamation) {
        String query = "DELETE FROM `reclamation` WHERE idRec=?";
        int rows = 0;

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reclamation.getIdRec());

            rows = preparedStatement.executeUpdate();
            if (rows > 0) return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
