package controllers;

import entities.Equipe;
import entities.InscriTournoi;
import entities.Joueur;
import entities.Tournoi;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.EquipeService;
import service.InscriTournoiService;
import service.JoueurService;
import service.TournoiService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

public class AfficherTournoiController {
    @FXML
    private DatePicker DdebutT;

    @FXML
    private TextField DescriT;

    @FXML
    private DatePicker DfinT;

    @FXML
    private TextField StatutT;
    @FXML
    private TextField nomT;
    @FXML
    private TextField idT;
    private List<Tournoi> Tlist;

    private List<InscriTournoi>Ilist;

    private final TournoiService ts = new TournoiService();
    private final Tournoi tournoi = new Tournoi();
    private final EquipeService equipeService = new EquipeService();
    private final Equipe equipe = new Equipe();
    private final JoueurService joueurService = new JoueurService();
    private final Joueur joueur = new Joueur();
    private final InscriTournoi it =new InscriTournoi();
    private final InscriTournoiService its =new InscriTournoiService();


    @FXML
    private TableView<Tournoi> tournoiTable;


    @FXML
    private TableColumn<Tournoi, String> nomColumn;

    @FXML
    private TableColumn<Tournoi, Date> dateDebutColumn;

    @FXML
    private TableColumn<Tournoi, Date> dateFinColumn;

    @FXML
    private TableColumn<Tournoi, String> descriptionColumn;

    @FXML
    private TableColumn<Tournoi, String> statutColumn;



    @FXML
    private TableView<InscriTournoi> inscriTable;
    @FXML
    private TableColumn<InscriTournoi,String> equipeColumn;




    public void showTournoi() throws IOException {
        Tlist = ts.readAll();
        nomColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nomT"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateDebutT"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateFinT"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("DescriT"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("statutT"));

        if (tournoiTable != null && tournoiTable instanceof TableView<Tournoi>) {
            ((TableView<Tournoi>) tournoiTable).setItems(FXCollections.observableArrayList(Tlist));
        }

        // Ajouter un écouteur de sélection à la table tournoiTable
        tournoiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Afficher les inscriptions du tournoi sélectionné
                showInscriptions(newSelection.getIdT());
            }
        });
    }

    private void showInscriptions(int tournoiId) {
        // Récupération des inscriptions pour le tournoi spécifié depuis le service
        Ilist = its.getInscriptionsByTournoi(tournoiId);

        // Configuration des colonnes de la table inscriTable
        equipeColumn.setCellValueFactory(cellData -> {
            InscriTournoi is = cellData.getValue();
            String nomEquipe = is.getE().getNomE();
            return new SimpleStringProperty(nomEquipe);
        });


        // Ajout des données à la table inscriTable
        inscriTable.setItems(FXCollections.observableArrayList(Ilist));
    }



    @FXML
    void ajouter(ActionEvent event) {
        String nom = nomT.getText();
        // Convertir les valeurs des DatePicker en java.util.Date
        Date dateDebut = java.sql.Date.valueOf(DdebutT.getValue());
        Date dateFin = java.sql.Date.valueOf(DfinT.getValue());
        String Description = DescriT.getText();
        String Statut = StatutT.getText();
        Tournoi t = new Tournoi(nom,dateDebut,dateFin,Description,Statut);
        TournoiService ts = new TournoiService();
        ts.add(t);
        // maj  liste des tournois depuis  service
        List<Tournoi> tournois = ts.readAll();

        // maj   des données de la TableView
        tournoiTable.getItems().setAll(tournois);

        //  alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le tournoi a été ajouté avec succès !");
        alert.showAndWait();
    }


    @FXML
    void supprimer(ActionEvent event) {
        // Récupérer la ligne sélectionnée dans la TableView
        Tournoi tournoi = tournoiTable.getSelectionModel().getSelectedItem();

        if (tournoi != null) {

            supprimerLigne(tournoi);
        } else {
            //  message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
            alert.showAndWait();
        }

    }

    private void supprimerLigne(Tournoi tournoi) {

        // Récupérer toutes les équipes associées à ce tournoi
        List<Equipe> equipes = equipeService.readAllEquipesByTournoiId(tournoi.getIdT());
        joueurService.deleteJoueursByEquipeId(equipe.getIdE());

        // Supprimer toutes les équipes associées à ce tournoi
        for (Equipe equipe : equipes) {
            equipeService.delete(equipe);
        }

        // Supprimer la ligne de tournoi
        tournoiTable.getItems().remove(tournoi);
        ts.delete(tournoi);
        // Supprimer la ligne d'équipe
        equipeService.delete(equipe);


    }


    public void selection(MouseEvent mouseEvent) {
        Tournoi selectedTournoi = tournoiTable.getSelectionModel().getSelectedItem();
        if (selectedTournoi != null) {
            // Remplir les champs
            nomT.setText(selectedTournoi.getNomT());
            DescriT.setText(selectedTournoi.getDescriT());
            StatutT.setText(selectedTournoi.getStatutT());
            // Convertir les dates
            Date dateDebut = selectedTournoi.getDateDebutT();
            Date dateFin = selectedTournoi.getDateFinT();

            //  dates-->Instant-->LocalDate
            LocalDate localDateDebut = Instant.ofEpochMilli(dateDebut.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateFin = Instant.ofEpochMilli(dateFin.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            // maj DatePicker
            DdebutT.setValue(localDateDebut);
            DfinT.setValue(localDateFin);
        }

    }


    @FXML
    void modifier(ActionEvent event) {
        // Vérifier si un élément est sélectionné dans la TableView
        Tournoi tournoiSelectionne = tournoiTable.getSelectionModel().getSelectedItem();
        if (tournoiSelectionne != null) {
            // Récupérer les valeurs
            String nom = nomT.getText();
            String description = DescriT.getText();
            String statut = StatutT.getText();

            // Convertir les valeurs des DatePicker
            Date dateDebut = java.sql.Date.valueOf(DdebutT.getValue());
            Date dateFin = java.sql.Date.valueOf(DfinT.getValue());

            // maj
            tournoiSelectionne.setNomT(nom);
            tournoiSelectionne.setDateDebutT(dateDebut);
            tournoiSelectionne.setDateFinT(dateFin);
            tournoiSelectionne.setDescriT(description);
            tournoiSelectionne.setStatutT(statut);

            // maj TableView
            tournoiTable.refresh();

            //appel update
            TournoiService ts = new TournoiService();
            ts.update(tournoiSelectionne);

            idT.clear();
            nomT.clear();
            DescriT.clear();
            StatutT.clear();
            DdebutT.setValue(null);
            DfinT.setValue(null);
        } else {
            //message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un élément à modifier.");
            alert.showAndWait();
        }
    }


    public void backMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTournoi.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1300, 800);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    public void toEquipe(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipeAdmin.fxml"));
        Parent root = loader.load();

        EquipeAdminController controller = loader.getController();
        controller.showEquipe();

        // Créer une nouvelle scène avec la vue chargée
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Equipes");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
}






