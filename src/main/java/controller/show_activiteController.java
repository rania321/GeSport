package controller;

import controllers.LoginUserControllers;
import entities.Activite;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import service.ActiviteService;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.UserService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
    private Button btnMesReserv;
    @FXML
    private AnchorPane menu_formA;
    private File selectedFile;

    private Activite selectedActivite;

    private final ActiviteService as = new ActiviteService();
    List<Activite> activites = as.readAll();

    @FXML
    private TextField searchByTypeTF;

    private int found=0;
    private static User loggedInUser;


    public Activite getSelectedActivite() {
        return selectedActivite;
    }

    public void initialize() throws SQLException {
        loggedInUser = LoginUserControllers.getLoggedInUser();
        setActivityGridPaneList();
        // Ajoutez cet écouteur pour la recherche dynamique
        searchByTypeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchByType(newValue); // Appel de la méthode de recherche avec le nouveau texte
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
    private void setActivityGridPaneList() {
        VBox mainVBox = new VBox(20);  // Espace vertical entre chaque ligne
        mainVBox.setPadding(new Insets(10));  // Marge autour du VBox principal

        HBox currentHBox = new HBox(20);  // Espace horizontal entre chaque activité dans une ligne
        for (Activite activite : activites) {
            VBox vbox = createActivityVBox(activite);

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
        ScrollPaneA.setContent(mainVBox);

    }
    private void searchByType(String searchText) throws IOException {
        VBox mainVBox = new VBox(20);
        mainVBox.setPadding(new Insets(10));

        HBox currentHBox = new HBox(20);

        for (Activite activite : activites) {
            if (activite.getTypeA().toLowerCase().contains(searchText.toLowerCase())) {
                VBox vbox = createActivityVBox(activite);
                currentHBox.getChildren().add(vbox);

                if (currentHBox.getChildren().size() == 3) {
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox(20);
                }
            }
        }

        if (!currentHBox.getChildren().isEmpty()) {
            mainVBox.getChildren().add(currentHBox);
        }

        ScrollPaneA.setContent(mainVBox);
    }
    private VBox createActivityVBox(Activite activite) {
        User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        UserService us = new UserService();
        User u = us.readById(2);
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
                selectedActivite = activite;
                System.out.println("Activité sélectionnée : " + selectedActivite);
                // Mettez ici la logique pour afficher l'interface de prise de rendez-vous
                System.out.println("Bouton Réserver cliqué pour : " + activite.getNomA());
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

        // Ajouter un bouton représentant le cœur vide
        Button addToFavoritesButton = new Button();
        ImageView heartImage ;
        if (isActiviteInFavList(activite.getIdA(), loggedInUser.getIdU())) {
            // Si l'activité est dans la liste des favoris, utiliser le cœur rouge
            heartImage = new ImageView(new Image("/img/favori (1).png"));
        } else {
            // Sinon, utiliser le cœur vide
            heartImage = new ImageView(new Image("/img/favori.png"));
        }
        heartImage.setFitWidth(40);  // Ajustez la largeur de l'image selon vos besoins
        heartImage.setFitHeight(40);
        addToFavoritesButton.setGraphic(heartImage);
        addToFavoritesButton.setStyle("-fx-background-color: transparent;");

        // Ajouter un label pour afficher le nombre de likes
        Label likeCountLabel = new Label(getLikeCountText(activite.getIdA()));
        likeCountLabel.setStyle("-fx-font-size: 14px;");

        addToFavoritesButton.setOnAction(event -> {
            selectedActivite = activite;
            ActiviteService activiteService = new ActiviteService();


            try {
                found = activiteService.activiteInFavList(activite.getIdA(), loggedInUser.getIdU());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (found == 0) {
            //if (isActiviteInFavList(activite.getIdA(), user.getIdU()))
                // Ajouter l'activité aux favoris
                activiteService.addActiviteToFavoriteList(selectedActivite.getIdA(), loggedInUser.getIdU());
                updateFavoritesButton(addToFavoritesButton,"/img/favori (1).png" );
                // Afficher un message ou effectuer d'autres actions si nécessaire
                System.out.println("Activité ajoutée aux favoris avec succès!");
            }
            else{
                try {
                    activiteService.removeActiviteFromFavoriteList(selectedActivite.getIdA(), loggedInUser.getIdU());
                    updateFavoritesButton(addToFavoritesButton,"/img/favori.png" );
                    System.out.println("Activité supprimée aux favoris avec succès!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            // Mettre à jour le texte du label de comptage des likes
            likeCountLabel.setText(getLikeCountText(activite.getIdA()));
        });

        //vbox.getChildren().add(addToFavoritesButton);
        vbox.getChildren().addAll(addToFavoritesButton, likeCountLabel);
        vbox.setAlignment(Pos.CENTER);
            return vbox;

    }
    private String getLikeCountText(int activiteId) {
        int likeCount = as.getLikeCountForActivite(activiteId);
        return "Likes: " + likeCount;
    }

    private void updateFavoritesButton(Button button, String imagePath) {
        ImageView heartImage = new ImageView(new Image(imagePath));
        heartImage.setFitWidth(40);
        heartImage.setFitHeight(40);
        button.setGraphic(heartImage);
        button.setStyle("-fx-background-color: transparent;");
    }

    private boolean isActiviteInFavList(int activiteID, int userId) {
        try {
            return as.activiteInFavList(activiteID, userId) == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void mesreservations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show_reservation.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Mes réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void mesfavoris(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/showfavlist.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Mes Activités Favoris");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void accueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(); // Créez une nouvelle instance de Stage
            stage.setScene(new Scene(root));
            stage.setTitle("Interface utilisateur"); // Titre de la fenêtre
            stage.show();

            // Fermez la fenêtre actuelle si nécessaire
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void compte(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Compte");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void reclamation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionReclamation.fxml"));
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
    void tournois(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournoiClient.fxml"));
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
    void activite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show_activite.fxml"));
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

}
