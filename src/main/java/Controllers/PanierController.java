package Controllers;

import entities.Panier;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import org.example.Service.PanierService;
import javafx.scene.Scene;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PanierController {

    @FXML
    private ScrollPane Scrollpa;

    @FXML
    private GridPane gridpa;

    @FXML
    private ImageView imgvisa;

    @FXML
    private Label totalPa;

    private Panier selectedProduit;

    private PanierService pas = new PanierService();
    List<Panier> paniers = pas.readAll();

    public void initialize() {
        setPanierGridPaneList();
    }

    private void setPanierGridPaneList() {
        int rowIndex = 0;
        int colIndex = 0;

        for (Panier panier : paniers) {
            HBox panierHBox = createPanierHBox(panier);
            panierHBox.setMargin(panierHBox, new Insets(20, 60, 50, 0));

            gridpa.add(panierHBox, colIndex, rowIndex);

            colIndex++;
            if (colIndex == 1) {  // Changez 3 selon le nombre d'éléments que vous voulez afficher par ligne
                colIndex = 0;
                rowIndex++;
            }
        }
    }

   /* private void setPanierGridPaneList() {
        VBox mainVBox = new VBox(20);  // Espace vertical entre chaque ligne
        mainVBox.setPadding(new Insets(10));  // Marge autour du VBox principal

        for (Panier panier : paniers) {
            HBox panierHBox = createPanierHBox(panier);
            mainVBox.getChildren().add(panierHBox);
        }

        // Ajouter le VBox principal à votre conteneur parent (ScrollPane)
        Scrollpa.setContent(mainVBox);
    }*/

    private HBox createPanierHBox(Panier panier) {
        HBox hbox = new HBox(20);  // Espace horizontal entre chaque activité dans une ligne
        hbox.setPrefSize(609, 124);  // Dimensions spécifiques

        VBox vbox = new VBox();  // VBox pour chaque Panier
        Label nomLabel = new Label(Integer.toString(panier.getIdP()));
        Label prixLabel = new Label(Integer.toString(panier.getIdV()));

        float total= panier.getTotalPa()*panier.getQuantiteP();
        totalPa.setText(Float.toString(total));

        // Ajoutez d'autres éléments en fonction de votre besoin
        vbox.getChildren().addAll(nomLabel, prixLabel);

        if (paniers.size() != 0) {
            Button affichageButton = new Button("Supprimer");
            affichageButton.setOnAction(event -> {
                selectedProduit = panier;
                pas.delete(selectedProduit);
                System.out.println(selectedProduit + " supprimé du panier  " );

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
                    Parent root = loader.load();
                   // Obtenir le contrôleur de la nouvelle interface
                    PanierController PanierController = loader.getController();


                    // Créer une nouvelle scène
                    Scene scene = new Scene(root);

                    // Configurer la nouvelle scène dans une nouvelle fenêtre
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);

                    // Afficher la nouvelle fenêtre
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            affichageButton.getStyleClass().add("round-buttonMenu1");

            vbox.getChildren().add(affichageButton);
        }
            vbox.setAlignment(Pos.CENTER);

            hbox.getChildren().add(vbox);
            return hbox;
    }


    @FXML
    void accueil(javafx.event.ActionEvent actionEvent) throws IOException{
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerProduit.fxml"));
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

    public void panier(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Image actionEvent = null;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Buvette");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    /*---------------------controller quantité----------------------------------------------------------------*/
    
}
