package Controllers;

import entities.Panier;
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
import org.example.Service.PanierService;
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
    private ImageView imageaffp;

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

    private Panier panier;
    private PanierService pas = new PanierService();

    private int va =0;

    public void initialize(){

        setProduitGridPaneList();
    }
    private void setProduitGridPaneList( ) {
        VBox mainVBox = new VBox(60);  // Espace vertical entre chaque ligne
        mainVBox.setPadding(new Insets(100));  // Marge autour du VBox principal

        HBox currentHBox = new HBox(100);  // Espace horizontal entre chaque activité dans une ligne
        for (Produit produit : produits) {
            VBox vbox = createProduitVBox(produit);

            currentHBox.getChildren().add(vbox);

            // Créer une nouvelle ligne après chaque 3 activités
            if (currentHBox.getChildren().size() == 3) {
                mainVBox.getChildren().add(currentHBox);
                currentHBox = new HBox(100);
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
        Label prixLabel = new Label(Float.toString(produit.getPrixP()) + "dt");
        ImageView imageView = new ImageView();

        try {
            Image image = new Image(new File(produit.getImageP()).toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(200);  // largeur
            imageView.setFitHeight(200); // hauteur
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ajouter votre logique pour l'image ici

        vbox.getChildren().addAll(imageView,nomLabel, prixLabel);

        // Ajouter un bouton affichage
        if (produit.getStockP()!= 0) {
            Button affichageButton = new Button("Voir");
            affichageButton.setOnAction(event -> {
                selectedProduit = produit;
                System.out.println("Produit sélectionnée : " + selectedProduit);

                nompAf.setText("" + selectedProduit.getNomP());
                prixpAf.setText(selectedProduit.getPrixP()+"   dt");
                descrpAf1.setText("" + selectedProduit.getDescriP());
                refpAf.setText("" + selectedProduit.getReferenceP());
                Image image = new Image(new File(selectedProduit.getImageP()).toURI().toString());
                imageaffp.setImage(image);
            });
            affichageButton.getStyleClass().add("round-buttonMenu1");

            vbox.getChildren().add(affichageButton);

            ajoutpanier.setOnAction(event -> {
                try {

                    int quantite = Integer.parseInt(qteproduit.getText());
                    float  total=quantite*selectedProduit.getPrixP();
                    //total = Float.parseFloat(totalclabel.getText());
                    totalclabel.setText(total+"dt");
                    Panier pa = new Panier(9, selectedProduit.getIdP(), quantite, total);
                    pas.add(pa);
                    System.out.println("Ajout au panier effectuer avec succes ");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            });
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
    public void restaurant(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilProduit.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Buvette");

        // Afficher la nouvelle fenêtre
        stage.show();
    }


    @FXML
    void tournois(ActionEvent event) {

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
        if (va > 1) {
            va--;
            System.out.println("Quantité : " + va);
            qteproduit.setText(String.valueOf(va));
        }
    }

    @FXML
    void plusqte(ActionEvent event) {
        va++;
        System.out.println("Quantité : " + va);
        qteproduit.setText(String.valueOf(va));
    }
    /*---------------------controller quantité----------------------------------------------------------------*/

   /* public void recherchep(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        java.awt.Image actionEvent = null;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Buvette");

        // Afficher la nouvelle fenêtre
        stage.show();
    }*/

    @FXML
    void recherchep(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Buvette");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    }

