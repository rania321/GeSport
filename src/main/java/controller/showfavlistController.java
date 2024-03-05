package controller;

import controllers.LoginUserControllers;
import entities.Activite;
import entities.Reservation;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import service.ActiviteService;
import service.ReservationService;
import service.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class showfavlistController {

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
    User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
    UserService us = new UserService();
    User u = us.readById(LoginUserControllers.getLoggedInUser().getIdU());


    @FXML
    private TextField searchByTypeTF;

    private int found=0;

    private static User loggedInUser;
    public Activite getSelectedActivite() {
        return selectedActivite;
    }

    public void initialize() throws SQLException {
        loggedInUser = LoginUserControllers.getLoggedInUser();
        System.out.println("user : "+ loggedInUser);
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
   /* public void initialize(User user) {
        this.loggedInUser = user;
    }*/
    List<Activite> activites = as.getActiviteFavList(u.getIdU());
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

        return vbox;

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
    void accueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(); // Créez une nouvelle instance de Stage
            stage.setScene(new Scene(root));
            stage.setTitle("GeSport"); // Titre de la fenêtre
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
        stage.setTitle("reclamation");

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
        stage.setTitle("tournois");

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
        stage.setTitle("activite");

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