package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterTournoiController {

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
    void tournoi(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTournoi.fxml"));
        Parent root = loader.load();
        // Appel de showTournoi()
        AfficherTournoiController controller = loader.getController();
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
