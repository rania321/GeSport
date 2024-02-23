package controllers;
import Services.UserService;
import entities.User;
import entities.role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

//import javax.imageio.IIOException;
import java.io.IOException;
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
    /*void ajouter(ActionEvent event) {
        try {
            us.add(new User(nomU.getText(), prenomU.getText(), emailU.getText(), mdpU.getText()));
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            try {
                Parent root=loader.load();
                AfficherUserController au=loader.getController();
                au.setNomU(nomU.getText());
                au.setPrenomU(prenomU.getText());
                au.setEmailU(emailU.getText());
                au.setMdpU(mdpU.getText());
                au.setRoleU(roleU.getText());
                au.setListU(us.readall().toString());
                nomU.getScene().setRoot(root);

            }
            catch (IOException e) {
                System.out.println(e);
            }

        } catch(Exception ex) { // Attraper une exception plus générale
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }*/
    void ajouter(ActionEvent event) {
        try {
            if (!isValidInput()) {
                // Affichez un message d'erreur si la saisie est invalide
                afficherMessageErreur("Saisie invalide. Veuillez remplir tous les champs correctement.");
                return;
            }

            // Vérifiez le format de l'adresse e-mail
            if (!isValidEmail(emailU.getText())) {
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

            // Vérifier si le mot de passe est déjà utilisé par un autre utilisateur
            if (us.isPasswordAlreadyUsed(mdpU.getText())) {
                afficherMessageErreur("Le mot de passe est déjà utilisé par un autre utilisateur. Veuillez saisir un autre mot de passe.");
                return;
            }

            // Add the new user
            us.add(new User(nomU.getText(), prenomU.getText(), emailU.getText(), mdpU.getText()));
            afficherMessageConfirmation("Utilisateur ajouté avec succès.");


            // Load AfficherUserController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            Parent root = loader.load();
            AfficherUserController au = loader.getController();
            TableView<User> tableView = (TableView<User>) root.lookup("#tableAdd");


            // Optionally, you can clear the input fields after adding the user


            // Clear existing items in the table and add the new list of users
            tableView.getItems().clear();


            // Récupérer tous les utilisateurs depuis la base de données
            List<User> users = us.readall();

            // Ajouter les utilisateurs au TableView
            tableView.getItems().addAll(users);

            // Set the new user details in AfficherUserController
            au.setNomU(nomU.getText());
            au.setPrenomU(prenomU.getText());
            au.setEmailU(emailU.getText());
            au.setMdpU(mdpU.getText());
            au.setRoleU(roleU.getText());

            // Set the root scene
            nomU.getScene().setRoot(root);
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
}







