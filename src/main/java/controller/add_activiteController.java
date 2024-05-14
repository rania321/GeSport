package controller;

import entities.Activite;
import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.ActiviteService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class add_activiteController {

    private final ActiviteService as = new ActiviteService();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ImageView imageView;
    private File selectedFile;
    private String imageUrl;

    @FXML
    private TextField DescriA;

    @FXML
    private TextField DispoA;

    @FXML
    private Button btnAddA;

    @FXML
    private TextField nomA;

    @FXML
    private TextField typeA;
    @FXML
    private Button btnAddImgA;
    @FXML
    private TextField pathimgA;

    @FXML
    private ImageView imageViewA;
    @FXML
    private TableColumn<?, ?> CVDescriA;

    @FXML
    private TableColumn<?, ?> CVDispoA;

    @FXML
    private TableColumn<?, ?> CVImgA;

    @FXML
    private TableColumn<?, ?> CVNomA;

    @FXML
    private TableColumn<?, ?> CVTypeA;

    @FXML
    private TableView<Activite> TableViewA;

    @FXML
    private Button btnUpdateA;

    @FXML
    private Button btnDeleteA;

    @FXML
    private ComboBox<String> CBdispoA;

    String uploadedPhotoName ;

    private List<Activite> ActiviteList = new ArrayList<>();
    @FXML
    private TextField searchTF;

    public void initialize() throws IOException {

        // Initialiser le ComboBox avec des données
        ObservableList<String> options = FXCollections.observableArrayList(
                "Disponible",
                "Non disponible"
        );
        CBdispoA.setItems(options);

       TableViewA.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifie si c'est un simple clic
                Activite selectedActivite = TableViewA.getSelectionModel().getSelectedItem();
                if (selectedActivite != null) {
                    // Afficher les informations de la séance sélectionnée dans le formulaire
                    displayActiviteInfo(selectedActivite);
                }
            }
        });
        ShowActivite();
        // Ajoutez un écouteur sur le TextField de recherche pour gérer la recherche dynamique
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchActivite(newValue); // Appel de la méthode de recherche avec le nouveau texte
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    private void searchActivite(String searchText) throws IOException {
        /*List<Activite> searchResult = new ArrayList<>();
        for (Activite activite : ActiviteList) {
            if (activite.getTypeA().toLowerCase().contains(searchText.toLowerCase())) {
                searchResult.add(activite);
            }
        }
        // Mettre à jour la TableView avec les résultats de la recherche
        TableViewA.setItems(FXCollections.observableArrayList(searchResult));*/
        List<Activite> searchResult = ActiviteList.stream()
                .filter(activite ->
                        activite.getTypeA().toLowerCase().contains(searchText.toLowerCase())
                )
                .collect(Collectors.toList());

        // Mettre à jour la TableView avec les résultats de la recherche
        TableViewA.setItems(FXCollections.observableArrayList(searchResult));
    }


    private void displayActiviteInfo(Activite a) {
        nomA.setText(a.getNomA());
        typeA.setText(a.getTypeA());
        CBdispoA.setValue(a.getDispoA());
        DescriA.setText(a.getDescriA());
        pathimgA.setText(a.getImageA());
        //nomid.setValue(a.getNom());
    }

    private Activite activite;
    @FXML
    void add_activite(ActionEvent event) throws IOException {
        String nom = nomA.getText();
        String type = typeA.getText();
        String dispo = CBdispoA.getValue();
        String descri = DescriA.getText();
        // Vérification du contrôle de saisie
        if (nom.isEmpty() || type.isEmpty() || dispo == null || descri.isEmpty()) {
            // Afficher un message d'erreur si les champs obligatoires ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return; // Ne pas poursuivre si les champs obligatoires ne sont pas remplis
        }
        if (selectedFile == null) {
            // Afficher un message d'erreur si aucun fichier image n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de fichier image");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un fichier image.");
            alert.showAndWait();
            return;
        }
        if (actList.stream().anyMatch(activite -> activite.getNomA().equals(nom))) {
            // Activité avec le même nom existe déjà, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'ajout");
            alert.setHeaderText(null);
            alert.setContentText("Une activité avec le même nom existe déjà.");
            alert.showAndWait();
            return;
        }
        as.add(new Activite(nom,type,dispo,descri,selectedFile.getName()));
        // Afficher un message de succès
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Message d'information");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Ajouté avec succès !");
        successAlert.showAndWait();

        ShowActivite();
    }
    @FXML
    void btnAddImgA_clicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");

        fileChooser.getExtensionFilters().add(extFilter);

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {

                // Enregistrez l'URL de l'image dans le champ de texte pathimageid
                pathimgA.setText(selectedFile.getName());

                // Chargez l'image depuis le fichier sélectionné
                Image image = new Image(selectedFile.toURI().toString());
                // Display additional information for debugging
                System.out.println("Selected file path: " + selectedFile.getAbsolutePath());

                // Affichez l'image dans l'ImageView
                imageViewA.setImage(image);

                String uniqueFileName = selectedFile.getName();

                File destDir = new File("C:\\xampp\\htdocs\\images");

                File destFile = new File(destDir, uniqueFileName);

                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                this.imageUrl = uniqueFileName;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upload Successful");
                alert.setHeaderText(null);
                alert.setContentText("Activity picture uploaded successfully.");
                alert.showAndWait();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Could not upload the file: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private List<Activite> actList;

    public void ShowActivite() throws IOException{

        actList = as.readAll();
        ActiviteList = new ArrayList<>(actList);

        //ticket_tv_id.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));
        CVNomA.setCellValueFactory(new PropertyValueFactory<>("NomA"));
        CVTypeA.setCellValueFactory(new PropertyValueFactory<>("TypeA"));
        CVDispoA.setCellValueFactory(new PropertyValueFactory<>("DispoA"));
        CVDescriA.setCellValueFactory(new PropertyValueFactory<>("DescriA"));
        CVImgA.setCellValueFactory(new PropertyValueFactory<>("imageA"));

        if (TableViewA != null && TableViewA instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Activite>) TableViewA).setItems(FXCollections.observableArrayList(actList));
        }

    }

    @FXML
    void update_activite(ActionEvent event) throws IOException {
        // Récupérer les informations modifiées depuis le formulaire
        String nom = nomA.getText();
        String type = typeA.getText();
        String dispo = CBdispoA.getValue();
        String descri = DescriA.getText();
        String imageUrl = pathimgA.getText();

        // Mettre à jour la séance dans la base de données
        Activite selectedActivite = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedActivite != null) {
            selectedActivite.setNomA(nom);
            selectedActivite.setTypeA(type);
            selectedActivite.setDispoA(dispo);
            selectedActivite.setDescriA(descri);
            selectedActivite.setImageA(imageUrl);
            // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de modification");
            alert.setHeaderText("Modifier l'activité");
            alert.setContentText("Êtes-vous sûr de vouloir modifier l'activité sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();
            // Si l'utilisateur confirme la suppression, procéder
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Mettre à jour la séance dans la base de données
                as.update(selectedActivite);
                // Rafraîchir l'affichage des séances dans la TableView
                ShowActivite();
            }
        }
        else {
            // Afficher un message si aucune activité n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune activité sélectionnée");
            alert.setHeaderText("Aucune activité sélectionnée");
            alert.setContentText("Veuillez sélectionner une activité à modifier.");
            alert.showAndWait();
        }
    }

    @FXML
    void delete_activite(ActionEvent event) throws IOException {
        // Récupérer l'activité sélectionnée dans la TableView
        Activite selectedActivite = TableViewA.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (selectedActivite != null) {
            // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'activité");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer l'activité sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();

            // Si l'utilisateur confirme la suppression, procéder
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'activité de la base de données
                as.delete(selectedActivite);

                // Rafraîchir l'affichage des activités dans la TableView
                ShowActivite();
            }
        } else {
            // Afficher un message si aucune activité n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune activité sélectionnée");
            alert.setHeaderText("Aucune activité sélectionnée");
            alert.setContentText("Veuillez sélectionner une activité à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void backMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void toReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show_reservationBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
    @FXML
    void toStatistique(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistiquesAR.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

}
