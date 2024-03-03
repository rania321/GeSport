package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controllers.AjouterProduitController;
import Controllers.DashboardBackController;
import entities.Produit;
import java.io.IOException;

public class MainFX extends Application {

    public static final String CURRENCY ="$";
    public static void main  (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilProduit.fxml") );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter produit");
        primaryStage.show();
    }
}