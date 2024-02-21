package controller;

import entities.Activite;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    }

    private void displayActiviteInfo(Activite a) {
        nomA.setText(a.getNomA());
        typeA.setText(a.getTypeA());
        CBdispoA.setValue(a.getDispoA());
        DescriA.setText(a.getDescriA());
        pathimgA.setText(a.getImageA());
        //nomid.setValue(a.getNom());
    }

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
        as.add(new Activite(nom,type,dispo,descri,selectedFile.getName()));
        // Afficher un message de succès
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Message d'information");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Ajouté avec succès !");
        successAlert.showAndWait();

        ShowActivite();
        if(selectedFile!=null)  {


           /* imageUrl="http://localhost/images/"+selectedFile.getName();
            String phpUrl = "http://localhost/images/upload.php";


            // Read the image file data
            byte[] imageData = Files.readAllBytes(selectedFile.toPath());

            // Create the boundary string for the multipart request
            String boundary = "---------------------------12345";
            // Open the connection to the PHP script
            URL url = new URL(phpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // Write the image file data to the output stream of the connection
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + selectedFile.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Type: image/jpeg\r\n\r\n").getBytes());
            outputStream.write(imageData);
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.flush();
            outputStream.close();

            // Read the response from the PHP script
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            as.add(new Activite(nomA.getText(),typeA.getText(),DispoA.getText(),DescriA.getText()));
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();*/
        }
    }
    @FXML
    void btnAddImgA_clicked(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(selectedFile!=null) {
            // Enregistrez l'URL de l'image dans le champ de texte pathimageid
            pathimgA.setText(selectedFile.toURI().toString());

            // Chargez l'image depuis le fichier sélectionné
            Image image = new Image(selectedFile.toURI().toString());

            // Affichez l'image dans l'ImageView
            imageViewA.setImage(image);
        }
    }

    private List<Activite> actList;

    public void ShowActivite() throws IOException{

        actList = as.readAll();

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


}
