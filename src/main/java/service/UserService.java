package service;

import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import utils.DataSource;

public class UserService implements iService<User> {
    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    public UserService() {
    }

    public void add(User user) {
        String requete = "insert into user (NomU,PrenomU,EmailU,mdpU,RoleU) value(?,?,?,?,?)";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, user.getNomU());
            this.pst.setString(2, user.getPrenomU());
            this.pst.setString(3, user.getEmailU());
            this.pst.setString(4, user.getMdpU());
            this.pst.setString(5, user.getRoleU());
            this.pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(User user) {
    }

    public void update(User user) {
    }

    public List<User> readAll() {
        return null;
    }

    public User readById(int id) {
        User user = new User();
        String req = "SELECT * FROM user WHERE idU = ?";

        try {
            PreparedStatement pst = this.conn.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
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
