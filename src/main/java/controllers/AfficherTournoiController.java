package controllers;

import entities.Tournoi;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.w3c.dom.events.MouseEvent;
import service.TournoiService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    private final TournoiService ts = new TournoiService();
    private final Tournoi tournoi = new Tournoi();

    @FXML
    private TableView<Tournoi> tournoiTable;

    @FXML
    private TableColumn<Tournoi, Integer> idColumn;

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

    public void showTournoi() {
        Tlist = ts.readAll();
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdT()).asObject());
        nomColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nomT"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateDebutT"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateFinT"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("DescriT"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("statutT"));

        if (tournoiTable != null && tournoiTable instanceof TableView<Tournoi>) {
            ((TableView<Tournoi>) tournoiTable).setItems(FXCollections.observableArrayList(Tlist));
        }

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
        // Mettre à jour la liste des tournois depuis le service
        List<Tournoi> tournois = ts.readAll();

        // Mettre à jour les données de la TableView
        tournoiTable.getItems().setAll(tournois);

        // Afficher une alerte de succès
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
            // Appeler la méthode de suppression de la ligne
            supprimerLigne(tournoi);
        } else {
            // Afficher un message d'erreur si aucune ligne n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
            alert.showAndWait();
        }

    }

    private void supprimerLigne(Tournoi tournoi) {

        // Suppression de la ligne sélectionnée
        tournoiTable.getItems().remove(tournoi);
        // Appel à la méthode de suppression dans le service
        ts.delete(tournoi);


    }


    public void selection(javafx.scene.input.MouseEvent mouseEvent) {
        Tournoi selectedTournoi = tournoiTable.getSelectionModel().getSelectedItem();
        if (selectedTournoi != null) {
            // Remplir les champs avec les informations du tournoi sélectionné
            nomT.setText(selectedTournoi.getNomT());
            idT.setText(String.valueOf(selectedTournoi.getIdT())); // Convertir l'entier en chaîne de caractères
            DescriT.setText(selectedTournoi.getDescriT());
            StatutT.setText(selectedTournoi.getStatutT());
            // Convertir les dates de java.sql.Date à LocalDate
            Date dateDebut = selectedTournoi.getDateDebutT();
            Date dateFin = selectedTournoi.getDateFinT();

            // Convertir les dates en Instant, puis en LocalDate
            LocalDate localDateDebut = Instant.ofEpochMilli(dateDebut.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateFin = Instant.ofEpochMilli(dateFin.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            // Mettre à jour les DatePicker avec les valeurs converties en LocalDate
            DdebutT.setValue(localDateDebut);
            DfinT.setValue(localDateFin);
        }

    }


    @FXML
    void modifier(ActionEvent event) {
        // Vérifier si un élément est sélectionné dans la TableView
        Tournoi tournoiSelectionne = tournoiTable.getSelectionModel().getSelectedItem();
        if (tournoiSelectionne != null) {
            // Récupérer les valeurs des champs de texte
            String nom = nomT.getText();
            String description = DescriT.getText();
            String statut = StatutT.getText();

            // Convertir les valeurs des DatePicker en java.util.Date
            Date dateDebut = java.sql.Date.valueOf(DdebutT.getValue());
            Date dateFin = java.sql.Date.valueOf(DfinT.getValue());

            // Mettre à jour les informations du tournoi sélectionné
            tournoiSelectionne.setNomT(nom);
            tournoiSelectionne.setDateDebutT(dateDebut);
            tournoiSelectionne.setDateFinT(dateFin);
            tournoiSelectionne.setDescriT(description);
            tournoiSelectionne.setStatutT(statut);

            // Mettre à jour la TableView avec les modifications
            tournoiTable.refresh();

            // Vous pouvez également appeler votre méthode de mise à jour dans le service ici
            TournoiService ts = new TournoiService();
            ts.update(tournoiSelectionne);

            // Réinitialiser les champs de texte après la modification
            nomT.clear();
            DescriT.clear();
            StatutT.clear();
            DdebutT.setValue(null);
            DfinT.setValue(null);
        } else {
            // Afficher un message d'erreur si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un élément à modifier.");
            alert.showAndWait();
        }
    }
}






