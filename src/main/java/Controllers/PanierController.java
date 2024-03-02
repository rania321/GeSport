package Controllers;

import entities.Panier;
import entities.Produit;
import entities.Vente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import org.example.Service.PanierService;
import javafx.scene.Scene;
import org.example.Service.ProduitService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.Service.VenteService;

import java.io.File;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Optional;

public class PanierController {

    @FXML
    private Button confirmerV;

    @FXML
    private ScrollPane Scrollpa;

    @FXML
    private GridPane gridpa;

    @FXML
    private ImageView imgvisa;

    @FXML
    private Label totalPa;

    private Panier selectedProduit;

    @FXML
    private Button facture;

    @FXML
    private Button buttonBackMenu;

    private PanierService pas = new PanierService();
    //List<Panier> paniers = (List<Panier>) pas.readById(8);
    List<Panier> paniers =  pas.readAll();

    private ProduitService ps = new ProduitService();

    private VenteService vs = new VenteService();

    List<Vente> ventes =  vs.readAll();

    Vente vente;

    // Pour avoir la date actuelle
    Date utilDate = new Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

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

    private HBox createPanierHBox(Panier panier) {
        HBox hbox = new HBox(50);  // Espace horizontal entre chaque activité dans une ligne
        hbox.setPrefSize(609, 124);  // Dimensions spécifiques

        // VBox pour chaque Panier
        VBox vbox = new VBox();
        String imageUrl = ps.getImageFromIdProduit(panier.getIdP());

// Convertissez l'URL en une URL absolue
        File file = new File(imageUrl);
        String absolutePath = file.toURI().toString();

// Créez l'ImageView à partir de l'URL absolue
        ImageView imageView = new ImageView(new Image(absolutePath));
        imageView.setFitWidth(100);  // Ajustez la largeur de l'image selon vos besoins
        imageView.setPreserveRatio(true);
        String nompp = ps.getNomFromIdProduit(panier.getIdP());
        // Label pour afficher le nom du panier
        Label nomLabel = new Label(nompp);
        float prixpp = ps.getPrixFromIdProduit(panier.getIdP());
        // Label pour afficher le prix du panier
        Label prixLabel = new Label(Float.toString(prixpp)+"dt");
        Label quantiteLabel = new Label("Quantité : "+Integer.toString(panier.getQuantiteP()));

        int index = 0;
        float total=0.0f;
        while (index < paniers.size()) {
            Panier p = paniers.get(index);
             total = total + (p.getTotalPa() * p.getQuantiteP());
            index++;
        }

        totalPa.setText(Float.toString(total));

        vbox.getChildren().add(imageView);

        // Ajoutez l'ImageView et les Labels à la VBox
        vbox.getChildren().addAll( nomLabel, prixLabel,quantiteLabel);

        // Si la liste de paniers n'est pas vide, ajoutez un bouton de suppression
        if (!paniers.isEmpty()) {
            Button affichageButton = new Button("Supprimer");
            affichageButton.setOnAction(event -> {
                selectedProduit = panier;
                pas.delete(selectedProduit);
                System.out.println(selectedProduit + " supprimé du panier  ");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
                    Parent root = loader.load();
                    // Obtenir le contrôleur de la nouvelle interface
                    PanierController panierController = loader.getController();

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
            affichageButton.getStyleClass().add("button-vbox");

            // Créer une nouvelle VBox pour le bouton de suppression
            VBox buttonVBox = new VBox(affichageButton);
            //VBox.setMargin(buttonVBox, new Insets(0, 400, 0, -400)); // Ajustez les valeurs selon vos besoins

            buttonVBox.setAlignment(Pos.CENTER_RIGHT);

            // Ajouter la VBox du bouton de suppression à la HBox
            hbox.getChildren().addAll(vbox,buttonVBox);
        } else {
            // Si la liste de paniers est vide, ajoutez simplement la VBox contenant l'ImageView et les Labels à la HBox
            hbox.getChildren().add(vbox);
        }
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

        // Obtenir la source de l'événement
        Node source = (Node) mouseEvent.getSource();

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Buvette");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    /*---------------------controller quantité----------------------------------------------------------------*/

    @FXML
    void confirmerVente(ActionEvent event) {
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmer la vente");
        alert.setContentText("Voulez-vous vraiment confirmer vos achats?");

        // Show the confirmation dialog and wait for the user's choice
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            int index = 0;

            while (index < paniers.size()) {
                Panier panier = paniers.get(index);
                Vente vi = new Vente(2, panier.getIdP(), panier.getQuantiteP(), sqlDate, Float.parseFloat(totalPa.getText()));
                Produit pr= ps.readById(panier.getIdP());
                int nouvelleQuantiteEnStock = pr.getStockP() - panier.getQuantiteP();

                if (nouvelleQuantiteEnStock >= 0) {
                    vs.add(vi);
                    ps.updateQuantite(pr, nouvelleQuantiteEnStock);
                    pas.delete(panier);
                }
                else {
                    System.out.println(pr.getNomP() + "n'est disponible que "+ pr.getStockP() + "unité.");
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Manque de stock ! ");
                    confirmationAlert.setHeaderText(pr.getNomP() + "n'est disponible que "+ pr.getStockP() + "unité.");
                    confirmationAlert.setContentText("Veuillez changer votre commande");
                    confirmationAlert.showAndWait();
                }
                index++;
            }
            System.out.println("Vente confirmée");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
                Parent root = loader.load();
                // Obtenir le contrôleur de la nouvelle interface
                PanierController panierController = loader.getController();

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
        } else {
            System.out.println("Vente annulée");
            // Optionally, you can handle the case where the user cancels the confirmation
        }
    }
    @FXML
    void genererFacture(ActionEvent event) {
        // Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Voulez-vous générer la facture ?");
        confirmationAlert.setContentText("Appuyez sur OK pour générer la facture.");

        // Show the confirmation alert and wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Check if the user clicked OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User wants to generate the bill
            String fileName = "Facture.pdf";
            String cn = " ";
            String cp = " ";
            String cq = " ";

            int index = 0;

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                    // Création d'un document
                    Document document = new Document();
                    // Création d'un écrivain PDF associé au document
                    PdfWriter.getInstance(document, fileOutputStream);

                    // Ouverture du document pour écrire
                    document.open();

                    document.add(new Paragraph("Bienvenue chez GesPort"));
                    document.add(new Paragraph());
                    document.add(new Paragraph("Votre Facture :"));
                    document.add(new Paragraph());
                    document.add(new Paragraph());

                    while (index < paniers.size()) {
                        Panier panier = paniers.get(index);
                        String nompp = ps.getNomFromIdProduit(panier.getIdP());
                        float prixpp = ps.getPrixFromIdProduit(panier.getIdP());
                        int quantite = panier.getQuantiteP();

                        cn= nompp + " " ;
                        document.add(new Paragraph(cn));
                        document.add(new Paragraph());
                        cp= "Prix :"+ prixpp + " dt" ;

                        document.add(new Paragraph(cp));
                        document.add(new Paragraph());
                        cq= "Qté : "+ quantite;

                        document.add(new Paragraph(cq));
                        document.add(new Paragraph());
                        index++;
                    }

                    document.add(new Paragraph("Au revoir."));
                    document.close();
                System.out.println("PDF généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // User canceled the operation
            System.out.println("Génération de la facture annulée par l'utilisateur.");
        }
    }

}


