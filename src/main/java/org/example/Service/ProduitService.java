package org.example.Service;

import entities.Produit;
import org.example.utile.DataSource;
import java.security.Provider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IService <Produit>{

    private Connection con;

    //private Statement ste;
    public ProduitService() {
        con= DataSource.getInstance().getCnx();
    }
    public void add (Produit p)
    {
        String requete= "insert into produit (nomP, descriP, PrixP, StockP, DateAjoutP, imageP, referenceP) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(requete);

            pst.setString(1, p.getNomP());
            pst.setString(2, p.getDescriP());
            pst.setFloat(3, p.getPrixP());
            pst.setInt(4,p.getStockP());
            pst.setDate(5, p.getDateAjoutP());
            pst.setString(6, p.getImageP());
            pst.setInt(7, p.getReferenceP());

            pst.executeUpdate();
            System.out.println("Produit ajouté avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Produit p)
    {
        String requete="DELETE FROM produit WHERE idP=?";
        try {
            PreparedStatement pst= con.prepareStatement(requete);
            pst.setInt(1, p.getIdP());
            pst.executeUpdate();
            System.out.println("Produit supprimé avec succes");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            System.out.println("Produit non supprimé!");
        }
    }

    @Override
    public void update(Produit p) {
        String req = "UPDATE `produit` SET nomP=?, descriP=?, PrixP=?, StockP=?, DateAjoutP=?, imageP=? WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);

            ps.setString(1,   p.getNomP());
            ps.setString(2,   p.getDescriP());
            ps.setFloat( 3,   p.getPrixP());
            ps.setInt(   4,   p.getStockP());
            ps.setDate(  5,   p.getDateAjoutP());
            ps.setString(6,   p.getImageP());
            ps.setInt(   7,   p.getIdP());

            ps.executeUpdate();
            System.out.println("Produit modifié avec succes");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Modification non effectuée : ERREUR !");
        }
    }

    @Override
    public List<Produit> readAll() {
        String requete = "select * from produit";
        List<Produit> list = new ArrayList<>();
        try {
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Produit p = new Produit(rs.getInt("idP"), rs.getString("NomP"), rs.getString("DescriP"), rs.getFloat("PrixP"), rs.getInt("StockP"), rs.getDate("DateAjoutP"), rs.getString("imageP"),rs.getInt("referenceP"));
                list.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.toString());
        return list;
    }

    @Override
    public Produit readById(int idP) {
        Produit p = new Produit();
        String req = "SELECT * FROM produit WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idP);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                p.setIdP(rst.getInt("idP"));
                p.setStockP(rst.getInt("StockP"));
                p.setNomP(rst.getString("NomP"));
                p.setDescriP(rst.getString("descriP"));
                p.setReferenceP(rst.getInt("referenceP"));
                p.setPrixP(rst.getFloat("PrixP"));
                p.setImageP(rst.getString("imageP"));
                p.setDateAjoutP(rst.getDate("DateAjoutP"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public Produit readByRef(int ref) {
        Produit p = new Produit();
        String req = "SELECT * FROM produit WHERE referenceP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, ref);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                p.setIdP(rst.getInt("idP"));
                p.setNomP(rst.getString("NomP"));
                p.setDescriP(rst.getString("DescriP"));
                p.setPrixP(rst.getFloat("PrixP"));
                p.setStockP(rst.getInt("StockP"));
                p.setDateAjoutP(rst.getDate("DateAjoutP"));
                p.setImageP(rst.getString("imageP"));
                p.setReferenceP(rst.getInt("referenceP"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }


    public boolean referenceExists(String reference) {
        try {
            String req = "SELECT COUNT(*) FROM produit WHERE referenceP = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1, reference);
            ResultSet rst = ps.executeQuery();
            rst.next();

            int count = rst.getInt(1);
            return count > 0; // Si count > 0, la référence existe déjà
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
            return false;
        }
    }

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

    public String getImageFromIdProduit(int id) {
        String imageP = null;  // Variable pour stocker le contenu de imageP

        String req = "SELECT * FROM produit WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                imageP = rst.getString("imageP");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return imageP;
    }
    public String getNomFromIdProduit(int id) {
        String nom = null;

        String req = "SELECT * FROM produit WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                nom = rst.getString("nomP");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nom;
    }
    public float getPrixFromIdProduit(int id) {
        float prix = 0.0f;

        String req = "SELECT * FROM produit WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                prix = rst.getFloat("prixP");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prix;
    }

    public int getQteFromIdProduit(int id) {
        int qte = 0;

        String req = "SELECT * FROM produit WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                qte = rst.getInt("StockP");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return qte;
    }

    public List<Produit> RechercheProduit(int ref) {
        List<Produit> produit = new ArrayList<>();
        try {
            String req ="select * from produit WHERE referenceP = '"+ref+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                Produit p = new Produit();
                p.setIdP(rs.getInt("idP"));
                p.setNomP(rs.getString("nomP"));
                p.setDescriP(rs.getString("descriP"));
                p.setImageP(rs.getString("imageP"));
                p.setDateAjoutP(rs.getDate("DateAjoutP"));
                p.setStockP(rs.getInt("StockP"));
                p.setPrixP(rs.getFloat("PrixP"));

                produit.add(p);
                System.out.println("Nom du produit avec la reference : "+ref+" est :"+p.getNomP());
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return produit;
    }
//String req = "SELECT * FROM produit WHERE quantite = 0";

    public int pRupture() {
        int nb=0;
        try {
            String req ="SELECT * FROM produit WHERE StockP = 0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                nb++;
                System.out.println("Nombre de produit en rupture de stock =  "+ nb);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return nb;
    }
    public int pPrevRupture() {
        int nb=0;
        try {
            String req ="SELECT * FROM produit WHERE StockP <= 5";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                nb++;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("Nombre de produit qui sera en rupture de stock =  "+ nb);

        return nb;
    }

    public Produit getProduitFromId(int idP) {
        List<Produit> list = new ArrayList<>();
        for (Produit produit : list) {
            if (produit.getIdP() == idP) {
                return produit;
            }
        }
        return null;
    }

    public void updateQuantite(Produit p, int qte)
    {
        String req = "UPDATE `produit` SET StockP=? WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);

            ps.setInt(   1,   qte);
            ps.setInt(   2,   p.getIdP());

            ps.executeUpdate();
            System.out.println("Stock diminué");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Modification non effectuée : ERREUR !");
        }
    }

}
