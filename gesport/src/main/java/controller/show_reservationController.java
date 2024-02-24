package controller;

import entities.Reservation;
import entities.User;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ReservationService;
import service.UserService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class show_reservationController {

    @FXML
    private TableColumn<?, ?> CVActiviteR;

    @FXML
    private TableColumn<?, ?> CVDateR;

    @FXML
    private TableColumn<?, ?> CVHeureR;

    @FXML
    private TableColumn<?, ?> CVStatutR;

    @FXML
    private ImageView IVBtnDelete;

    @FXML
    private ImageView IVBtnUpdate;

    @FXML
    private TableView<Reservation> TableViewR;

    @FXML
    private TextField activiteR;

    @FXML
    private Button annulerR;

    @FXML
    private DatePicker dateR;

    @FXML
    private ComboBox<String> heureR;

    @FXML
    private Button updateR;

    @FXML
    private Label activiteR1;

    @FXML
    private Label choisirHeure;

    @FXML
    private ImageView imageviewR;

    @FXML
    private ImageView imageviewUpdate;

    @FXML
    private AnchorPane anchorpanemodifier;


    ReservationService rs = new ReservationService();
    Reservation reservation = new Reservation();

    public void initialize() throws IOException {
        // Initialiser le ComboBox avec des données
        ObservableList<String> options = FXCollections.observableArrayList(
                "08:00",
                "09:30","11:00","12:30","14:00","15:30","17:00"
        );
        heureR.setItems(options);
        anchorpanemodifier.setVisible(false);
        imageviewUpdate.setVisible(false);

        TableViewR.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifie si c'est un simple clic
                Reservation selectedReservation = (Reservation) TableViewR.getSelectionModel().getSelectedItem();
                if (selectedReservation != null) {
                    anchorpanemodifier.setVisible(true);
                    imageviewR.setVisible(false);
                    imageviewUpdate.setVisible(true);
                    // Afficher les informations de la séance sélectionnée dans le formulaire
                    displayReservationInfo(selectedReservation);

                }
            }
        });
        ShowReservation();
    }

    @FXML
    void checkDate() {
       if (dateR.getValue() != null) {
            // Charger les horaires disponibles pour la date sélectionnée
           Reservation selectedReservation = (Reservation) TableViewR.getSelectionModel().getSelectedItem();
           List<String> horairesDisponibles = rs.getAvailableHoursForActivityAndDate(selectedReservation.getActivite(), dateR.getValue());

           // Mettre à jour le ComboBox avec les horaires disponibles
           heureR.setItems(FXCollections.observableArrayList(horairesDisponibles));

           // Rendre le ComboBox visible
           heureR.setVisible(true);
           choisirHeure.setVisible(true);
        }
    }

    List<Reservation> resList;

    public void ShowReservation() throws IOException {

        resList = rs.readAll();

        List<Reservation> filtredResList= new ArrayList<>();
        User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        UserService us = new UserService();
        User u = us.readById(2);

        for (Reservation r:resList) {
            if (r.getUser().equals(u)) {
                filtredResList.add(r);
            }
        }

        CVActiviteR.setCellValueFactory(new PropertyValueFactory<>("activiteNom"));
        CVDateR.setCellValueFactory(new PropertyValueFactory<>("DateDebutR"));
        CVHeureR.setCellValueFactory(new PropertyValueFactory<>("HeureR"));
        CVStatutR.setCellValueFactory(new PropertyValueFactory<>("statutR"));

        if (TableViewR != null && TableViewR instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Reservation>) TableViewR).setItems(FXCollections.observableArrayList(filtredResList));
        }

    }
    @FXML
    void delete_reservation(ActionEvent event) throws IOException {
// Récupérer l'activité sélectionnée dans la TableView
        Reservation selectedReservation = TableViewR.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (selectedReservation != null) {

            // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer la réservation");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer la réservation sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();

            // Si l'utilisateur confirme la suppression, procéder
            if (result.isPresent() && result.get() == ButtonType.OK) {

                // Supprimer l'activité de la base de données
                rs.delete(selectedReservation);

                // Rafraîchir l'affichage des activités dans la TableView
                ShowReservation();
                anchorpanemodifier.setVisible(false);
                imageviewUpdate.setVisible(false);
                imageviewR.setVisible(true);
            }
        } else {
            // Afficher un message si aucune activité n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune réservation sélectionnée");
            alert.setHeaderText("Aucune réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à supprimer.");
            alert.showAndWait();
        }
    }



    @FXML
    void update_reservation(ActionEvent event) throws IOException {
// Récupérer les informations modifiées depuis le formulaire

        Date date = java.sql.Date.valueOf(dateR.getValue());
        String heure = heureR.getSelectionModel().getSelectedItem();

        // Mettre à jour la séance dans la base de données
        Reservation selectedReservation = TableViewR.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            LocalDate selectedDate = dateR.getValue();
            LocalDate currentDate = LocalDate.now();

            if (selectedDate == null || selectedDate.isBefore(currentDate)) {
                // Afficher un message d'erreur si la date n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de date");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une date ultérieure à la date actuelle.");
                alert.showAndWait();
                return; // Ne pas poursuivre si la date n'est pas valide
            }
            selectedReservation.setDateDebutR(date);
            selectedReservation.setHeureR(heure);
            selectedReservation.setStatutR("En cours");
            // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de modification");
            alert.setHeaderText("Modifier la réservation");
            alert.setContentText("Êtes-vous sûr de vouloir modifier la réservation sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();
            // Si l'utilisateur confirme la suppression, procéder
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Mettre à jour la séance dans la base de données
                rs.update(selectedReservation);
                // Rafraîchir l'affichage des séances dans la TableView
                ShowReservation();
                anchorpanemodifier.setVisible(false);
                imageviewUpdate.setVisible(false);
                imageviewR.setVisible(true);
            }
        }
        else {
            // Afficher un message si aucune activité n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Réservation sélectionnée");
            alert.setHeaderText("Aucune Réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à modifier.");
            alert.showAndWait();
        }
    }
    private void displayReservationInfo(Reservation r) {
        //dateR.setValue(r.getDateDebutR());
        // Convert java.sql.Date to LocalDate
        java.sql.Date sqlDate= (Date) r.getDateDebutR();
        java.time.LocalDate localDate = sqlDate.toLocalDate();


        activiteR1.setText(r.getActivite().getNomA());
        // Afficher les informations de réservation dans les champs de formulaire
        //activiteR.setText(r.getActiviteNom());
      /*  dateR.setValue(localDate);
        heureR.setValue(r.getHeureR());*/

        //nomid.setValue(a.getNom());
    }
    @FXML
    void accueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Accueil");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void activite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show_activite.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Activités");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void compte(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void restaurant(ActionEvent event) {

    }

    @FXML
    void tournois(ActionEvent event) {

    }

}

