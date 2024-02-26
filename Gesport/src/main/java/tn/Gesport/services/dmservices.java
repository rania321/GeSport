package tn.Gesport.services;

import tn.Gesport.utils.Database;
import tn.Gesport.models.User;
import tn.Gesport.models.dossiermedical;
import tn.Gesport.iservices.IService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dmservices implements IService<dossiermedical> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public dmservices() {
        conn = Database.getInstance().getConnection();
    }

    public void add(dossiermedical d) {
        int userId = d.getUse().getIdU(); // Get the idU value of the associated user directly from dossiermedical
        System.out.println("User ID: " + userId);
        String requete = "insert into dossiermedical (idU, poidsDM, tailleDM, ageDM) values (?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, userId); // Set the idU value
            pst.setString(2, d.getPoidsDM());
            pst.setString(3, d.getTailleDM());
            pst.setInt(4, d.getAgeDM());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(dossiermedical d) {

        try {
            String requet = "delete from dossiermedical where idDM=?";
            pst = conn.prepareStatement(requet);

            pst.setInt(1, d.getIdDM());
            pst.executeUpdate();
            System.out.println("dossier supprimé");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("dossier non supprimé");


        }
        return false;
    }
    public void deleteById(int idDM ) {
        try {
            String requete = "DELETE  FROM dossiermedical WHERE idDM=?";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, idDM);

            pst.executeUpdate();
            System.out.println("Réservation supprimée!");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Réservation non supprimée!");
        }
    }

    @Override
    public void update(dossiermedical d) {
        String req = "update dossiermedical SET idU=?,poidsDM=?,tailleDM=?,ageDM=? where idDM=?";
        try {
            pst = conn.prepareStatement(req);
            pst.setInt(1, d.getUse().getIdU());
            pst.setString(2, d.getPoidsDM());
            pst.setString(3, d.getTailleDM());
            pst.setInt(4, d.getAgeDM());
            pst.setInt(5, d.getIdDM());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public ArrayList<dossiermedical> getAll() {
        String requete = "select * from dossiermedical";
        List<dossiermedical> list = new ArrayList<>();


        try (Statement ste = conn.createStatement()) {
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                UserService us=new UserService();
                User user = new User();
                user=us.readById(rs.getInt("idU"));
                // Instantiate dossiermedical object using the retrieved User object
                dossiermedical dossier = new dossiermedical(rs.getInt("idDM"), user, rs.getString("poidsDM"),
                        rs.getString("tailleDM"), rs.getInt("ageDM"));
                list.add(dossier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (ArrayList<dossiermedical>) list;
    }

    @Override
    public User readById(int idDM) {
        dossiermedical d=new dossiermedical();
        String req = "SELECT * FROM dossiermedical WHERE idDM = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, idDM);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                d.setIdDM(rst.getInt("idDM"));
                UserService us =new UserService();
                User u =new User();
                u=us.readById(rst.getInt("idU"));
                d.setuse(u);
                d.setPoidsDM(rst.getString("poidsDM"));
                d.setTailleDM(rst.getString("tailleDM"));
                d.setAgeDM(rst.getInt("ageDM"));




            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return d.getUse();
    }

    public int getUserId(int idU) {
        // Implement the logic to retrieve the user ID based on the provided idU
        // This could involve querying the database or some other mechanism
        // For now, let's just return the provided idU
        return idU;
    }
    public User getUserById(int idU) {
        // Implement the logic to retrieve user details based on the provided idU
        // This could involve querying the database or some other mechanism
        // For now, let's just return null
        return null;
    }
}


