package Services;

import Utils.DataSource;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Iservice<User> {
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
    public void delete(User u){
        try {
            String requet="delete from user where idU=?";
            pst=conn.prepareStatement(requet);

            pst.setInt(1,u.getIdU());
            pst.executeUpdate();
            System.out.println("user supprimé");

        }
        catch (SQLException ex){
            System.err.println(ex.getMessage());
            System.out.println("user non supprimé");



        }


    }
    public void deleteById(int idU ) {
        try {
            String requete = "DELETE  FROM user WHERE idU=?";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, idU);

            pst.executeUpdate();
            System.out.println("Activité supprimée!");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Activité non supprimée!");
        }
    }

    @Override
    public void update(User u) {
        String req="update user SET NomU=?,PrenomU=?,EmailU=?,mdpU=?,RoleU=? where idU=?";
        try {
            pst=conn.prepareStatement(req);
            pst.setString(1,u.getNomU());
            pst.setString(2,u.getPrenomU());
            pst.setString(3,u.getEmailU());
            pst.setString(4,u.getMdpU());
            pst.setString(5,u.getRoleU());
            pst.setInt(6,u.getIdU()); // Setting the value for idU
            pst.executeUpdate();
        } catch (SQLException e) {
System.out.println(e.getMessage());
        }

    }

    @Override
    public List<User> readall() {
        String requete="select * from user";
        List<User>list=new ArrayList<>();

        try {
            ste=conn.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while(rs.next()){
                list.add(new User(rs.getInt("idU"),rs.getString("NomU"),rs.getString("PrenomU"),rs.getString("EmailU"),rs.getString("mdpU"),rs.getString("RoleU")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override




    public User readById(int idU) {
        User user = new User();
        String req = "SELECT * FROM user WHERE idU = ?";
        try {
            PreparedStatement pst= conn.prepareStatement(req);
            pst.setInt(1, idU);
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
