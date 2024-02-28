package service;

import entities.Activite;
import entities.Reservation;
import entities.User;
import utils.DataSource;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public List<Reservation> getReservationsByUserAndActivity(User user, Activite activite) {
        List<Reservation> reservations = new ArrayList<>();
        String requete = "SELECT * FROM ReservationActivite WHERE idU = ? AND idA = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, user.getIdU());
            pst.setInt(2, activite.getIdA());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setIdR(rs.getInt("idR"));
                r.setUser(user);
                r.setActivite(activite);
                r.setDateDebutR(rs.getDate("DateDebutR"));
                r.setHeureR(rs.getString("HeureR"));
                r.setStatutR(rs.getString("statutR"));

                reservations.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching reservations by user and activity: " + e.getMessage());
        }

        return reservations;
    }
    public List<Reservation> getReservationsByActivityAndDate(Activite activite, LocalDate date) {
        List<Reservation> reservations = new ArrayList<>();
        String requete = "SELECT * FROM ReservationActivite r1 WHERE idA = ? AND DateDebutR = ? AND NOT EXISTS (SELECT 1 FROM ReservationActivite r2 WHERE r2.idA = r1.idA AND r2.DateDebutR = r1.DateDebutR AND r2.HeureR = r1.HeureR AND r2.idR <> r1.idR)";

        try {
            PreparedStatement ps = conn.prepareStatement(requete);
            ps.setInt(1, activite.getIdA());
            ps.setDate(2, Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new UserService().readById(rs.getInt("idU"));
                Activite activity = new ActiviteService().readById(rs.getInt("idA"));

                Reservation reservation = new Reservation(
                        rs.getInt("idR"),
                        user,
                        activity,
                        rs.getDate("DateDebutR"),
                        rs.getString("HeureR"),
                        rs.getString("statutR")
                );

                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching reservations: " + e.getMessage());
        }

        return reservations;
    }
    public List<String> getAvailableHoursForActivityAndDate(Activite activite, LocalDate selectedDate) {
        List<String> allHours = Arrays.asList("08:00", "09:30", "11:00", "12:30", "14:00", "15:30", "17:00");

        // Récupérer les réservations existantes pour l'activité et la date sélectionnée
        List<Reservation> reservations = getReservationsByActivityAndDate(activite, selectedDate);

        // Extraire les heures réservées de ces réservations
        List<String> reservedHours = reservations.stream()
                .map(Reservation::getHeureR)
                .collect(Collectors.toList());

        // Filtrer les heures disponibles en excluant les heures réservées
        List<String> availableHours = allHours.stream()
                .filter(hour -> !reservedHours.contains(hour))
                .collect(Collectors.toList());

        return availableHours;
    }

    public Map<String, Integer> getReservationStatisticsByActivity() {
        Map<String, Integer> statistics = new HashMap<>();
        String requete = "SELECT nomA, COUNT(*) as count FROM ReservationActivite ra " +
                "JOIN Activite a ON ra.idA = a.idA " +
                "GROUP BY ra.idA, nomA";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(requete);

            while (rs.next()) {
                String activityName = rs.getString("nomA");
                int reservationCount = rs.getInt("count");

                statistics.put(activityName, reservationCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching reservation statistics: " + e.getMessage());
        }

        return statistics;
    }

    // Méthode pour récupérer la liste de tous les statuts de réservation
    public List<String> getAllReservationStatus() {
        List<String> statuses = new ArrayList<>();
        String query = "SELECT DISTINCT statutR FROM ReservationActivite";

        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                statuses.add(rs.getString("statutR"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching reservation statuses: " + e.getMessage());
        }

        return statuses;
    }


}
