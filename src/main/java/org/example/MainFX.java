package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }
   /* @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatEquipe.fxml"));
        Parent root = loader.load();

        // Obtenir une référence au contrôleur
        StatEquipeController controller = loader.getController();

        // Charger les données des équipes
        EquipeService equipeService = new EquipeService();
        List<Equipe> equipes = equipeService.readAll();

        // Passer les données au contrôleur
        controller.setData(equipes);

        // Configurer la scène
        Scene scene = new Scene(root, 800, 600);

        // Configurer le stage principal
        primaryStage.setTitle("Statistiques des équipes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/





    /*   @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/CalendarFront.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }*/

@Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTournoi.fxml"));
        Parent root = loader.load();
        Scene scene =new Scene(root,1300,800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("dashboard");
        primaryStage.show();
    }

  /*  @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dachboardFront.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // Charger le fichier CSS
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show();
    }*/













  /* @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournoiClient.fxml"));
        Parent root = loader.load();

        // Appel de showTournoi() après le chargement du fichier FXML
        TournoiClientController controller = loader.getController();
        controller.showTournoi();


        Scene scene =new Scene(root,1300,800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tournoi");
        primaryStage.show();

    }*/

  /*  @Override
      public void start(Stage primaryStage) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipeAdmin.fxml"));
          Parent root = loader.load();

          // Appel de showEquipe() après le chargement du fichier FXML
          EquipeAdminController controller = loader.getController();
          controller.showEquipe();


          Scene scene =new Scene(root,750,400);
          primaryStage.setScene(scene);
          primaryStage.setTitle("Equipe");
          primaryStage.show();

      }*/


    /* @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipeClient.fxml"));
        Parent root = loader.load();

        // Appel de showEquipe() après le chargement du fichier FXML
        EquipeClientController controller = loader.getController();
        controller.showEquipe();


        Scene scene = new Scene(root, 750, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Equipe");
        primaryStage.show();

    }*/
}



