package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.Service.ProduitService;
import org.example.Service.VenteService;

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
    private Label RevenuMois;

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
    public void initialize() {
        // Appelez la méthode Heure dès l'initialisation
        Heure();
        nbProduitRuptureStock();
        nbProduitPrevRupture ();
        revenuMois ();
        bestSeller ();
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
        RevenuMois.setText(""+r);
        System.out.println("Revenus du mois actuelle = " + r);
    }
    public void bestSeller () {
        int id =vs.getMostSoldProductID();
        String nomBestSeller= ps.getNomFromIdProduit(id);
        bestseller.setText(""+nomBestSeller);
        System.out.println("BestSeller : " + nomBestSeller);
    }

    }
