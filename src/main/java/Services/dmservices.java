package Services;

import Utils.DataSource;
import entities.User;
import entities.dossiermedical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dmservices implements Iservice<dossiermedical> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public dmservices() {
        conn = DataSource.getInstance().getCnx();
    }

    public void add(dossiermedical d) {
        String requete = "insert into dossiermedical (idU,poidsDM,tailleDM,ageDM) value(?,?,?,?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, d.getIdU());
            pst.setString(2, d.getPoidsDM());
            pst.setString(3, d.getTailleDM());
            pst.setInt(4, d.getAgeDM());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int idDM) {

        try {
            String requet = "delete from dossiermedical where idDM=?";
            pst = conn.prepareStatement(requet);

            pst.setInt(1, idDM);
            pst.executeUpdate();
            System.out.println("dossier supprimé");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("dossier non supprimé");


        }
    }

    @Override
    public void update(dossiermedical d, int idDM) {
        String req="update dossiermedical SET idU=?,poidsDM=?,tailleDM=?,ageDM=? where idDM=?";
        try {
            pst=conn.prepareStatement(req);
            pst.setInt(1,d.getIdU());
            pst.setString(2,d.getPoidsDM());
            pst.setString(3,d.getTailleDM());
            pst.setInt(4,d.getAgeDM());
            pst.setInt(5, idDM);

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public List<dossiermedical> readall() {
        String requete="select * from dossiermedical";
        List<dossiermedical>list=new ArrayList<>();

        try {
            ste=conn.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while(rs.next()){
                list.add(new dossiermedical(rs.getInt("idDM"),rs.getInt("idU"),rs.getString("poidsDM"),rs.getString("tailleDM"),rs.getInt("ageDM")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public dossiermedical readByid(int idDM) {
        return null;
    }
}

