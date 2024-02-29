package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.Service.ProduitService;
import org.example.Service.VenteService;
import java.util.HashMap;
import java.util.Map;

import java.time.LocalTime;


import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class StatBuvetteController {

    @FXML
    private Button ButtonAccueil;

    @FXML
    private Button ButtonAjouterP;

    @FXML
    private Button ButtonListeP;

    @FXML
    private Button ButtonListeV;

    @FXML
    private Button buttonstatistiqueP;

    @FXML
    private Label HeureActuelle;

    @FXML
    private Label NbPPrevRupture;

    @FXML
    private Label bestseller;

    @FXML
    private Label NbPRupture;

    @FXML
    private ImageView RefPPlusRentable;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private Label RevenuMois;

    @FXML
    private PieChart piechart;

    ProduitService ps = new ProduitService();
    VenteService vs = new VenteService();



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
    @FXML
    public void ajP(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
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
    @FXML
    public void showStatP(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatBuvette.fxml"));
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

    @FXML
    public void initialize() {
        // Appelez la méthode Heure dès l'initialisation
        Heure();
        nbProduitRuptureStock();
        nbProduitPrevRupture ();
        revenuMois ();
        bestSeller ();
        iniLineChart();
        iniPieChart();
    }

        public void Heure () {
            // Obtenez l'heure actuelle
            LocalTime heureActuelle = LocalTime.now();
            // Formatez l'heure en heures et minutes
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String heureFormatee = heureActuelle.format(formatter);
            HeureActuelle.setText(""+heureFormatee);
            System.out.println("L'heure actuelle est : " + heureFormatee);
        }

    public void nbProduitRuptureStock () {
        int nb =ps.pRupture();
        NbPRupture.setText(""+nb);
        System.out.println("L'heure actuelle est : " + nb);
    }

    public void nbProduitPrevRupture () {
        int n =ps.pPrevRupture();
        NbPPrevRupture.setText(""+n);
        System.out.println("Nombre de produit prochainement en rupture de stock : " + n);
    }

    public void revenuMois () {
        float r =vs.calculerRevenusDuMois();
        RevenuMois.setText(""+r + " dt ");
        System.out.println("Revenus du mois actuelle = " + r + " dt ");
    }
    public void bestSeller () {
        int id =vs.getMostSoldProductID();
        String nomBestSeller= ps.getNomFromIdProduit(id);
        bestseller.setText(""+nomBestSeller);
        System.out.println("BestSeller : " + nomBestSeller);
    }

    private void iniLineChart()
    {
        Map<String, Integer> m =vs.mapNomQte();
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.println("Clé : " + key + ", Valeur : " + value);

            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(key,value));
            lineChart.getData().addAll(series);
            //lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        }
    }
    private void iniPieChart() {
        Map<String, Integer> m = vs.mapNomQte();
        int somme = vs.sumQte();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            String key = entry.getKey();
            double value = (entry.getValue() / (double) somme) * 100;
            pieChartData.add(new PieChart.Data(key, value));
        }

        piechart.setData(pieChartData);
    }


}
