package Controllers;
import entities.Produit;
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
import javafx.stage.Stage;
import org.example.Service.ProduitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListerProduitBackController {

        @FXML
        private ComboBox<String> CBStatuteP;

        @FXML
        private Button ButtonAccueil;

        @FXML
        private Button ButtonAjouterP;

        @FXML
        private Button ButtonListeP;

        @FXML
        private Button ButtonListeV;

        @FXML
        private Button ButtonModifierP;

        @FXML
        private Button ButtonModifierV;

        @FXML
        private TableView<?> TableProduit;

        @FXML
        private TableColumn<?, ?> DP;

        @FXML
        private TableColumn<?, ?> DateP;

        @FXML
        private TableColumn<?, ?> IP;

        @FXML
        private TableColumn<?, ?> NP;

        @FXML
        private TableColumn<?, ?> PP;

        @FXML
        private TableColumn<?, ?> RP;

        @FXML
        private TableColumn<?, ?> SP;

        @FXML
        private TableColumn<Produit, Void> SuppressionP;

        @FXML
        private TableColumn<Produit, Produit> ModificationP;

        ProduitService ps = new ProduitService();
        List<Produit> PList;

        public void initialize() throws IOException {
                // Initialiser le ComboBox avec des données
                ObservableList<String> options = FXCollections.observableArrayList(
                        "confirmée",
                        "annulée"
                );
                CBStatuteP.setItems(options);
                ShowProduit();
                configureSuppressionColumn();
                configureModificationColumn();
        }

        private void configureModificationColumn() {
                ModificationP.setCellFactory(param -> new TableCell<>() {
                        private final Button boutonModifier = new Button("Modifier");
                        {
                                boutonModifier.setOnAction(event -> {
                                        Produit produit = getTableView().getItems().get(getIndex());
                                        ps.update(produit);
                                        System.out.println("Modifier : " + produit.getNomP());

                                        // Mettre à jour uniquement cette cellule
                                        updateItem(produit, false);
                                        // Appeler le rafraîchissement des graphiques pour que la cellule soit correctement mise à jour visuellement
                                        getTableView().getColumns().get(getTableView().getColumns().indexOf(getTableColumn())).setVisible(false);
                                        getTableView().getColumns().get(getTableView().getColumns().indexOf(getTableColumn())).setVisible(true);
                                });
                        }
                        @Override
                        protected void updateItem(Produit produit, boolean empty) {
                                super.updateItem(produit, empty);

                                if (empty || produit == null) {
                                        setText(null);
                                        setGraphic(null);
                                } else {
                                        setText("Nouvelle valeur : " + produit.getNomP());
                                        setGraphic(boutonModifier);
                                }
                        }
                });
        }
        /*@FXML
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
        }*/

        private void configureSuppressionColumn() {
                SuppressionP.setCellFactory(param -> new TableCell<>() {
                        private final Button boutonSupprimer = new Button("Supprimer");

                        {
                                boutonSupprimer.setOnAction(event -> {
                                        Produit produit = getTableView().getItems().get(getIndex());
                                        // Ici, vous pouvez appeler votre méthode de suppression
                                        // en utilisant les données du produit.
                                        ps.delete(produit);
                                        System.out.println("Supprimer : " + produit.getNomP());
                                        TableProduit.refresh();

                                });
                        }
                        @Override
                        protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                setGraphic(empty ? null : boutonSupprimer);
                        }
                });
        }

        public void ShowProduit() throws IOException {

                PList = ps.readAll();
                List<Produit> filtredPList= new ArrayList<>();

                RP.setCellValueFactory(new PropertyValueFactory<>("referenceP"));
                NP.setCellValueFactory(new PropertyValueFactory<>("nomP"));
                PP.setCellValueFactory(new PropertyValueFactory<>("PrixP"));
                DateP.setCellValueFactory(new PropertyValueFactory<>("DateAjoutP"));
                SP.setCellValueFactory(new PropertyValueFactory<>("StockP"));
                DP.setCellValueFactory(new PropertyValueFactory<>("descriP"));
                IP.setCellValueFactory(new PropertyValueFactory<>("imageP"));

                if (TableProduit != null && TableProduit instanceof TableView) {
                        // Cast ticket_tableview to TableView<Ticket> and set its items
                        ((TableView<Produit>) TableProduit).setItems(FXCollections.observableArrayList(PList));
                }
        }
        @FXML
        public void listP(ActionEvent actionEvent)  throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerProduitBack.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Configurer la nouvelle scène dans une nouvelle fenêtre
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Produit");

                // Afficher la nouvelle fenêtre
                stage.show();
        }

        @FXML
        public void listV(ActionEvent actionEvent) throws IOException{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerVenteBack.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Configurer la nouvelle scène dans une nouvelle fenêtre
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Vente");

                // Afficher la nouvelle fenêtre
                stage.show();
        }
        @FXML
        public void Home(ActionEvent actionEvent) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardBack.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Configurer la nouvelle scène dans une nouvelle fenêtre
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Dashboard");

                // Afficher la nouvelle fenêtre
                stage.show();
        }
        @FXML
        public void ajP(ActionEvent actionEvent) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Configurer la nouvelle scène dans une nouvelle fenêtre
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Dashboard");

                // Afficher la nouvelle fenêtre
                stage.show();
        }
}
