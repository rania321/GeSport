/*package service;

import utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import enitites.commentaire;


public class commentaireService implements IService<commentaire>{
    private Connection conn;
    private PreparedStatement pst;
    public commentaireService() {
        conn= DataSource.getInstance().getCnx();
    }

    public void add(commentaire C) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String Datestr=sdf.format(C.getDateC());
        String requete="INSERT INTO commentaire (idRec,DateC, ContenuC) VALUES (?,?,?)";
        try {
            pst=conn.prepareStatement(requete);
        pst.setInt(1,C.getIdrec());
        pst.setDate(2, (Date) C.getDateC());
        pst.setString(3,C.getContenuC());
        pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override


    @Override
    public void delete(commentaire commentaire) {

    }

    @Override
    public void update(commentaire commentaire) {

    }

    @Override
    public List<commentaire> readAll() {
        return null;
    }

    @Override
    public commentaire readById(int idRec) {
        return null;
    }
}*/
package service;

import utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import enitites.commentaire;

public class commentaireService implements IService<commentaire>{
    private Connection conn;
    private PreparedStatement pst;

    public commentaireService() {
        conn= DataSource.getInstance().getCnx();
    }

    public void add(commentaire C) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String Datestr=sdf.format(C.getDateC());
        String requete="INSERT INTO commentaire (idRec,DateC, ContenuC) VALUES (?,?,?)";
        try {
            pst=conn.prepareStatement(requete);
            pst.setInt(1,C.getIdrec());
            pst.setDate(2, (Date) C.getDateC());
            pst.setString(3,C.getContenuC());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(commentaire C) {
        String requete = "DELETE FROM Reclamation WHERE idRec=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, C.getIdC());
            pst.executeUpdate();
            System.out.println("Reclamation supprime");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
        public void deleteById(int idC){
            try {
                String requete = "DELETE FROM Commentaire WHERE idC=?";
                PreparedStatement pst=conn.prepareStatement(requete);
                pst.setInt(1,idC);
                pst.executeUpdate();
                System.out.println("commentaire supprime");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.out.println("commentaire non supprime");
            }
    }

    @Override
    public void update(commentaire commentaire) {
        // Implement update method
    }

    @Override
    public void update(commentaireService commentaireService) {

    }

    @Override
    public List<commentaire> readAll() {
        return null;
    }

    @Override
    public commentaire readById(int idRec) {
        return null;
    }
}

