package service;

import enitites.Reclamation;
import utils.DataSource;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReclamationService implements IService<Reclamation> {
    private Connection conn;
    private PreparedStatement pst;

    public ReclamationService() {
        conn= DataSource.getInstance().getCnx();
    }


    public void add(Reclamation R){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String Datestr=sdf.format(R.getDateRec());
        String requete="INSERT INTO reclamation (idU,descriRec, DateRec, CategorieRec, StatutRec) VALUES (?,?,?,?,?)";
        try {
            pst=conn.prepareStatement(requete);
            pst.setInt(1,R.getIdU());
            pst.setString(2,R.getDescriRec());
            pst.setDate(3, (Date) R.getDateRec());
            pst.setString(4,R.getCategorieRec());
            pst.setString(5,R.getStatutRec());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    /*public void delete(Reclamation r) {
       string requete= " DELETE FROM Reclamation where idRec=?";
        try {
            pst.setInt(1,r.getIdRec());
            pst.executeUpdate();
            System.out.println("Reclamation supprime");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    public void delete(Reclamation r) {
        String requete = "DELETE FROM Reclamation WHERE idRec=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, r.getIdRec());
            pst.executeUpdate();
            System.out.println("Reclamation supprime");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteById(int idRec){
        try {
            String requete = "DELETE FROM Reclamation WHERE idRec=?";
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setInt(1,idRec);
            pst.executeUpdate();
            System.out.println("reclamation supprime");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println("reclamation non supprime");
        }


    }
    @Override
    public void update(Reclamation R) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String Datestr=sdf.format(R.getDateRec());
        String req="UPDATE `Reclamation` SET idU=?,descriRec= ?, DateRec=?, CategorieRec=?, StatutRec=? WHERE idRec";
        {
            try {
                PreparedStatement ps = conn.prepareStatement(req);
                ps.setInt(1,R.getIdU());
                ps.setString(2,R.getDescriRec());
                ps.setDate(3, new java.sql.Date(R.getDateRec().getTime()));

                //ps.setDate(3,R.getDateRec().getTime());
                ps.setString(4,R.getCategorieRec());
                ps.setString(5,R.getStatutRec());
                ps.executeUpdate();
                System.out.println("reclamtion modifier");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    @Override
    public void update(commentaireService commentaireService) {

    }

    @Override
    public List<Reclamation> readAll() {
        return null;
    }

    @Override
    public Reclamation readById(int idRec) {
        return null;
    }

}







