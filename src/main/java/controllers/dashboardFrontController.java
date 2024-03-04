package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class dashboardFrontController {
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
    void activite(ActionEvent event) {

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
    void tournois(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournoiClient.fxml"));
        Parent root = loader.load();
        // Appel de showTournoi()
        TournoiClientController controller = loader.getController();
        controller.showTournoi();

        // Créer une nouvelle scène
        Scene scene = new Scene(root,1300,800);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Tournois");

        // Afficher la nouvelle fenêtre
        stage.show();


    }

}
