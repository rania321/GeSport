package service;

import entities.Activite;
import entities.Reservation;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.util.List;

public class UserService implements IService<User> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;


    public UserService() {
        conn= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(User u) {
        String requete="insert into user (NomU,PrenomU,EmailU,mdpU,RoleU) value(?,?,?,?,?)";
        try {
            pst=conn.prepareStatement(requete);
            pst.setString(1,u.getNomU());
            pst.setString(2,u.getPrenomU());
            pst.setString(3,u.getEmailU());
            pst.setString(4,u.getMdpU());
            pst.setString(5,u.getRoleU());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User a) {

    }

    @Override
    public void update(User a) {

    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public User readById(int id) {
        User user = new User();
        String req = "SELECT * FROM user WHERE idU = ?";
        try {
            PreparedStatement pst= conn.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.setIdU(rs.getInt("idU"));
                user.setNomU(rs.getString("NomU"));
                user.setPrenomU(rs.getString("PrenomU"));
                user.setEmailU(rs.getString("EmailU"));
                user.setMdpU(rs.getString("mdpU"));
                user.setRoleU(rs.getString("RoleU"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}


