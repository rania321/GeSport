package controller;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import entities.*;
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
import javafx.stage.Stage;
import service.ReservationService;
import service.UserService;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class show_reservationBackController {

    @FXML
    private ComboBox<String> CBStatutR;

    @FXML
    private TableColumn<?, ?> CVActiviteR;

    @FXML
    private TableColumn<?, ?> CVClientR;

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
    private Button annulerR;

    @FXML
    private Button updateR;

    @FXML
    private ComboBox<String> trie;

    ReservationService rs = new ReservationService();

    List<Reservation> resList;
    private List<Reservation> resInitiales;

    public void initialize() throws IOException {
        // Initialiser le ComboBox avec des données
        ObservableList<String> options = FXCollections.observableArrayList(
                "confirmée"
        );
        CBStatutR.setItems(options);

        ShowReservation();

        // Ajoutez une option vide au début du ComboBox
        trie.getItems().add("");

        // Ajoutez les options de tri au ComboBox
        trie.getItems().addAll("Nom Activité ascendant", "Nom Activité descendant", "Date ascendante", "Date descendante", "Client ascendant", "Client descendant");

        // Faites une copie de la liste initiale des œuvres
        resInitiales = FXCollections.observableArrayList(TableViewR.getItems());

        // Ajoutez un écouteur pour détecter quand l'utilisateur change l'option de tri
        trie.getSelectionModel().selectedItemProperty().addListener((options1, oldValue, newValue) -> {
            trierOeuvres(newValue);
        });
    }
    public void ShowReservation() throws IOException {

        resList = rs.readAll();

        List<Reservation> filtredResList= new ArrayList<>();

        CVClientR.setCellValueFactory(new PropertyValueFactory<>("userNom"));
        CVActiviteR.setCellValueFactory(new PropertyValueFactory<>("activiteNom"));
        CVDateR.setCellValueFactory(new PropertyValueFactory<>("DateDebutR"));
        CVHeureR.setCellValueFactory(new PropertyValueFactory<>("HeureR"));
        CVStatutR.setCellValueFactory(new PropertyValueFactory<>("statutR"));

        if (TableViewR != null && TableViewR instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Reservation>) TableViewR).setItems(FXCollections.observableArrayList(resList));
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

            String statut = CBStatutR.getSelectionModel().getSelectedItem();

            // Mettre à jour la séance dans la base de données
            Reservation selectedReservation = TableViewR.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                selectedReservation.setStatutR(statut);
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
                    User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
                    UserService us = new UserService();
                    User u = us.readById(2);
                    // Générer le PDF
                    byte[] pdfData = PDFGenerator.generatePDF(selectedReservation, u);

                    // Envoyer l'e-mail avec le PDF en pièce jointe et les détails de la réservation
                    SendEmail.send(u.getEmailU(), pdfData, selectedReservation, u);
                    sendSMS.sendSMS(selectedReservation);
                    // Rafraîchir l'affichage des séances dans la TableView
                    ShowReservation();
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

    private void trierOeuvres(String option) {
        List<Reservation> reservations;

        if (option.equals("")) {
            // Si l'option est vide, réinitialisez la TableView à son état initial
            reservations = resInitiales;
        } else {
            // Sinon, faites une copie de la liste initiale et triez-la
            reservations = resInitiales.stream().collect(Collectors.toList());
            switch (option) {
                case "Nom Activité ascendant":
                    rs.triParNomA(reservations, true);
                    break;
                case "Nom Activité descendant":
                    rs.triParNomA(reservations, false);
                    break;
                case "Date ascendante":
                    rs.triParDateR(reservations, true);
                    break;
                case "Date descendante":
                    rs.triParDateR(reservations, false);
                    break;
                case "Client ascendant":
                    rs.triParNomU(reservations, true);
                    break;
                case "Client descendant":
                    rs.triParNomU(reservations, false);
                    break;
                default:
                    // Option non reconnue, vous pouvez gérer cette situation comme vous le souhaitez
                    break;
            }
        }

        TableViewR.setItems(FXCollections.observableArrayList(reservations));
    }

    @FXML
    void toActivite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_activite.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
    @FXML
    void backMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void toStatistique(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistiquesAR.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

}


