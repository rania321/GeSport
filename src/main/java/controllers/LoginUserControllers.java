package controllers;

import Services.UserService;
import entities.User;
import entities.role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginUserControllers {
   /* @FXML
    private TextField EmailU;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField PasswordU;
    @FXML
    private Button goRegister;
    private UserService userService = new UserService();


    @FXML
    void goregister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register_interface.fxml"));
            loader.load();
            // Vous pouvez également obtenir le contrôleur de l'interface d'inscription ici si nécessaire

            // Rediriger vers l'interface d'inscription
            // Parent root = loader.getRoot();
            // Stage stage = (Stage) goRegister.getScene().getWindow();
            // stage.setScene(new Scene(root));
            // stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void LoginButton(ActionEvent event) {
        String email = EmailU.getText();
        String password = PasswordU.getText();

        // Vérifier les informations de connexion
        User loggedInUser = userService.login(email, password);
        if (loggedInUser != null) {
            // Connexion réussie
            role Role = loggedInUser.getRole();
            switch (role) {
                case ADMIN:
                    // Rediriger vers l'interface d'administration
                    loadAdminInterface();
                    break;
                case USER:
                    // Rediriger vers l'interface utilisateur
                    loadUserInterface();
                    break;
                default:
                    // Gérer les autres rôles si nécessaire
                    break;
            }
        } else {
            // Afficher un message d'erreur en cas d'échec de la connexion
            alertError("Identifiants invalides. Veuillez réessayer.");
        }
    }
    private void loadAdminInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_interface.fxml"));
            loader.load();
            // Vous pouvez également obtenir le contrôleur de l'interface admin ici si nécessaire

            // Rediriger vers l'interface admin
            // Parent root = loader.getRoot();
            // Stage stage = (Stage) LoginButton.getScene().getWindow();
            // stage.setScene(new Scene(root));
            // stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_interface.fxml"));
            loader.load();
            // Vous pouvez également obtenir le contrôleur de l'interface utilisateur ici si nécessaire

            // Rediriger vers l'interface utilisateur
            // Parent root = loader.getRoot();
            // Stage stage = (Stage) LoginButton.getScene().getWindow();
            // stage.setScene(new Scene(root));
            // stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }*/
}
