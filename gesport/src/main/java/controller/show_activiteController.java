package controller;

import entities.Activite;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import service.ActiviteService;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.File;
import java.util.List;

public class show_activiteController {

    @FXML
    private GridPane ActivityListContainer;

    @FXML
    private BorderPane BorderPaneA;

    @FXML
    private ScrollPane ScrollPaneA;

    @FXML
    private VBox VBoxA2;

    @FXML
    private AnchorPane menu_formA;
    private File selectedFile;


    private final ActiviteService as = new ActiviteService();
    List<Activite> activites = as.readAll();

    public void initialize(){
        setActivityGridPaneList();
    }
    private void setActivityGridPaneList() {
        VBox mainVBox = new VBox(20);  // Espace vertical entre chaque ligne
        mainVBox.setPadding(new Insets(10));  // Marge autour du VBox principal

        HBox currentHBox = new HBox(20);  // Espace horizontal entre chaque activité dans une ligne
        for (Activite activite : activites) {
            VBox vbox = createActivityVBox(activite);

            currentHBox.getChildren().add(vbox);

            // Créer une nouvelle ligne après chaque 3 activités
            if (currentHBox.getChildren().size() == 4) {
                mainVBox.getChildren().add(currentHBox);
                currentHBox = new HBox(20);
            }
        }

        // Ajouter la dernière ligne si elle n'est pas complète

        if (!currentHBox.getChildren().isEmpty()) {
            mainVBox.getChildren().add(currentHBox);
        }

        // Ajouter le VBox principal à votre conteneur parent (ScrollPane)
        ScrollPaneA.setContent(mainVBox);

    }
    private VBox createActivityVBox(Activite activite) {
       /* VBox vbox = new VBox();

        Label nomLabel = new Label(activite.getNomA());
        Label typeLabel = new Label(activite.getTypeA());
        Label dispoLabel = new Label(activite.getDispoA());
        Label descriLabel = new Label(activite.getDescriA());
        ImageView imageView = new ImageView();

        try {
            Image image = new Image(new File(activite.getImageA()).toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(300);  // Définir la largeur selon vos besoins
            imageView.setFitHeight(200); // Définir la hauteur selon vos besoins
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple, définir une image par défaut
            // imageView.setImage(defaultImage);
        }

        // Ajouter votre logique pour l'image ici

        vbox.getChildren().addAll(imageView, nomLabel, typeLabel, dispoLabel, descriLabel);
        vbox.setAlignment(Pos.CENTER);

        return vbox;*/
        VBox vbox = new VBox();

        Label nomLabel = new Label(activite.getNomA());
        Label typeLabel = new Label(activite.getTypeA());
        Label dispoLabel = new Label(activite.getDispoA());
        Label descriLabel = new Label(activite.getDescriA());
        ImageView imageView = new ImageView();

        try {
            Image image = new Image(new File(activite.getImageA()).toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(300);  // Définir la largeur selon vos besoins
            imageView.setFitHeight(200); // Définir la hauteur selon vos besoins
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple, définir une image par défaut
            // imageView.setImage(defaultImage);
        }

        // Ajouter votre logique pour l'image ici

        vbox.getChildren().addAll(imageView, nomLabel, typeLabel, dispoLabel, descriLabel);

        // Ajouter un bouton de réservation
        if (activite.getDispoA().equalsIgnoreCase("disponible")) {
            Button reservationButton = new Button("Réserver");
            reservationButton.setOnAction(event -> {
                // Mettez ici la logique pour afficher l'interface de prise de rendez-vous
                System.out.println("Bouton Réserver cliqué pour : " + activite.getNomA());
                // Ajoutez ici le code pour afficher l'interface de réservation
            });

            vbox.getChildren().add(reservationButton);
            vbox.setAlignment(Pos.CENTER);
        }

            return vbox;


    }

}
