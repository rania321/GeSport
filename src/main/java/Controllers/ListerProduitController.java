package Controllers;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

public class ListerProduitController implements Initializable {

    @FXML
    private Button accueil;

    @FXML
    private Button activité;

    @FXML
    private Button ajoutpanier;

    @FXML
    private Button compte;

    @FXML
    private Button moinsqte;

    @FXML
    private Button plusqte;

    @FXML
    private Label qtelb;

    @FXML
    private Label qteproduit;

    @FXML
    private Button recherchep;

    @FXML
    private ImageView showpanier;

    @FXML
    private Button tournois;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private ProduitService ps = new ProduitService();

    private int va =0;

    private List<Produit> produits = new ArrayList<>();
    /*private List<Produit> getData() {
         List<Produit> p = new ArrayList<>();
        // Produit produit;
         for (int i=0; i<20; i++)
         {
            // produit= new Produit();
             ps = new ProduitService();
             ps.readById(1);
         }
         return produits;
    }*/

    /*private List<Produit> getData() {
         List<Produit> p = new ArrayList<>();
         Produit produit;
         for (int i=0; i<20; i++)
         {
             produit= new Produit();
             produit.setNomP("aa");
             produit.setPrixP(5f);
             produit.setImageP(" ");

         }
         return produits;
    }*/

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /*
    public void initialize(){
        setActivityGridPaneList();
    }
    private void setActivityGridPaneList() {
        VBox mainVBox = new VBox(20);  // Espace vertical entre chaque ligne
        mainVBox.setPadding(new Insets(10));  // Marge autour du VBox principal

        HBox currentHBox = new HBox(20);  // Espace horizontal entre chaque activité dans une ligne
        for (Produit produit : produits) {
            VBox vbox = createActivityVBox(produit);

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
    private VBox createActivityVBox(Produit produit) {
        VBox vbox = new VBox();

        Label nomLabel = new Label(produit.getNomP());
        Label prixLabel = new Label(produit.getPrixP());
        ImageView imageView = new ImageView();

        try {
            Image image = new Image(new File(produit.getImageP()).toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(300);  // Définir la largeur selon vos besoins
            imageView.setFitHeight(200); // Définir la hauteur selon vos besoins
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple, définir une image par défaut
            // imageView.setImage(defaultImage);
        }

        // Ajouter votre logique pour l'image ici

        vbox.getChildren().addAll(imageView, nomLabel);

        // Ajouter un bouton de réservation
        if (produit.getStockP()!= 0) {
            Button reservationButton = new Button("Voir");
            reservationButton.setOnAction(event -> {
                selectedActivite = produit;
                System.out.println("Activité sélectionnée : " + selectedActivite);
                // Mettez ici la logique pour afficher l'interface de prise de rendez-vous
                System.out.println("Bouton Réserver cliqué pour : " + produit.getNomP());
                // Ajoutez ici le code pour afficher l'interface de réservation

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_reservation.fxml"));
                    Parent root = loader.load();
                    // Obtenir le contrôleur de la nouvelle interface
                    add_reservationController addReservationController = loader.getController();


                    // Passer des données à votre contrôleur de réservation si nécessaire
                    System.out.println("Passage de l'activité au contrôleur add_reservationController : " + activite.getNomA());
                    addReservationController.initializeWithActivite(activite);

                    addReservationController.setActivite(activite);


                    // Créer une nouvelle scène
                    Scene scene = new Scene(root);

                    // Configurer la nouvelle scène dans une nouvelle fenêtre
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Réservation pour : " + activite.getNomA());


                    // Afficher la nouvelle fenêtre
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            });
            reservationButton.getStyleClass().add("round-buttonMenu1");

            vbox.getChildren().add(reservationButton);
        }
        vbox.setAlignment(Pos.CENTER);
        return vbox;

    }*/

   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        produits.addAll(getData());
        int column=3;
        int row =0;
        try {
        for(int i=0; i<produits.size(); i++){
            FXMLLoader fxmlLoader =new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/items.fxml"));
            AnchorPane anchorPane = null;

            anchorPane = fxmlLoader.load();

            ItemsController itemsController = fxmlLoader.getController();
            itemsController.setData(produits.get(i));

                if (column==3)
                {
                    column=0;
                    row++;
                }

            grid.add(anchorPane, column++, row);
            GridPane.setMargin(anchorPane,new Insets( 10));
            }
        } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

