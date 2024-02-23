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
        String req = "UPDATE `produit` SET nomP=?, descriP=?, PrixP=?, StockP=?, DateAjoutP=?, imageP=?, referenceP=? WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);

            ps.setString(1,   p.getNomP());
            ps.setString(2,   p.getDescriP());
            ps.setFloat( 3,   p.getPrixP());
            ps.setInt(   4,   p.getStockP());
            ps.setDate(  5,   p.getDateAjoutP());
            ps.setInt(   6,   p.getIdP());
            ps.setString(7,   p.getImageP());
            ps.setInt(8,   p.getReferenceP());


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
                p.setNomP(rst.getString("NomP"));
                p.setPrixP(rst.getFloat("PrixP"));
                p.setImageP(rst.getString("imageP"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }
/*public Produit readById(int idP) {
        Produit p = new Produit();
        String req = "SELECT * FROM produit WHERE idP = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idP);
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
    }*/

}
