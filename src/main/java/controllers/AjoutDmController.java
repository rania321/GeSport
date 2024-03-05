package controllers;

import Services.dmservices;
import entities.User;
import entities.dossiermedical;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AjoutDmController {
    public TextField PoidsDM;
    public TextField tailleDM;
    public TextField ageDM;
    public ListView<String> listDm;
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    private User loggedInUser;
    private dmservices dmServices = new dmservices();
    private boolean ajoutEffectue = false;

    private int loggedeInUserId;

    public void ModifierDm(ActionEvent event) {
        /*User loggedInUser = LoginUserControllers.getLoggedInUser();
        if (loggedInUser != null) {
            int userId = loggedInUser.getIdU();
            System.out.println("Utilisateur connecté avec l'ID: " + userId);

            // Récupérer les données du dossier médical de l'utilisateur connecté
            dossiermedical dm = dmServices.getDossierMedicalByUserId(userId);
            if (dm != null) {
                // Mettre à jour les données du dossier médical
                dm.setPoidsDM(PoidsDM.getText());
                dm.setTailleDM(tailleDM.getText());
                try {
                    dm.setAgeDM(Integer.parseInt(ageDM.getText()));
                } catch (NumberFormatException e) {
                    System.err.println("Erreur : Veuillez entrer un nombre valide pour l'âge.");
                    return; // Sortir de la méthode si l'âge n'est pas un nombre valide
                }

                // Appeler le service pour mettre à jour le dossier médical
                dmServices.update(dm);
                loadDataIntoListView(userId);

                // Afficher un message de succès ou effectuer d'autres actions nécessaires
            } else {
                // Gérer le cas où le dossier médical est null
                System.err.println("Le dossier médical est null pour l'utilisateur avec l'ID: " + userId);
                // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
            }
        } else {
            System.err.println("Utilisateur non connecté.");
            // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
        }*/
        User loggedInUser = LoginUserControllers.getLoggedInUser();
        if (loggedInUser != null) {
            int userId = loggedInUser.getIdU();
            System.out.println("Utilisateur connecté avec l'ID: " + userId);

            // Récupérer les données du dossier médical de l'utilisateur connecté
            dossiermedical dm = dmServices.getDossierMedicalByUserId(userId);
            if (dm != null) {
                // Valider les champs de texte
                String poids = PoidsDM.getText();
                String taille = tailleDM.getText();
                String ageStr = ageDM.getText();

                if (poids.isEmpty() || taille.isEmpty() || ageStr.isEmpty()) {
                    // Afficher un message d'erreur si un champ est vide
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs.");
                    return;
                }
                if (poids.length() > 3 || taille.length() > 3 || ageStr.length() > 3) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Longueur invalide", "Les entrées ne doivent pas dépasser 3 caractères.");
                    return;
                }

                // Valider que l'âge est un nombre valide
                int age;
                try {
                    age = Integer.parseInt(ageStr);
                    if (age < 0 || age > 999) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Âge invalide", "Veuillez entrer un âge valide (entre 0 et 999).");
                    return;
                }

                // Mettre à jour les données du dossier médical
                dm.setPoidsDM(poids);
                dm.setTailleDM(taille);
                dm.setAgeDM(age);

                // Appeler le service pour mettre à jour le dossier médical
                dmServices.update(dm);
                loadDataIntoListView(userId);

                // Afficher un message de succès ou effectuer d'autres actions nécessaires
            } else {
                // Gérer le cas où le dossier médical est null
                System.err.println("Le dossier médical est null pour l'utilisateur avec l'ID: " + userId);
                // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
            }
        } else {
            System.err.println("Utilisateur non connecté.");
            // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
        }
    }


    public void AjoutDm(ActionEvent event) {


          User loggedInUser = LoginUserControllers.getLoggedInUser();

        // Vérifiez si l'utilisateur est connecté
        if (loggedInUser != null) {
            // Récupérez l'ID de l'utilisateur connecté
            int userId = loggedInUser.getIdU();

            // Vérifiez s'il existe déjà un dossier médical pour cet utilisateur
            if (dmServices.dossierMedicalExistsForUser(userId)) {
                // Afficher un message à l'utilisateur pour l'informer qu'un seul ajout est autorisé
                showAlert(Alert.AlertType.INFORMATION, "Information", null, "Un seul dossier médical est autorisé par utilisateur.");
            } else {
                // Si aucun dossier médical n'existe pour cet utilisateur, procéder à l'ajout
                String poids = PoidsDM.getText();
                String taille = tailleDM.getText();
                String ageStr = ageDM.getText();

                if (poids.isEmpty() || taille.isEmpty() || ageStr.isEmpty()) {
                    // Afficher un message d'erreur si un champ est vide
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Champs vides", "Veuillez remplir tous les champs.");
                    return;
                }

                // Valider que les entrées ne dépassent pas 3 caractères
                if (poids.length() > 3 || taille.length() > 3 || ageStr.length() > 3) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Longueur invalide", "Les entrées ne doivent pas dépasser 3 caractères.");
                    return;
                }

                // Valider que l'âge est un nombre valide
                int age;
                try {
                    age = Integer.parseInt(ageStr);
                    if (age < 0 || age > 999) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Âge invalide", "Veuillez entrer un âge valide (entre 0 et 999).");
                    return;
                }

                // Créer un nouveau dossier médical
                dossiermedical dm = new dossiermedical();
                dm.setPoidsDM(poids);
                dm.setTailleDM(taille);
                dm.setAgeDM(age);

                // Définissez l'utilisateur connecté dans le dossier médical
                dm.setuse(loggedInUser);

                // Ajouter le dossier médical à la base de données
                dmServices.add(dm);

                // Mettre à jour la liste des dossiers médicaux affichée dans la ListView
                updateListView();
            }
        } else {
            // Gérer le cas où aucun utilisateur n'est connecté
            System.err.println("Aucun utilisateur connecté.");
            // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
        }
        }



    public void setLoggedInUserId(int userId) {
        this.loggedeInUserId = userId;
    }

    public void SupprimerDm(ActionEvent event) {
        User loggedInUser = LoginUserControllers.getLoggedInUser();

        if (loggedInUser != null) {
            int userId = loggedInUser.getIdU();
            System.out.println("Utilisateur connecté avec l'ID: " + userId);

            // Récupérer les données du dossier médical de l'utilisateur connecté
            dossiermedical dm = dmServices.getDossierMedicalByUserId(userId);
            if (dm != null) {
                // Appeler le service pour supprimer le dossier médical
                dmServices.deleteById(dm.getIdDM());
                System.out.println(dm.getIdDM());

                // Rafraîchir la ListView pour refléter la suppression
                loadDataIntoListView(userId);

                // Fermer la fenêtre actuelle
                ((Node) (event.getSource())).getScene().getWindow().hide();

                // Afficher un message de succès ou effectuer d'autres actions nécessaires
            } else {
                // Afficher un message d'erreur si le dossier médical est null
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le dossier médical est null pour l'utilisateur avec l'ID: " + userId);
            }
        } else {
            // Afficher un message d'erreur si l'utilisateur n'est pas connecté
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Utilisateur non connecté.");
        }
    }

    private void updateListView() {
        listDm.getItems().clear();

        // Récupérer l'utilisateur connecté
        User loggedInUser = LoginUserControllers.getLoggedInUser();
        if (loggedInUser != null) {
            int userId = loggedInUser.getIdU();
            System.out.println("Utilisateur connecté avec l'ID: " + userId);

            // Récupérer le dossier médical de l'utilisateur connecté
            dossiermedical dm = dmServices.getDossierMedicalByUserId(userId);
            if (dm != null) {
                // Construire une chaîne contenant les informations de taille, d'âge et de poids
                String medicalInfo = "Taille: " + dm.getTailleDM() + ", Age: " + dm.getAgeDM() + ", Poids: " + dm.getPoidsDM();
                // Ajouter les informations du dossier médical à la ListView
                listDm.getItems().add(medicalInfo);
            } else {
                // Gérer le cas où le dossier médical est null
                System.err.println("Le dossier médical est null pour l'utilisateur avec l'ID: " + userId);
                // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
            }
        } else {
            System.err.println("Utilisateur non connecté.");
        }
    }

    public void initialize() {
        // Récupérer l'utilisateur connecté
        User loggedInUser = LoginUserControllers.getLoggedInUser();
        if (loggedInUser != null) {
            int userId = loggedInUser.getIdU();
            System.out.println("Utilisateur connecté avec l'ID: " + userId);

            // Récupérer les données du dossier médical de l'utilisateur connecté
            dossiermedical dm = dmServices.getDossierMedicalByUserId(userId);
            if (dm != null) {
                // Afficher les données dans les champs de texte
                PoidsDM.setText(dm.getPoidsDM());
                tailleDM.setText(dm.getTailleDM());
                ageDM.setText(String.valueOf(dm.getAgeDM()));

                // Afficher les données dans la ListView
                listDm.getItems().add("Poids: " + dm.getPoidsDM() + ", Taille: " + dm.getTailleDM() + ", Age: " + dm.getAgeDM());
            } else {
                System.err.println("Le dossier médical est null pour l'utilisateur avec l'ID: " + userId);
            }
        } else {
            System.err.println("Utilisateur non connecté.");
            // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
        }

        // Ajouter un écouteur d'événements à la ListView pour mettre à jour les champs texte lorsqu'un élément est sélectionné
        listDm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] parts = newValue.split(", ");
                String poids = parts[0].split(": ")[1];
                String taille = parts[1].split(": ")[1];
                String age = parts[2].split(": ")[1];

                // Mettre à jour les champs texte avec les valeurs du dossier médical sélectionné
                PoidsDM.setText(poids);
                tailleDM.setText(taille);
                ageDM.setText(age);
            }
        });
    }
    public void loadDataIntoListView(int userId) {
        // Charger les données du dossier médical pour l'utilisateur spécifié dans votre ListView
        listDm.getItems().clear(); // Effacez d'abord les anciennes données
        // Ensuite, ajoutez les nouvelles données spécifiques à l'utilisateur avec l'ID userId
        dossiermedical dm = dmServices.getDossierMedicalByUserId(userId);
        if (dm != null) {
            // Construire une chaîne contenant les informations de taille, d'âge et de poids
            String medicalInfo = "Taille: " + dm.getTailleDM() + ", Age: " + dm.getAgeDM() + ", Poids: " + dm.getPoidsDM();
            // Ajouter les informations du dossier médical à la ListView
            listDm.getItems().add(medicalInfo);
        } else {
            // Gérer le cas où le dossier médical est null
            System.err.println("Le dossier médical est null pour l'utilisateur avec l'ID: " + userId);
            // Afficher un message d'erreur ou effectuer d'autres actions nécessaires
        }
    }private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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

