package org.example;

import controllers.EquipeAdminController;
import controllers.EquipeClientController;
import controllers.TournoiClientController;
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



    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTournoi.fxml"));
        Parent root = loader.load();
        Scene scene =new Scene(root,700,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tournoi");
        primaryStage.show();

    }






   /* @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournoiClient.fxml"));
        Parent root = loader.load();

        // Appel de showTournoi() après le chargement du fichier FXML
        TournoiClientController controller = loader.getController();
        controller.showTournoi();


        Scene scene =new Scene(root,700,400);
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




