package org.example.Service;

import entities.Produit;
import entities.Vente;
import org.example.utile.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VenteService implements IService<Vente> {
    private Connection con;
    //private Statement ste;

    public VenteService() {
        con = DataSource.getInstance().getCnx();
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
        String requete = "DELETE FROM vente WHERE idV=?";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1, v.getIdV());
            pst.executeUpdate();
            System.out.println("Vente annulée avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Vente v) {
        //int idV = getIdVFromDatabase(v);

        String req = "UPDATE `vente` SET idU=?, idP=?, QuantitéV=?, DateV=?, MontantV=? WHERE idV = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, v.getIdU());
            ps.setInt(2, v.getIdP());
            ps.setInt(3, v.getQuantitéV());
            ps.setDate(4, v.getDateV());
            ps.setFloat(5, v.getMontantV());
            ps.setInt(6, v.getIdV());

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
                Vente v = new Vente(rs.getInt("idV"), rs.getInt("IdU"), rs.getInt("IdP"), rs.getInt("QuantitéV"), rs.getDate("DateV"), rs.getFloat("MontantV"));
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
                v.setIdU(rst.getInt("IdU"));
                v.setIdP(rst.getInt("IdP"));
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
}