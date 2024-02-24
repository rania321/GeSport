package org.example.Service;

import entities.Panier;
import entities.Produit;
import org.example.utile.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements IService <Panier>{
    private Connection con;
    public PanierService() {
        con= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Panier pa) {
        String requete= "insert into panier (idP,idV,quantiteP,totalPa) values (?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(requete);

            pst.setInt(1, pa.getIdP());
            pst.setInt(2, pa.getIdV());
            pst.setFloat(3, pa.getQuantiteP());
            pst.setFloat(4,pa.getTotalPa());

            pst.executeUpdate();
            System.out.println("Produit ajouté au panier avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Panier pa) {
        String requete="DELETE FROM panier WHERE idP=?";
        try {
            PreparedStatement pst= con.prepareStatement(requete);
            pst.setInt(1, pa.getIdP());
            pst.executeUpdate();
            System.out.println("Produit supprimé avec succes");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            System.out.println("Produit non supprimé!");
        }
    }

    @Override
    public void update(Panier pa) {
        String req = "UPDATE `panier` SET idP=?, totalPa=?, quantiteP=? WHERE idV = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);

            ps.setInt(   1,   pa.getIdP());
            ps.setFloat( 2,   pa.getTotalPa());
            ps.setInt(   3,   pa.getQuantiteP());
            ps.setInt(   4,   pa.getIdV());

            ps.executeUpdate();
            System.out.println("Panier modifié avec succes");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Modification non effectuée : ERREUR !");
        }
    }

    @Override
    public List<Panier> readAll() {
        String requete = "select * from panier";
        List<Panier> list = new ArrayList<>();
        try {
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Panier pa = new Panier(rs.getInt("idV"), rs.getInt("idP"), rs.getInt("quantiteP"), rs.getFloat("totalPa"));
                list.add(pa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.toString());
        return list;
    }

    @Override
    public Panier readById(int idV) {
        Panier pa = new Panier();
        String req = "SELECT * FROM panier WHERE idV = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idV);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                pa.setIdP(rst.getInt("idP"));
                pa.setQuantitéP(rst.getInt("quantiteP"));
                pa.setTotalPa(rst.getFloat("totalPa"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pa;    }
}
