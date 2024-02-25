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
import javafx.stage.Stage;

import java.awt.*;
import javafx.scene.control.Button;


import java.io.IOException;

public class LoginUserControllers {
    public javafx.scene.control.TextField EmailU;
    public javafx.scene.control.TextField PasswordU;

    @FXML
    private Button LoginButton;


    @FXML
    private Button goRegister;
    private UserService userService = new UserService();
    private static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    // Méthode pour obtenir l'utilisateur connecté
    public static User getLoggedInUser() {
        return loggedInUser;
    }


    @FXML
    public void goregister(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(); // Créez une nouvelle instance de Stage
            stage.setScene(new Scene(root));
            stage.setTitle("Interface d'administration"); // Titre de la fenêtre
            stage.show();

            // Fermez la fenêtre actuelle si nécessaire
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML

    /*private void loadAdminInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashbordBack.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void loadAdminInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashbordBack.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(); // Créez une nouvelle instance de Stage
            stage.setScene(new Scene(root));
            stage.setTitle("Interface d'administration"); // Titre de la fenêtre
            stage.show();

            // Fermez la fenêtre actuelle si nécessaire
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadUserInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
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


    private void alertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void LoginButton(javafx.event.ActionEvent event) {
        String email = EmailU.getText();
        String password = PasswordU.getText();

        // Vérifier les informations de connexion
        User loggedInUser = userService.login(email, password);
        if (loggedInUser != null) {

            // Connexion réussie
            role role1 = loggedInUser.getRoleU();
            switch (role1) {
                case Admin:
                    // Rediriger vers l'interface d'administration
                    loadAdminInterface(event);
                    break;
                case utulisateur:

                    // Rediriger vers l'interface utilisateur
                    loadUserInterface(event);
                    break;
                default:
                    // Gérer les autres rôles si nécessaire
                    break;
            }
            setLoggedInUser(loggedInUser);

        } else {
            // Afficher un message d'erreur en cas d'échec de la connexion
            alertError("Identifiants invalides. Veuillez réessayer.");
        }
    }


}
