package Controllers;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.example.Service.ProduitService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccueilProduitController {
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

    /*private final ProduitService ps = new ProduitService();
   ist<Produit> produits = ps.readAll();
*/
/*___________________________________________entete___________________________________________________*/
    @FXML
    void accueil(ActionEvent event) throws IOException {
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
    void compte(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void restaurant(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerProduit.fxml"));
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

    public void recherchep(ActionEvent actionEvent) {
    }

    public void activité(ActionEvent actionEvent) {
    }


/*___________________________________________entete___________________________________________________*/

}
