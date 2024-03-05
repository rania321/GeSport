package controllers;

import Services.UserService;
import entities.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.Optional;

public class ProfileController {
    public TextField PrenomU;
    public TextField EmailU;
    public TextField mdpU;
    public TextField NomU;
    public ListView<String> listU;
    @FXML
    public ImageView avatarImageView;
    private UserService userService = new UserService();
    private User loggedInUser;


    public void initialize() {
        // Récupérer l'utilisateur connecté depuis le contrôleur LoginUserControllers
        loggedInUser = LoginUserControllers.getLoggedInUser();

        // Afficher les données de l'utilisateur connecté dans les champs texte
        if (loggedInUser != null) {
            loadAvatarAsync(loggedInUser.getNomU());
            NomU.setText(loggedInUser.getNomU());
            PrenomU.setText(loggedInUser.getPrenomU());
            EmailU.setText(loggedInUser.getEmailU());
            //mdpU.setText(loggedInUser.getMdpU());

            // Ajouter d'autres informations à la liste

            // Par exemple, vous pouvez ajouter d'autres informations comme l'email, le nom, etc.
            listU.getItems().add("Email: " + loggedInUser.getEmailU());
            listU.getItems().add("Nom: " + loggedInUser.getNomU());
            listU.getItems().add("Prenom: " + loggedInUser.getPrenomU());
            //listU.getItems().add("Motdepasse: " + loggedInUser.getMdpU());
        }
    }



    public void SupprCuser(ActionEvent event) {
       /* if (loggedInUser != null) {
          /*  // Supprimer l'utilisateur de la base de données
            userService.deleteById(loggedInUser.getIdU());

            // Mettre à jour la TableView en supprimant l'utilisateur de sa liste d'éléments
            // Par exemple, si vous avez une liste observable appelée "usersList",
            // vous pouvez appeler usersList.remove(loggedInUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();


        } else {
            System.err.println("L'utilisateur connecté est null.");
        }*/
        if (loggedInUser == null) {
            System.err.println("L'utilisateur connecté est null.");
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de suppression");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
        confirmationDialog.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si l'utilisateur confirme la suppression, supprimez l'utilisateur
            userService.deleteById(loggedInUser.getIdU());

            // Fermer la fenêtre
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void modifieCuser(ActionEvent event) {
        loggedInUser.setNomU(NomU.getText());
        loggedInUser.setPrenomU(PrenomU.getText());
        loggedInUser.setEmailU(EmailU.getText());
        loggedInUser.setMdpU(mdpU.getText());

        // Mettre à jour l'utilisateur dans la base de données
        userService.update(loggedInUser);
        NomU.setText(loggedInUser.getNomU());
        PrenomU.setText(loggedInUser.getPrenomU());
        EmailU.setText(loggedInUser.getEmailU());
        mdpU.setText(loggedInUser.getMdpU());

        // Mettre à jour la ListView avec les nouvelles informations de l'utilisateur
        listU.getItems().clear(); // Effacer toutes les informations actuelles dans la ListView
        // Ajouter les nouvelles informations de l'utilisateur à la ListView
        listU.getItems().add("Email: " + loggedInUser.getEmailU());
        listU.getItems().add("Nom: " + loggedInUser.getNomU());
        listU.getItems().add("Prenom: " + loggedInUser.getPrenomU());
        listU.getItems().add("Motdepasse: " + loggedInUser.getMdpU());
    }

    public void gotoAjouterDm(ActionEvent event) {
        /*try {
            // Charger l'interface utilisateur AjoutDm.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDm.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à AjoutDm.fxml
            AjoutDmController ajoutDmController = loader.getController();

            // Passer l'ID de l'utilisateur actuellement connecté au contrôleur AjoutDmController
            ajoutDmController.setLoggedInUserId(loggedInUser.getIdU());

            // Afficher la nouvelle interface utilisateur
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter DM");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            // Charger l'interface utilisateur AjoutDm.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDm.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à AjoutDm.fxml
            AjoutDmController ajoutDmController = loader.getController();

            // Passer l'ID de l'utilisateur actuellement connecté au contrôleur AjoutDmController
            ajoutDmController.setLoggedInUserId(loggedInUser.getIdU());

            // Remplacer le contenu de la fenêtre actuelle par la nouvelle interface utilisateur
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ajouter DM");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
@FXML
    public void Accueil(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
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
    void joke(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Joke.fxml"));
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
    private void loadAvatarAsync(String username) {
        Task<Image> task = new Task<>() {
            @Override
            protected Image call() {
                try {
                    // Générer l'URL de l'avatar à partir de l'adresse e-mail de l'utilisateur
                    String avatarUrl = "https://robohash.org/" + username + ".png";
                    return new Image(avatarUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        task.setOnSucceeded(event -> {
            Image avatarImage = task.getValue();
            if (avatarImage != null) {
                // Assurez-vous que la mise à jour de l'interface utilisateur est effectuée sur le thread de l'interface utilisateur
                Platform.runLater(() -> avatarImageView.setImage(avatarImage));
            }
        });

        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        new Thread(task).start();
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
        stage.setTitle("Activités");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void compte(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Profile.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Profile.fxml");
        stage.show();
    }

    @FXML
    void reclamation(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/GestionReclamation.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Profile.fxml");
        stage.show();
    }

    @FXML
    void restaurant(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AccueilProduit.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Profile.fxml");
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
        stage.setTitle("Activités");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
}