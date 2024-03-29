package controllers;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.Service.ProduitService;

import java.io.IOException;
import java.util.Date;

public class AjouterProduitController {

    @FXML
    private TextField desctf;

    @FXML
    private TextField imagetf;

    @FXML
    private TextField nomtf;

    @FXML
    private TextField prixtf;

    @FXML
    private TextField qtf;

    @FXML
    private TextField referencetf;

    @FXML
    private Button boutonajp;

    private final ProduitService ps = new ProduitService();

    Date utilDate = new Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    @FXML
    public void Home(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardBack.fxml"));
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
    public void ajouterp(javafx.event.ActionEvent actionEvent) throws IOException {
        String nomt = nomtf.getText();
        String descri = desctf.getText();
        //float prix = Float.parseFloat(prixtf.getText());
        //int qt = Integer.parseInt(qtf.getText());
        String imaget = imagetf.getText();
       // int ref = Integer.parseInt(referencetf.getText());


        if (!ps.isNumeric(prixtf.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour le prix !");
            alert.showAndWait();
            return;
        }
        if (!ps.isNumericInt(qtf.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour la quantité !");
            alert.showAndWait();
            return;
        }

        if (nomt.isEmpty() || imaget.isEmpty()) {
            // Afficher un message d'erreur si les champs obligatoires ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Information manquante !");
            alert.showAndWait();
            return;
        }

        if (referencetf == null || referencetf.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Il faut saisir une réference!");
            alert.showAndWait();
            return;
        }

        if (ps.referenceExists(referencetf.getText())) {
            // Affichez un message d'erreur si la référence existe déjà
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("La référence existe déjà dans la base de données !");
            alert.showAndWait();
            return;
        }

        ps.add(new Produit(nomt,descri,Float.parseFloat(prixtf.getText()), Integer.parseInt(qtf.getText()), sqlDate, imaget,Integer.parseInt(referencetf.getText()) ));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml") );
        Parent root = loader.load();
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
}

