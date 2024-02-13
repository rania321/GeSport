package org.example.Service;

import entities.Produit;
import entities.Vente;
import org.example.utile.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VenteService implements IService<Vente>{
    private Connection con;
    private Statement ste;

    public VenteService() {
        con = DataSource.getInstance().getCnx();
    }

    public void add(Vente v) {
        String requete = "insert into vente (idU, idP, QauntitéV, DateV, MontantV) values ('" + v.getIdU() + "', '" + v.getIdP() + "', '" + v.getQuantitéV() + "', '" + v.getDateV() + "', '" + v.getMontantV() + "')";
        try {
            ste = con.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(Vente v) {}

  /*  public void delete(Vente v) {
        String sql = "DELETE FROM vente WHERE idV = ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, v.getIdV());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Vente supprimée");}
        } catch (SQLException ex) {
            Logger.getLogger(VenteService.class.getIdV()).log(Level.SEVERE, null, ex);
        }
    }*/

    public void update(Vente v) {

    }
    public List<Vente> readAll() {
        return null;
    }

    public Vente readById(int idV) {
        return null;
    }
}