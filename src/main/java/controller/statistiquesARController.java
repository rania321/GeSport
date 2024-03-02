package controller;

import entities.Activite;
import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.ActiviteService;
import service.ReservationService;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class statistiquesARController {

    @FXML
    private PieChart piechart1;
    @FXML
    private PieChart piechart;
    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;



    // Injecter le service de réservation
    private ReservationService reservationService;
    private ActiviteService activiteService;

    // Méthode d'initialisation appelée après chargement du fichier FXML
    @FXML
    public void initialize() {
        // Initialiser le service de réservation
        reservationService = new ReservationService();
        activiteService = new ActiviteService();

        // Mettre à jour le PieChart avec les statistiques actuelles
        updatePieChart();

        // Créer une carte pour stocker le nombre de réservations par statut
        Map<String, Integer> statutCount = new HashMap<>();
        List<Reservation> reservations = reservationService.readAll();

        // Compter le nombre de réservations pour chaque statut
        for (Reservation reservation : reservations) {
            String statut = reservation.getStatutR();
            statutCount.put(statut, statutCount.getOrDefault(statut, 0) + 1);
        }

        // Créer une liste observable pour le PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Ajouter les données au PieChart
        //statutCount.forEach((statut, count) -> pieChartData.add(new PieChart.Data(statut, count)));
        statutCount.forEach((statut, count) -> {
            PieChart.Data dataPoint = new PieChart.Data(statut + " (" + count + ")", count);
            pieChartData.add(dataPoint);
        });

        // Afficher les données dans le PieChart
        piechart1.setData(pieChartData);

        label1.setText(reservationService.getClientAvecPlusReservations());
        label2.setText(String.valueOf(activiteService.getNombreTotalActivites()));
    }

    // Méthode pour mettre à jour le PieChart avec les statistiques
    private void updatePieChart() {
        // Récupérer les statistiques depuis le service de réservation
        Map<String, Integer> statistics = reservationService.getReservationStatisticsByActivity();

        // Effacer les sections existantes du PieChart
        piechart.getData().clear();

        // Ajouter les nouvelles sections basées sur les statistiques
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey() + " (" + entry.getValue() + ")", entry.getValue());
            piechart.getData().add(slice);
        }
    }

    @FXML
    void toActivite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_activite.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void toReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show_reservationBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void backMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

}
