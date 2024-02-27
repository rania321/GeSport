package controllers;




import entities.User;
import Services.UserService;
import entities.role;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherUserController implements Initializable {
    private final UserService us = new UserService();

    @FXML
    private TextField emailU;

    @FXML
    private TableView<User> tableAdd;

    @FXML
    private TextField mdpU;

    @FXML
    private TextField nomU;

    @FXML
    private TextField prenomU;

    @FXML
    private TextField roleU;
    @FXML
    private TableColumn<User, String> EmailU1;
    @FXML
    private TableColumn<User, Integer> idU;
    @FXML
    private TableColumn<User, String> mdpU1;
    @FXML
    private TableColumn<User, String> nomU1;
    @FXML
    private TableColumn<User, String> prenomU1;
    @FXML
    private TableColumn<User, String> roleU1;
    private User utilisateurSelectionne;





    public void setEmailU(String emailU) {
        this.emailU.setText(emailU);
    }



    public void setMdpU(String mdpU) {
        this.mdpU.setText(mdpU);
    }

    public void setNomU(String nomU) {
        this.nomU.setText(nomU);
    }

    public void setPrenomU(String prenomU) {
        this.prenomU.setText(prenomU);
    }

    public void setRoleU(String roleU) {
        this.roleU.setText(roleU);
    }

    public void initialize(URL location, ResourceBundle resources) {
        // Configure cell value factories
        //idU.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIdU()));
        nomU1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomU()));
        prenomU1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenomU()));
        EmailU1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmailU()));
        mdpU1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMdpU()));
        roleU1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRoleU().toString()));

        // Load data into TableView
        loadDataIntoTableView();

        // Configure TableView selection listener
        configureTableSelectionListener();
    }

    private void loadDataIntoTableView() {
        UserService userService = new UserService();

        // Retrieve data from database
        List<User> userList = userService.readall();

        // Create an ObservableList from the retrieved data
        ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);

        // Set the ObservableList as the items of the TableView
        tableAdd.setItems(observableUserList);
    }

    private void configureTableSelectionListener() {
        // Configure the TableView selection listener
        tableAdd.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Update text fields with the selected user's data
                nomU.setText(newSelection.getNomU());
                prenomU.setText(newSelection.getPrenomU());
                emailU.setText(newSelection.getEmailU());
                mdpU.setText(newSelection.getMdpU());
                roleU.setText(newSelection.getRoleU().toString());

                // Set the selected user as the currently selected user
                utilisateurSelectionne = newSelection;
            }
        });
    }


   public void supprimerUtilisateur(ActionEvent event) {
       User utilisateurSelectionne = tableAdd.getSelectionModel().getSelectedItem();

       if (utilisateurSelectionne != null) {
           // Remove the selected user from the TableView
           tableAdd.getItems().remove(utilisateurSelectionne);

           // Remove the selected user from the database
           UserService userService = new UserService();
           userService.deleteById(utilisateurSelectionne.getIdU());

           // Clear the text fields
           clearTextFields();
       } else {
           // Show an error message if no user is selected
           afficherMessageErreur("Veuillez sélectionner un utilisateur à supprimer.");
       }
   }


    public void ModifierUtulisateur(ActionEvent event) {
        if (utilisateurSelectionne != null) {
            // Update the selected user's data with the data from the text fields
            utilisateurSelectionne.setNomU(nomU.getText());
            utilisateurSelectionne.setPrenomU(prenomU.getText());
            utilisateurSelectionne.setEmailU(emailU.getText());
            utilisateurSelectionne.setMdpU(mdpU.getText());
            utilisateurSelectionne.setRoleU(role.valueOf(roleU.getText()));

            // Update the user in the database
            UserService userService = new UserService();
            userService.update(utilisateurSelectionne);

            // Refresh the TableView to reflect the changes
            tableAdd.refresh();
        } else {
            // Show an error message if no user is selected
            afficherMessageErreur("Veuillez sélectionner un utilisateur à modifier.");
        }
    }

    private void clearTextFields() {
        nomU.clear();
        prenomU.clear();
        emailU.clear();
        mdpU.clear();
        roleU.clear();
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void AjouterAd(ActionEvent event) {
        try {
            // Vérifier si les champs sont valides
            if (!isValidInput()) {
                afficherMessageErreur("Saisie invalide. Veuillez remplir tous les champs correctement.");
                return;
            }

            // Vérifier le format de l'adresse e-mail
            if (!isValidEmail(emailU.getText())) {
                afficherMessageErreur("Format de l'adresse e-mail invalide. Veuillez saisir une adresse e-mail valide.");
                return;
            }

            // Vérifier la complexité du mot de passe
            if (!isValidPassword(mdpU.getText())) {
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


            // Ajouter le nouvel utilisateur
            us.add(new User(nomU.getText(), prenomU.getText(), emailU.getText(), mdpU.getText()));
            tableAdd.refresh();

            // Afficher un message de confirmation
            afficherMessageConfirmation("Utilisateur ajouté avec succès.");

            // Nettoyer les champs texte
            clearTextFields();
            loadDataIntoTableView();
        } catch (Exception ex) {
            afficherMessageErreur2("Une erreur s'est produite lors de l'ajout de l'utilisateur.");
            ex.printStackTrace(); // Afficher les détails de l'erreur dans la console pour le débogage
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
    private void afficherMessageErreur2(String message) {
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


