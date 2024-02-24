package Controllers;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.example.Service.ProduitService;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListerProduitController {

    @FXML
    private Button accueil;

    @FXML
    private Button activité;

    @FXML
    private Button ajoutpanier;

    @FXML
    private Button buvette;

    @FXML
    private Button compte;

    @FXML
    private GridPane grid;

    @FXML
    private AnchorPane menu;

    @FXML
    private Button moinsqte;

    @FXML
    private Label nompr;

    @FXML
    private Button plusqte;

    @FXML
    private Label prixpr;

    @FXML
    private Label qtelb;

    @FXML
    private Label qteproduit;

    @FXML
    private Button recherchep;

    @FXML
    private Button reclamation;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ImageView showpanier;

    @FXML
    private Label totalclabel;

    @FXML
    private Button tournois;

    @FXML
    private Label prixpAf;

    @FXML
    private Label nompAf;

    @FXML
    private Label descrpAf1;

    @FXML
    private Label refpAf;

    @FXML
    private AnchorPane menu_formA;
    private File selectedFile;

    private Produit selectedProduit;

    private ProduitService ps = new ProduitService();
    List<Produit> produits = ps.readAll();
    //Produit produit = new Produit();

    private int va =0;

    public void initialize(){

        setProduitGridPaneList();
    }
    private void setProduitGridPaneList( ) {
        VBox mainVBox = new VBox(20);  // Espace vertical entre chaque ligne
        mainVBox.setPadding(new Insets(10));  // Marge autour du VBox principal

        HBox currentHBox = new HBox(20);  // Espace horizontal entre chaque activité dans une ligne
        for (Produit produit : produits) {
            VBox vbox = createProduitVBox(produit);

            currentHBox.getChildren().add(vbox);

            // Créer une nouvelle ligne après chaque 3 activités
            if (currentHBox.getChildren().size() == 3) {
                mainVBox.getChildren().add(currentHBox);
                currentHBox = new HBox(20);
            }
        }

        // Ajouter la dernière ligne si elle n'est pas complète
        if (!currentHBox.getChildren().isEmpty()) {
            mainVBox.getChildren().add(currentHBox);
        }

        // Ajouter le VBox principal à votre conteneur parent (ScrollPane)
        scroll.setContent(mainVBox);
    }

    private VBox createProduitVBox(Produit produit) {
        VBox vbox = new VBox();

        Label nomLabel = new Label(produit.getNomP());
        Label prixLabel = new Label(Float.toString(produit.getPrixP()));
        ImageView imageView = new ImageView();

        try {
            Image image = new Image(new File(produit.getImageP()).toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(300);  // Définir la largeur selon vos besoins
            imageView.setFitHeight(200); // Définir la hauteur selon vos besoins
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ajouter votre logique pour l'image ici

        vbox.getChildren().addAll(imageView,nomLabel, prixLabel);

        // Ajouter un bouton de réservation
        if (produit.getStockP()!= 0) {
            Button affichageButton = new Button("Voir");
            affichageButton.setOnAction(event -> {
                selectedProduit = produit;
                System.out.println("Produit sélectionnée : " + selectedProduit);

                nompAf.setText("" + selectedProduit.getNomP());
                prixpAf.setText("" + selectedProduit.getPrixP());
                descrpAf1.setText("" + selectedProduit.getDescriP());
                refpAf.setText("" + selectedProduit.getReferenceP());

                /*// Mettez ici la logique pour afficher l'interface de prise de rendez-vous
                System.out.println("Bouton affichage cliqué pour : " + produit.getNomP());
                // Ajoutez ici le code pour afficher l'interface de réservation

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
                    Parent root = loader.load();
                   // Obtenir le contrôleur de la nouvelle interface
                    add_reservationController addReservationController = loader.getController();

                    // Passer des données à votre contrôleur de réservation si nécessaire
                    System.out.println("Passage de l'activité au contrôleur add_reservationController : " + activite.getNomA());
                    addReservationController.initializeWithActivite(produit);

                    addReservationController.setActivite(produit);

                    // Créer une nouvelle scène
                    Scene scene = new Scene(root);

                    // Configurer la nouvelle scène dans une nouvelle fenêtre
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Réservation pour : " + produit.getNomP());

                    // Afficher la nouvelle fenêtre
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            });
            affichageButton.getStyleClass().add("round-buttonMenu1");

            vbox.getChildren().add(affichageButton);
        }
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }


    @FXML
    void accueil(ActionEvent event) {

    }

    @FXML
    void activité(ActionEvent event) {

    }

    @FXML
    void compte(ActionEvent event) {

    }
    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void restaurant(ActionEvent event) {

    }

    @FXML
    void tournois(ActionEvent event) {

    }

    @FXML
    void ajoutpanier(ActionEvent event) {

    }
/*---------------------controller quantité----------------------------------------------------------------*/
    @FXML
    void getqteproduit(ActionEvent event) {

        plusqte.setOnAction(this::plusqte);

        StackPane root = new StackPane();
        //root.getChildren().add(plusqte);
        root.getChildren().addAll(plusqte, qteproduit);
        Scene scene = new Scene(root, 300, 250);
    }

    @FXML
    void moinsqte(ActionEvent event) {
        if (va > 0) {
            va--;
            System.out.println("Quantité : " + va);
            qteproduit.setText(" " + va);
        }
    }

    @FXML
    void plusqte(ActionEvent event) {
        va++;
        System.out.println("Quantité : " + va);
        qteproduit.setText(" " + va);
    }
    /*---------------------controller quantité----------------------------------------------------------------*/

    @FXML
    void panier(MouseEvent event) {

    }

    @FXML
    void recherchep(ActionEvent event) {

    }

    }

