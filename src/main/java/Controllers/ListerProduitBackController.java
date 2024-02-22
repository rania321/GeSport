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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        private TableColumn<?, ?> SuppressionP;

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
                IP.setCellValueFactory(new PropertyValueFactory<>("image"));

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
}
