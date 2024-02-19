package service;

import entities.Activite;
import entities.Reservation;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationService implements IService<Reservation> {

    private static Connection conn;

    public ReservationService() {
        conn= DataSource.getInstance().getCnx();
    }

    /////////////CRUD AJOUT RESERVATION/////////////
    @Override
    public void add(Reservation r) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateDebutStr = sdf.format(r.getDateDebutR());

        System.out.println("id user dans ajouter"+r.getUser().getIdU());
        String requete="insert into ReservationActivite (idU,idA,DateDebutR,HeureR,statutR) values (?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, r.getUser().getIdU());
            pst.setInt(2, r.getActivite().getIdA());
            pst.setString(3, dateDebutStr);
            pst.setString(4, r.getHeureR());
            pst.setString(5, r.getStatutR());
            pst.executeUpdate();
            System.out.println("Reservation ajoutée!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while adding reservation: " + e.getMessage());
        }

    }

    @Override
    public void delete(Reservation r) {
        String requete="DELETE FROM ReservationActivite WHERE idR=?";
        try {
            PreparedStatement pst= conn.prepareStatement(requete);
            pst.setInt(1,r.getIdR());
            pst.executeUpdate();
            System.out.println("Réservation supprimée!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int idR ) {
        try {
            String requete = "DELETE  FROM ReservationActivite WHERE idR=?";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, idR);

            pst.executeUpdate();
            System.out.println("Réservation supprimée!");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Réservation non supprimée!");
        }
    }

    @Override
    public void update(Reservation r) {
        System.out.println("id user dans modifier"+r.getUser().getIdU());
        System.out.println("id activite dans modifier"+r.getActivite().getIdA());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String dateDebutStr = sdf.format(r.getDateDebutR());
        String req = "UPDATE ReservationActivite SET idU=?, idA=?, DateDebutR = ?, HeureR=?, statutR=? WHERE idR = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, r.getUser().getIdU());
            ps.setInt(2, r.getActivite().getIdA());
            ps.setString(3, dateDebutStr);
            ps.setString(4, r.getHeureR());
            ps.setString(5,r.getStatutR());
            ps.setInt(6,r.getIdR());
            ps.executeUpdate();
            System.out.println("Réservation modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reservation> readAll() {
        String requete="select * from ReservationActivite";
        List<Reservation> list=new ArrayList<>();
        try {
            Statement ste=conn.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                ActiviteService as=new ActiviteService();
                Activite a=new Activite();
                a=as.readById(rs.getInt("idA"));
                UserService us =new UserService();
                User u =new User();
                u=us.readById(rs.getInt("idU"));
                //User u= us.readById(rs.getInt("idU"));

                Reservation r= new Reservation(rs.getInt("idR"),u,a, rs.getDate("DateDebutR"),
                        rs.getString("HeureR"),rs.getString("statutR"));


                list.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Reservation readById(int id) {
        Reservation r = new Reservation();
        String req = "SELECT * FROM ReservationActivite WHERE idR = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                r.setIdR(rst.getInt("idR"));
                UserService us =new UserService();
                User u =new User();
                u=us.readById(rst.getInt("idU"));
                r.setUser(u);
                ActiviteService as=new ActiviteService();
                Activite a=new Activite();
                a=as.readById(rst.getInt("idA"));
                r.setActivite(a);
                r.setDateDebutR(rst.getDate("DateDebutR"));
                r.setHeureR(rst.getString("HeureR"));
                r.setStatutR(rst.getString("statutR"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static List<String> find_nameActivite() {
        List<String> list = new ArrayList<>();
        String req= "SELECT nomA FROM Activite";
        try {
            Statement ste=conn.createStatement();
            ResultSet rs= ste.executeQuery(req);

            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static String find_nameActivite(int id) {
        String list=null ;

        Statement statement;
        ResultSet resultSet;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT nomA FROM activite where idA="+id+"");

            while (resultSet.next()) {
                list=(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

   /* public static ArrayList<Integer> find_idroom() {
        ArrayList<Integer> list = new ArrayList<>();

        Statement statement;
        ResultSet resultSet;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT id_room FROM room");

            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Auction_Services.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static Integer find_idroom(String ID_artwork) {
        Integer result = null;

        PreparedStatement statement;
        ResultSet resultSet;
        try {

            String sql = "SELECT id_room FROM room WHERE name_room like  ? ";
            statement = conn.prepareStatement(sql);
            statement.setString(1, ID_artwork);
//        resultSet = statement.executeQuery("SELECT id_room FROM room WHERE name_room like " + ID_artwork);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Auction_Services.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }*/


}
