package org.example.Service;

import entities.Produit;
import org.example.utile.DataSource;
import java.security.Provider;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProduitService implements IService <Produit>{

    private Connection con;
    private Statement ste;
    public ProduitService() {
        con= DataSource.getInstance().getCnx();
    }
    public void add (Produit p)
    {
        String requete= "insert into produit (nomP, descriP, PrixP, StockP, DateAjoutP) values ('p.getNomP()', 'p.getDescriP()', 'p.getPrixP()', 'p.getStockP()', 'p.getDateAjoutP()')";
        try {
            ste = con.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /* public void addpst(Produit produit)
    {
        String redquest="insert into produit (nomP, descriP, PrixP, StockP, DateAjoutP) values (?,?,?,?,?)";
        try {
            ste = con.prepareStatement(redquest);
            //ste.setString(1, produit.getNomP());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Override
    public void delete(Produit produit) {

    }

    @Override
    public void update(Produit produit) {

    }

    @Override
    public List<Produit> readAll() {
        return null;
    }

    @Override
    public Produit readById(int idP) {
        return null;
    }
}
