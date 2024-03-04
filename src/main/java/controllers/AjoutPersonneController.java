package controllers;
import Services.UserService;
import entities.User;
import entities.role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

//import javax.imageio.IIOException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class AjoutPersonneController {
    private final UserService us = new UserService();
    @FXML
    private TextField emailU;

    @FXML
    private TextField mdpU;

    @FXML
    private TextField nomU;

    @FXML
    private TextField prenomU;

    @FXML
    private TextField roleU;

    @FXML
    void ajouter(ActionEvent event) {
        try {
            if (!isValidInput()) {
                // Affichez un message d'erreur si la saisie est invalide
                afficherMessageErreur("Saisie invalide. Veuillez remplir tous les champs correctement.");
                return;
            }


            // Vérifiez le format de l'adresse e-mail
           /* if (!isValidEmail(emailU.getText())) {
                // Affichez un message d'erreur si l'adresse e-mail n'est pas valide
                afficherMessageErreur("Format de l'adresse e-mail invalide. Veuillez saisir une adresse e-mail valide.");
                return;
            }*/
            // Vérifiez le format de l'adresse e-mail en utilisant EmailValidator
            EmailValidator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(emailU.getText())) {
                // Affichez un message d'erreur si l'adresse e-mail n'est pas valide
                afficherMessageErreur("Format de l'adresse e-mail invalide. Veuillez saisir une adresse e-mail valide.");
                return;
            }

            // Vérifiez si le mot de passe contient au moins une lettre majuscule et un chiffre
            if (!isValidPassword(mdpU.getText())) {
                // Affichez un message d'erreur si le mot de passe ne satisfait pas les critères
                afficherMessageErreur("Le mot de passe doit contenir au moins une lettre majuscule et un chiffre.");
                return;
            }
            if (us.isEmailAlreadyUsed(emailU.getText())) {
                afficherMessageErreur("L'email est déjà utilisé par un autre utilisateur. Veuillez saisir un autre email.");
                return;
            }
            String hashedPassword = hashPassword(mdpU.getText());

            // Vérifier si le mot de passe est déjà utilisé par un autre utilisateur
            if (us.isPasswordAlreadyUsed(hashedPassword)) {
                afficherMessageErreur("Le mot de passe est déjà utilisé par un autre utilisateur. Veuillez saisir un autre mot de passe.");
                return;
            }
            openCaptchaInterface(event);

            // Add the new user

            us.add(new User(nomU.getText(), prenomU.getText(), emailU.getText(), hashedPassword));
            afficherMessageConfirmation("Utilisateur ajouté avec succès.");

            // Load AfficherUserController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            Parent root = loader.load();
            AfficherUserController au = loader.getController();
            TableView<User> tableView = (TableView<User>) root.lookup("#tableAdd");

            // Clear existing items in the table and add the new list of users
            tableView.getItems().clear();
            List<User> users = us.readall();
            tableView.getItems().addAll(users);

            // Set the new user details in AfficherUserController
            au.setNomU(nomU.getText());
            au.setPrenomU(prenomU.getText());
            au.setEmailU(emailU.getText());
            au.setMdpU(mdpU.getText());
            au.setRoleU(roleU.getText());

            // Load Login.fxml in a new window
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent loginRoot = loginLoader.load();
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Définissez la racine de la scène actuelle sur le formulaire de connexion
            currentScene.setRoot(loginRoot);
            /*Scene loginScene = new Scene(loginRoot);

            // Afficher l'interface de connexion dans une nouvelle fenêtre
            Stage loginStage = new Stage();
            loginStage.setScene(loginScene);
            loginStage.setTitle("Interface de connexion");
            loginStage.show();*/
        } catch (Exception ex) {
            afficherMessageErreur("Une erreur s'est produite lors de l'ajout de l'utilisateur.");
            // Handle exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private boolean isValidInput() {
        return !nomU.getText().isEmpty()
                && !prenomU.getText().isEmpty()
                && !emailU.getText().isEmpty()
                && !mdpU.getText().isEmpty();

    }

    // Méthode pour valider le format de l'adresse e-mail
    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    // Méthode pour valider le format du mot de passe
    private boolean isValidPassword(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    // Méthode pour afficher un message d'erreur
    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher un message de confirmation
    private void afficherMessageConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String hashPassword(String password) {
        try {
            // Créez un objet MessageDigest pour l'algorithme de hachage SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Appliquez le hachage au mot de passe
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Convertissez les bytes hachés en une représentation hexadécimale
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // Gérer l'exception si l'algorithme de hachage n'est pas disponible
            e.printStackTrace();
            return null;
        }
    }

    private void openCaptchaInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Captcha.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène pour l'interface de captcha
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre modale pour l'interface de captcha
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Captcha Verification");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // Attendre la fermeture de la fenêtre de captcha avant de continuer avec la connexion
            stage.showAndWait();

            // Une fois que la fenêtre de captcha est fermée, vérifiez le résultat (captcha validé ou non)
            // Vous devrez définir une méthode dans votre contrôleur CaptchaController pour récupérer le résultat du captcha

            // Par exemple:
            // if (CaptchaController.isCaptchaValid()) {
            //     // Le captcha est valide, continuez avec le processus de connexion
            //     performLogin();
            // } else {
            //     // Le captcha est incorrect, informez l'utilisateur
            //     alertError("Captcha incorrect. Veuillez réessayer.");
            // }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}










