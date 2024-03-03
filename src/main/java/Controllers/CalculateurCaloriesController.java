package Controllers;

import API.EdamameApi;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculateurCaloriesController {
    @FXML
    private Button accueil;

    @FXML
    private Button activite;

    @FXML
    private Button buvette;

    @FXML
    private Button compte;

    @FXML
    private Button menu;

    @FXML
    private Button recherche;

    @FXML
    private Button reclamation;

    @FXML
    private Button tournois;

    private Produit selectedActivite;

    @FXML
    private Label nut;

    @FXML
    private Button accesPageAPI;

    @FXML
    private Label  cal;

    @FXML
    private Button calculerC;

    @FXML
    private TextField nomAliment;

    @FXML
    private TextField qteAliment;


    /*private final ProduitService ps = new ProduitService();
   ist<Produit> produits = ps.readAll();
*/
    /*___________________________________________entete___________________________________________________*/
    @FXML
    void accueil(ActionEvent event) {

    }

    @FXML
    void compte(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void restaurant(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilProduit.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Restaurant");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void tournois(ActionEvent event) {

    }

    public void activité(ActionEvent actionEvent) {
    }

    public void api (){
        int c=0;
        c=EdamameApi.apii(nomAliment.getText(),Double.parseDouble(qteAliment.getText())); //qte en gramme
        cal.setText("Calories : " + c);
    }

}
