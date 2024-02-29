package org.example.Service;

import entities.Produit;
import entities.Vente;
import org.example.utile.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VenteService implements IService<Vente> {
    private Connection con;
 ProduitService ps = new ProduitService();
    Map<String, Integer> ventesMap = new HashMap<>();

    //private Statement ste;

    public VenteService() {
        con = DataSource.getInstance().getCnx();
    }

    public  Map<String, Integer> mapNomQte(){
        try {
            // Exécuter la requête SQL pour récupérer les données
            String query = "SELECT idP, QuantitéV FROM vente";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Remplir le HashMap avec les données de la base de données
            while (resultSet.next()) {
                int idP = resultSet.getInt("idP");
                //rendre id nom
                String nomidp = ps.getNomFromIdProduit(idP);
                int quantite = resultSet.getInt("QuantitéV");

                if (ventesMap.containsKey(nomidp)) {
                    // Ajouter la quantité à la valeur existante
                    ventesMap.put(nomidp, ventesMap.get(nomidp) + quantite);
                } else {
                    // Ajouter une nouvelle entrée dans la map
                    ventesMap.put(nomidp, quantite);
                }
            }

            // Afficher le HashMap
            for (Map.Entry<String, Integer> entry : ventesMap.entrySet()) {
                System.out.println("Nom produit : " + entry.getKey() + ", Quantité de vente : " + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventesMap;
    }

    public void add(Vente v) {
        String requete = "insert into vente (idU, idP, QuantitéV, DateV, MontantV) values (?,?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, v.getIdU());
            pst.setInt(2, v.getIdP());
            pst.setInt(3, v.getQuantitéV());
            pst.setDate(4, v.getDateV());
            pst.setFloat(5, v.getMontantV());

            pst.executeUpdate();
            System.out.println("Vente effectuée avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Vente v) {
        String requete = "DELETE FROM vente where idV=?";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, v.getIdV());
            pst.executeUpdate();
            System.out.println("Vente annulée avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Id(Vente v) {
        int a=v.getIdV();
        System.out.println(a);
    }

    public void update(Vente v) {
        //int idV = getIdVFromDatabase(v);

        String req = "UPDATE `vente` SET idU=?, idP=?, QuantitéV=?, DateV=?, MontantV=? WHERE idV = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1,   v.getIdU());
            ps.setInt(2,   v.getIdP());
            ps.setInt(3,   v.getQuantitéV());
            ps.setDate(4,  v.getDateV());
            ps.setFloat(5, v.getMontantV());
            ps.setInt(6,   v.getIdV());

            ps.executeUpdate();
            System.out.println("Vente modifiée avec succes");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Modification non effectuée : ERREUR !");
        }
    }

    public List<Vente> readAll() {
        String requete = "select * from vente";

        List<Vente> list = new ArrayList<>();
        try {
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Vente v = new Vente(rs.getInt("idV"), rs.getInt("idU"), rs.getInt("idP"), rs.getInt("QuantitéV"), rs.getDate("DateV"), rs.getFloat("MontantV"));
                list.add(v);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.toString());
        return list;
    }

    public Vente readById(int idV) {
        Vente v = new Vente();
        String req = "SELECT * FROM vente WHERE idV = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idV);
            ResultSet rst = ps.executeQuery();
            while (rst.next())
            {
                v.setIdV(rst.getInt("idV"));
                v.setIdU(rst.getInt("idU"));
                v.setIdP(rst.getInt("idP"));
                v.setQuantitéV(rst.getInt("QuantitéV"));
                v.setDateV(rst.getDate("DateV"));
                v.setMontantV(rst.getFloat("MontantV"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return v;
    }

    /*public int getIdVFromDatabase(Vente v) {

        String requete = "SELECT idV FROM vente WHERE idU = ? AND idP = ? AND quantitéV = ? AND dateV = ? AND montantV = ?";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(  1, v.getIdU());
            pst.setInt(  2, v.getIdP());
            pst.setInt(  3, v.getQuantitéV());
            pst.setDate( 4, v.getDateV());
            pst.setFloat(5, v.getMontantV());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getInt("idV"));
                return rs.getInt("idV");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("ID NULL !");
        return -1;
    }*/

    public boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isNumericInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public float calculerRevenusDuMois() {
        float sommeRevenus = 0;

        try {
            // Obtenez la date du 1er jour du mois actuel
            LocalDate debutMois = LocalDate.now().withDayOfMonth(1);

            String req = "SELECT SUM(MontantV) AS totalRevenus FROM vente WHERE DateV >= ?";
            PreparedStatement st = con.prepareStatement(req);
            st.setDate(1, java.sql.Date.valueOf(debutMois));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                sommeRevenus = rs.getFloat("totalRevenus");
                System.out.println("Somme des revenus du mois : " + sommeRevenus);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return sommeRevenus;
    }

    public int getMostSoldProductID() {
        int mostSoldProductID = -1; // Valeur par défaut en cas d'erreur ou de résultat vide

        try {
            String query = "SELECT idP, COUNT(*) as totalSales FROM vente GROUP BY idP ORDER BY totalSales DESC LIMIT 1";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                mostSoldProductID = resultSet.getInt("idP");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du produit le plus vendu : " + ex.getMessage());
        }

        return mostSoldProductID;
    }

    public int sumQte() {
        int sum = 0;

        try {
            // Exécuter la requête SQL pour récupérer la somme des quantités
            String query = "SELECT SUM(QuantitéV) AS totalQuantite FROM vente";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Récupérer la somme des quantités
            if (resultSet.next()) {
                sum = resultSet.getInt("totalQuantite");
            }

            // Afficher la somme
            System.out.println("Somme des quantités de vente : " + sum);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sum;
    }

}