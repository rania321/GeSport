package service;

import entities.Activite;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteService implements IService<Activite>{

    private Connection conn;

    public ActiviteService() {
        conn= DataSource.getInstance().getCnx();
    }


    ////////////////CRUD AJOUT ACTIVITE////////////////////////
    @Override
    public void add(Activite a){
        String requete="insert into Activite (NomA,TypeA,DispoA,DescriA,imageA) values (?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, a.getNomA());
            pst.setString(2, a.getTypeA());
            pst.setString(3, a.getDispoA());
            pst.setString(4, a.getDescriA());
            pst.setString(5, a.getImageA());
            pst.executeUpdate();
            System.out.println("Activite ajouté!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    ////////////////CRUD Suppression ACTIVITE////////////////////////
    @Override
    public void delete(Activite a) {
        String requete="DELETE FROM activite WHERE idA=?";
        try {
            PreparedStatement pst= conn.prepareStatement(requete);
            pst.setInt(1,a.getIdA());
            pst.executeUpdate();
            System.out.println("Activité supprimée!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteById(int idA ) {
        try {
            String requete = "DELETE  FROM activite WHERE idA=?";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, idA);

            pst.executeUpdate();
            System.out.println("Activité supprimée!");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Activité non supprimée!");
        }
    }

    ////////////////////CRUD MODIFIER ACTIVITE/////////////////
    @Override
    public void update(Activite a) {
        String req = "UPDATE `activite` SET NomA=?, TypeA=?, DispoA = ?,DescriA=?, imageA=? WHERE idA = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, a.getNomA());
            ps.setString(2, a.getTypeA());
            ps.setString(3, a.getDispoA());
            ps.setString(4, a.getDescriA());
            ps.setString(5, a.getImageA());
            ps.setInt(6,a.getIdA());
            ps.executeUpdate();
            System.out.println("Acivité modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //////////////////////////CRUD AFFICHER ACTIVITE/////////////////////
    @Override
    public List<Activite> readAll() {
        String requete="select * from activite";
        List<Activite> list=new ArrayList<>();
        try {
            Statement ste=conn.createStatement();
           ResultSet rs= ste.executeQuery(requete);
           while(rs.next()){
               Activite a= new Activite(rs.getInt("idA"),rs.getString("NomA"),rs.getString("TypeA"),rs.getString("DispoA"),rs.getString("DescriA"),rs.getString("imageA"));
               list.add(a);
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }



    @Override
    public Activite readById(int id) {
        Activite a = new Activite();
        String req = "SELECT * FROM activite WHERE idA = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                a.setIdA(rst.getInt("idA"));
                a.setNomA(rst.getString("NomA"));
                a.setTypeA(rst.getString("TypeA"));
                a.setDispoA(rst.getString("DispoA"));
                a.setDescriA(rst.getString("DescriA"));
                a.setDescriA(rst.getString("imageA"));

               /*String nom = rst.getString("nom");
                Time horaire = rst.getTime("horaire");
                String jour = rst.getString("jourseance");
                int numSalle = rst.getInt("numesalle");
                String duree= rst.getString("duree");
                a = new Activite(id, nom,horaire,jour,numSalle,duree);*/
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }
}
