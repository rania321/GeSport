package controllers;

import entities.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.TournoiService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AjouterTournoiController {

    private final TournoiService ts = new TournoiService();
    private final Tournoi t = new Tournoi();

    @FXML
    private DatePicker DdebutT;

    @FXML
    private TextField DescriT;

    @FXML
    private DatePicker DfinT;

    @FXML
    private TextField StatutT;

    @FXML
    private TextField nomT;




    @FXML
    void ajouter(ActionEvent event) {
        String nom = nomT.getText();
        // Convertir les valeurs des DatePicker en java.util.Date
        Date dateDebut = java.sql.Date.valueOf(DdebutT.getValue());
        Date dateFin = java.sql.Date.valueOf(DfinT.getValue());
        String Description = DescriT.getText();
        String Statut = StatutT.getText();
        Tournoi t = new Tournoi(nom,dateDebut,dateFin,Description,Statut);
        TournoiService ts = new TournoiService();
        ts.add(t);
       // try {
         //   ts.add(new Tournoi(nomT.getText(), dateDebut, dateFin, DescriT.getText(), StatutT.getText()));
       // }catch ( RuntimeException e)
       /* {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }*/
        // Afficher une alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le tournoi a été ajouté avec succès !");
        alert.showAndWait();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTournoi.fxml"));
        try {
            Parent root = loader.load();
            AfficherTournoiController ac = loader.getController();
            ac.showTournoi();
            nomT.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void gerer(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTournoi.fxml"));
        try {
            Parent root = loader.load();
            AfficherTournoiController ac = loader.getController();
            ac.showTournoi();
            nomT.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }


}
