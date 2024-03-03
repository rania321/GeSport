package controllers;

import Services.UserService;
import entities.User;
import entities.role;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

import javafx.util.Duration;
import mailing.SendEmail;
import org.controlsfx.control.Notifications;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class LoginUserControllers {
    public javafx.scene.control.TextField EmailU;
    public javafx.scene.control.TextField PasswordU;
    public VBox changePasswordVBox;
    public PasswordField newPasswordField;
    public PasswordField confirmPasswordField;
    public Label passwordChangeStatusLabel;
    public Button showPasswordButton;
    public TextField passw;
    private boolean isPasswordVisible = false;

    @FXML
    private Button LoginButton;
    private String url = "jdbc:mysql://localhost:3306/gesport";
    private String login = "root";
    private String pwd = "";


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
    public void goregister(ActionEvent event) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardBack.fxml"));
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


    private void alertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void LoginButton(javafx.event.ActionEvent event) {
        openCaptchaInterface(event);
        String email = EmailU.getText();
        String password = PasswordU.getText();
        String hashedPassword = hashPassword(password);

        // Vérifier les informations de connexion
        User loggedInUser = userService.login(email, hashedPassword);
        if (loggedInUser != null) {
            showNotification();
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Votre compte est ouvert");
            confirmAlert.setContentText("Est-ce bien vous ?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

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

    public String generateCode() {
        // Define characters to be used in the code
        String characters = "0123456789";

        // Initialize random object
        Random random = new Random();

        // Initialize StringBuilder to store the generated code
        StringBuilder code = new StringBuilder();

        // Generate code of length 4
        for (int i = 0; i < 4; i++) {
            // Generate random index within the characters string length
            int randomIndex = random.nextInt(characters.length());

            // Append character at the random index to the code
            code.append(characters.charAt(randomIndex));
        }

        // Convert StringBuilder to String and return
        return code.toString();
    }


    public void modifierPassword(String email, String newPassword) {
        String req = "UPDATE user SET mdpU = ? WHERE EmailU = ?";
        Connection conn = null;
        try {
            // Chargez le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Créez la connexion
            conn = DriverManager.getConnection(url, login, pwd);

            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, newPassword);
            pst.setString(2, email);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully for user with email: " + email);
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error updating password for user with email: " + email);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void forgotPasswordButtonAction(ActionEvent event) {
        // Generate a random code
        if (EmailU.getText().isEmpty()) {
            // Show a warning message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your email address.");
            alert.showAndWait();
            return; // Exit the method
        }

        String generatedCode = generateCode();

        // Send the code via email
        SendEmail.send(EmailU.getText(), Integer.parseInt(generatedCode));

        // Prompt the user to enter the code
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Verification");
        dialog.setHeaderText("Enter the verification code sent to your email:");
        dialog.setContentText("Code:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String enteredCode = result.get();
            if (enteredCode.equals(generatedCode)) {
                // Code is correct, show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Verification successful. You can proceed to reset your password.");
                alert.showAndWait();
                changePasswordVBox.setVisible(true);

                // Proceed to reset password page
                // Add your code to navigate to the reset password page here
            } else {
                // Code is incorrect, show alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid verification code. Please try again.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void changePasswordAction(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            passwordChangeStatusLabel.setText("Passwords do not match.");
            return;
        }
        if (!isPasswordStrong(newPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Password must contain at least one uppercase letter and one digit.");
            return;
        }
        String hashedPassword = hashPassword(newPassword);


        // Update the password in the database

        String email = EmailU.getText(); // Assuming you have an emailField for the user's email
        modifierPassword(email, hashedPassword);

        // Show success message
        passwordChangeStatusLabel.setText("Password changed successfully.");

        changePasswordVBox.setVisible(false);
    }

    private boolean isPasswordStrong(String password) {
        // Check if password contains at least one uppercase letter and one digit
        boolean containsUppercase = false;
        boolean containsDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUppercase = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            }
        }

        return containsUppercase && containsDigit;
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
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

    private void showNotification() {
        try {
            Image image = new Image("/notification.png");

            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Donation added successfully");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showpasswd(ActionEvent event) {
        // Récupérer le mot de passe du champ PasswordU
        String password = PasswordU.getText();

        // Afficher le mot de passe dans le champ passw
        passw.setText(password);

        // Rendre le champ de texte passw visible
        passw.setVisible(true);

        // Créer une Timeline pour masquer le mot de passe après 2 secondes
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            passw.setVisible(false); // Masquer le champ de texte passw après 2 secondes
            passw.clear(); // Effacer le contenu du champ de texte passw
        }));
        timeline.play();
    }
}



