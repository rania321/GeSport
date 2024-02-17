package service;

import entities.Tournoi;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;

public class TournoiService implements iService<Tournoi> {
    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    public TournoiService() {
    }

    public void add(Tournoi t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebutStr = sdf.format(t.getDateDebutT());
        String dateFinStr = sdf.format(t.getDateFinT());
        String requete = "insert into tournoi (idT,nomT,DateDebutT,DateFinT,DescriT,statutT) values ('" + t.getIdT() + "' , '" + t.getNomT() + "' , '" + dateDebutStr + "' , '" + dateFinStr + "' , '" + t.getDescriT() + "' , '" + t.getStatutT() + "')";

        try {
            this.ste = this.conn.createStatement();
            this.ste.executeUpdate(requete);
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    public void addPst(Tournoi t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebutStr = sdf.format(t.getDateDebutT());
        String dateFinStr = sdf.format(t.getDateFinT());
        String requete = "insert into tournoi (nomT,DateDebutT,DateFinT,DescriT,statutT) values (?,?,?,?,?)";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, t.getNomT());
            this.pst.setString(2, dateDebutStr);
            this.pst.setString(3, dateFinStr);
            this.pst.setString(4, t.getDescriT());
            this.pst.setString(5, t.getStatutT());
            this.pst.executeUpdate();
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }

    public void delete(Tournoi tournoi) {
        String requete = "DELETE FROM tournoi WHERE idT=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, tournoi.getIdT());
            this.pst.executeUpdate();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public void update(Tournoi tournoi) {
        String requete = "UPDATE tournoi SET nomT=?, DateDebutT=?, DateFinT=?, DescriT=?, statutT=? WHERE idT=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setString(1, tournoi.getNomT());
            this.pst.setDate(2, new Date(tournoi.getDateDebutT().getTime()));
            this.pst.setDate(3, new Date(tournoi.getDateFinT().getTime()));
            this.pst.setString(4, tournoi.getDescriT());
            this.pst.setString(5, tournoi.getStatutT());
            this.pst.setInt(6, tournoi.getIdT());
            this.pst.executeUpdate();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public List<Tournoi> readAll() {
        String requete = "select * from tournoi";
        List<Tournoi> list = new ArrayList();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);

            while(rs.next()) {
                list.add(new Tournoi(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getString(6)));
            }

            return list;
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

    public Tournoi readById(int id) {
        String requete = "select * from tournoi where idT=?";

        try {
            this.pst = this.conn.prepareStatement(requete);
            this.pst.setInt(1, id);
            ResultSet rs = this.pst.executeQuery();
            return rs.next() ? new Tournoi(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getString(6)) : null;
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }
}
