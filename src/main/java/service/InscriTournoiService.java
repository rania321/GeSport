package service;

import entities.Equipe;
import entities.InscriTournoi;
import entities.Tournoi;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriTournoiService  implements IService<InscriTournoi> {

    private Connection conn = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;
    private EquipeService Es = new EquipeService();

    public InscriTournoiService() {
    }

    @Override
    public void add(InscriTournoi inscriTournoi) {
        String requete = "INSERT INTO inscritournoi (idT, idE) VALUES (?, ?)";
        try {
            // Récupérer toutes les équipes associées au tournoi
            List<Equipe> equipes = Es.readAllEquipesByTournoiId(inscriTournoi.getT().getIdT());

            pst = conn.prepareStatement(requete);

            // Ajouter chaque équipe dans la table inscritournoi
            for (Equipe equipe : equipes) {
                pst.setInt(1, inscriTournoi.getT().getIdT());
                pst.setInt(2, equipe.getIdE());
                pst.addBatch(); // Ajouter à un batch pour une insertion groupée
            }

            // Exécuter le batch pour insérer toutes les équipes en une seule opération
            pst.executeBatch();

            System.out.println("Inscriptions ajoutées avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(InscriTournoi inscriTournoi) {

    }

    @Override
    public void update(InscriTournoi inscriTournoi) {

    }

    @Override
    public List<InscriTournoi> readAll() {
        String requete = "select * from inscritournoi";
        List<InscriTournoi> list = new ArrayList();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);

            while (rs.next()) {
                int idT = rs.getInt("idT");
                TournoiService tournoiService = new TournoiService();
                Tournoi tournoi = tournoiService.readById(idT);
                int idE = rs.getInt("idE");
                EquipeService equipeService = new EquipeService();
                Equipe equipe = equipeService.readById(idE);
                InscriTournoi inscriTournoi = new InscriTournoi(rs.getInt("idIT"), tournoi, equipe);
                list.add(inscriTournoi);

            }

            return list;
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        }
    }

    @Override
    public InscriTournoi readById(int i) {
        return null;
    }


    public List<InscriTournoi> getInscriptionsByTournoi(int tournoiId) {
        List<InscriTournoi> inscriptionsByTournoi = new ArrayList<>();
        List<InscriTournoi> allInscriptions = readAll();
        for (InscriTournoi inscri : allInscriptions) {
            if (inscri.getT().getIdT() == tournoiId) {
                inscriptionsByTournoi.add(inscri);
            }
        }
        return inscriptionsByTournoi;
    }
}
