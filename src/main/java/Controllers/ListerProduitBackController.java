package controllers;
import entities.Produit;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.Service.ProduitService;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
public class ListerProduitBackController {



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
        private TableColumn<Produit, Void> ModificationP;

        ProduitService ps = new ProduitService();
        List<Produit> PList;

        public void initialize() throws IOException {
                // Initialiser le ComboBox avec des données
                ObservableList<String> options = FXCollections.observableArrayList(
                        "confirmée",
                        "annulée"
                );
                ShowProduit();
                configureSuppressionColumn();
                configureModificationColumn();
                showNotification();
        }

        private void configureModificationColumn() {
                ModificationP.setCellFactory(param -> new TableCell<>() {
                        private final Button boutonModifier = new Button("Modifier");
                        {
                                /*boutonModifier.setOnAction(event -> {
                                        Produit produit = getTableView().getItems().get(getIndex());
                                        System.out.println("Modifier : " + produit.getNomP());
                                        TableProduit.refresh();

                                        });*/
                                        boutonModifier.setOnAction(event -> {
                                        Produit produit = getTableView().getItems().get(getIndex());
                                        System.out.println("Modifier : " + produit.getNomP());

                                        // Créer une nouvelle fenêtre (Stage)
                                        Stage nouvelleFenetre = new Stage();

                                        // Créer un FXMLLoader pour charger la nouvelle scène depuis le fichier FXML
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProduit.fxml"));

                                        try {
                                                Parent root = loader.load();
                                                // Obtenir le contrôleur de la nouvelle scène
                                                ModifierProduitController modifierProduitController = loader.getController();

                                                // Transmettre l'id de la vente au contrôleur de la nouvelle scène
                                                modifierProduitController.setProduitId(produit.getIdP());

                                                Scene nouvelleScene = new Scene(root);
                                                nouvelleFenetre.setScene(nouvelleScene);
                                                nouvelleFenetre.show();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                        // Rafraîchir la TableView si nécessaire
                                        TableProduit.refresh();
                                });
                        }
                        @Override
                        protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                setGraphic(empty ? null : boutonModifier);
                        }
                });
        }

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
                                        try {
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerproduitBack.fxml"));
                                                Parent root = loader.load();
                                                // Obtenir le contrôleur de la nouvelle interface
                                                ListerProduitBackController listerproduitBackController = loader.getController();

                                                // Créer une nouvelle scène
                                                Scene scene = new Scene(root);

                                                // Configurer la nouvelle scène dans une nouvelle fenêtre
                                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                                stage.setScene(scene);

                                                // Afficher la nouvelle fenêtre
                                                stage.show();

                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
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
                List<Produit> filtredPList = new ArrayList<>();

                RP.setCellValueFactory(new PropertyValueFactory<>("referenceP"));
                NP.setCellValueFactory(new PropertyValueFactory<>("nomP"));
                PP.setCellValueFactory(new PropertyValueFactory<>("PrixP"));
                DateP.setCellValueFactory(new PropertyValueFactory<>("DateAjoutP"));
                SP.setCellValueFactory(new PropertyValueFactory<>("StockP"));
                DP.setCellValueFactory(new PropertyValueFactory<>("descriP"));
                //IP.setCellValueFactory(new PropertyValueFactory<>("imageP"));
                if (TableProduit != null && TableProduit instanceof TableView) {
                        // Cast ticket_tableview to TableView<Ticket> and set its items
                        ((TableView<Produit>) TableProduit).setItems(FXCollections.observableArrayList(PList));
                }
        }

        private String getFileNameFromPath(String filePath) {
                // Split the path based on the '/' character
                String[] pathParts = filePath.split("/");

                // Get the last part of the path (file name)
                return pathParts[pathParts.length - 1];
        }

        private void showNotification() {
                try {
                        Image image = new Image(getClass().getResource("/notif.png").toExternalForm());

                        Notifications notifications = Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.title("Success Message");
                        notifications.hideAfter(Duration.seconds(4));
                        notifications.show();
                } catch (Exception e) {
                        e.printStackTrace();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardBack.fxml"));
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

        @FXML
        public void showStatP(ActionEvent actionEvent) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatBuvette.fxml"));
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
