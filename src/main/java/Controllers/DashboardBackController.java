package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;

import java.io.IOException;
public class DashboardBackController {

        @FXML
        private Button activite;

        @FXML
        private Button buvette;

        @FXML
        private Button compte;

        @FXML
        private Button logout;

        @FXML
        private Button reclamation;

        @FXML
        private Button tournoi;

        @FXML
        void activite(javafx.event.ActionEvent event){}

        @FXML
        void compte(javafx.event.ActionEvent event) {

        }

        @FXML
        void tournoi(javafx.event.ActionEvent event) {

        }

        public void reclamation(javafx.event.ActionEvent actionEvent) {
        }
        @FXML
        public void restaurant(javafx.event.ActionEvent actionEvent) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerProduitBack.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Configurer la nouvelle scène dans une nouvelle fenêtre
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Buvette");

                // Afficher la nouvelle fenêtre
                stage.show();
        }
}