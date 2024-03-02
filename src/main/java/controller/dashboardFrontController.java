package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class dashboardFrontController {

    @FXML
    void isometrie(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/isometrieActivite.fxml"));
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
    void weather(ActionEvent event) throws IOException {
// Charger le fichier FXML de l'interface météo
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Weather.fxml"));
        Parent weatherRoot = loader.load();

        // Créer une nouvelle scène avec l'interface météo
        Scene weatherScene = new Scene(weatherRoot);

        // Créer une nouvelle fenêtre pour l'interface météo
        Stage weatherStage = new Stage();
        weatherStage.setScene(weatherScene);
        weatherStage.setTitle("Votre Titre pour l'Interface Météo");

        // Afficher la nouvelle fenêtre
        weatherStage.show();
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