package Services;

import util.DataSource;
import controllers.LoginUserControllers;
import entities.User;
import entities.dossiermedical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dmservices implements Iservice<dossiermedical> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;


    public dmservices() {
        conn = DataSource.getInstance().getCnx();
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
    public void delete(dossiermedical d) {

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
        /*String req = "update dossiermedical SET idU=?,poidsDM=?,tailleDM=?,ageDM=? where idDM=?";
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
        }*/
        if (d.getUse() == null) {
            System.err.println("Utilisateur associé au dossier médical est nul.");
            return; // Sortir de la méthode si l'utilisateur est nul
        }

        String req = "update dossiermedical SET idU=?, poidsDM=?, tailleDM=?, ageDM=? where idDM=?";
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
    public List<dossiermedical> readall() {
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
        return list;
    }

    @Override
    public dossiermedical readById(int idDM) {
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
        return d;
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
    public boolean dossierMedicalExistsForUser(int userId) {
        try {
            // Créer une requête SQL pour vérifier si un dossier médical existe pour l'utilisateur spécifié
            String query = "SELECT COUNT(*) FROM dossiermedical WHERE idU = ?";

            // Préparer la requête
            pst = conn.prepareStatement(query);
            pst.setInt(1, userId);

            // Exécuter la requête et récupérer le résultat
            ResultSet rs = pst.executeQuery();
            rs.next(); // Déplacer le curseur sur la première ligne (il n'y en aura qu'une seule)

            // Récupérer le résultat du comptage
            int count = rs.getInt(1);

            // Vérifier si un dossier médical existe pour cet utilisateur
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public dossiermedical getDossierMedicalByUserId(int userId) {
        // Vérification de la connexion JDBC
        if (conn == null) {
            System.err.println("La connexion JDBC n'est pas initialisée.");
            return null;
        }

        // Vérification de l'utilisateur connecté
        if (LoginUserControllers.getLoggedInUser() == null) {
            System.err.println("Utilisateur non connecté.");
            return null;
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        dossiermedical dm = null;

        try {
            String query = "SELECT * FROM dossiermedical WHERE idU = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            rs = pst.executeQuery();

            // Affichage des résultats du ResultSet
            while (rs.next()) {
                int idU=rs.getInt("idU");
                int idDM = rs.getInt("idDM");
                String poidsDM = rs.getString("poidsDM");
                String tailleDM = rs.getString("tailleDM");
                int ageDM = rs.getInt("ageDM");

                // Afficher les valeurs dans la console
                System.out.println("idDM: " + idDM + ", poidsDM: " + poidsDM + ", tailleDM: " + tailleDM + ", ageDM: " + ageDM);

                // Création de l'objet dossiermedical avec les valeurs récupérées
                dm = new dossiermedical();
                dm.setuse(LoginUserControllers.getLoggedInUser());
                dm.setIdDM(idDM);
                dm.setPoidsDM(poidsDM);
                dm.setTailleDM(tailleDM);
                dm.setAgeDM(ageDM);
            }

            // Autres traitements si nécessaire
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources JDBC
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Retourner l'objet dossiermedical récupéré
        return dm;



    }

}


