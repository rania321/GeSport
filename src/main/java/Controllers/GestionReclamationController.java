package controllers;

import Services.ServiceResponse;
import entities.Response;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import entities.Reclamation;
import Services.ServiceReclamation;
import API.ExportToExcel;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// Inside your controller class


public class GestionReclamationController {
    @FXML
    private TableView<Reclamation> TableViewA;

    @FXML
    private Button button_add;

    @FXML
    private Button button_browse;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_update;

    @FXML
    private TableColumn<Reclamation, String> col_categ;

    @FXML
    private TableColumn<Reclamation, Date> col_date;

    @FXML
    private TableColumn<Reclamation, String> col_descri;


    @FXML
    private TableColumn<Reclamation, String> col_name;

    @FXML
    private TableColumn<Reclamation, String> col_last_name;

    @FXML
    private TableColumn<Reclamation, String> col_statut;

    @FXML
    private ImageView imageViewA;

    @FXML
    private TextField tf_categ;

    @FXML
    private DatePicker tf_date;

    @FXML
    private TextField tf_descri;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_last_name;
    @FXML
    private TextField tf_search;
    @FXML
    private ComboBox<String> tf_stat;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    int id = 0;

    @FXML
    private TableView<Map.Entry<String, String>>TableViewR;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> contenuRes;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> contenuRec;

    ServiceReclamation sr = new ServiceReclamation();

    ServiceResponse ss = new ServiceResponse();

    HashMap<String, String> RecRes = new HashMap<>();
    @FXML
    public void initialize() {
       //tf_stat.setValue("non traitee");
        showReclamation();
        tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
            searchReclamation();
        });
        showReclamation();

        showResponse();

    }
    @FXML
    void chatbot(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatBot.fxml"));
        Parent weatherRoot = loader.load();

        // Créer une nouvelle scène avec l'interface météo
        Scene weatherScene = new Scene(weatherRoot);

        // Créer une nouvelle fenêtre pour l'interface météo
        Stage weatherStage = new Stage();
        weatherStage.setScene(weatherScene);
        weatherStage.setTitle("Chatbot");

        // Afficher la nouvelle fenêtre
        weatherStage.show();
    }
    public void showReclamation() {
        ArrayList<Reclamation> reclamations = sr.readAll();
        TableViewA.setItems(FXCollections.observableArrayList(reclamations));

        col_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        col_last_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        col_descri.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescriRec()));
        col_date.setCellValueFactory(cellData -> {
            Reclamation reclamation = cellData.getValue();
            Timestamp timestamp = reclamation.getDateRec();
            Date date = new Date(timestamp.getTime());
            return new SimpleObjectProperty<>(date);
        });
        col_categ.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorieRec()));
        col_statut.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatutRec()));
    }

    @FXML
    void addReclamation(ActionEvent event) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

        Reclamation newReclamation = new Reclamation(0, tf_name.getText(), tf_last_name.getText(), tf_descri.getText(), currentTimestamp, tf_categ.getText(), "non traitee");
        sr.add(newReclamation);

        showReclamation();
    }

    @FXML
    void deleteReclamation(ActionEvent event) {
        Reclamation selectedReclamation = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            sr.delete(selectedReclamation);
            showReclamation();
        }
    }

    @FXML
    void updateReclamation(ActionEvent event) {
        Reclamation selectedReclamation = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            selectedReclamation.setName(tf_name.getText());
            selectedReclamation.setLastName(tf_last_name.getText());
            selectedReclamation.setDescriRec(tf_descri.getText());
            selectedReclamation.setDateRec(Timestamp.valueOf(LocalDateTime.now()));
            selectedReclamation.setCategorieRec(tf_categ.getText());
            selectedReclamation.setStatutRec("non traitee");
            sr.update(selectedReclamation);
            showReclamation();
        }
    }
    @FXML
    void updateReclamationAdmin(ActionEvent event) {
        Reclamation selectedReclamation = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            selectedReclamation.setName(tf_name.getText());
            selectedReclamation.setLastName(tf_last_name.getText());
            selectedReclamation.setDescriRec(tf_descri.getText());
            selectedReclamation.setDateRec(Timestamp.valueOf(LocalDateTime.now()));
            selectedReclamation.setCategorieRec(tf_categ.getText());

            // Retrieve the selected value from the ComboBox tf_stat
            String selectedStatus = tf_stat.getValue();

            // Set the selected value as the status of the Reclamation object
            selectedReclamation.setStatutRec(selectedStatus);

            sr.update(selectedReclamation);
            showReclamation();
        }
    }





    @FXML
    void getRecData(MouseEvent event) {
        Reclamation selectedReclamation = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            tf_name.setText(selectedReclamation.getName());
            tf_last_name.setText(selectedReclamation.getLastName());
            tf_descri.setText(selectedReclamation.getDescriRec());
            tf_categ.setText(selectedReclamation.getCategorieRec());
        }
    }

    @FXML
    void addResponseToReclamation(ActionEvent event) {
        Reclamation selectedReclamation = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            int reclamationId = selectedReclamation.getIdRec();
            // Navigate to the response page and pass the reclamation ID
            // Implement this method according to your navigation logic
            navigateToResponsePage(reclamationId,event);
        }

    }


    private void navigateToResponsePage(int reclamationId, ActionEvent event) {
        try {
            // Load the FXML file for the response page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionResponse.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller of the response page
            ResponseController responseController = fxmlLoader.getController();

            // Pass the reclamation ID to the response controller
            responseController.setReclamationId(reclamationId);

            // Show the response page
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Réservations");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the FXML file fails
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
        stage.setTitle("Réservations");

        // Afficher la nouvelle fenêtre
        stage.show();    }
    @FXML
    void btnAddImgA_clicked(ActionEvent event) {
        // Implement your logic for handling the button click event here
    }
    @FXML
    void toReservation(ActionEvent event) {
        // Implement your logic for navigating to the reservation here
    }


    @FXML
    void searchReclamation() {
        // Get the search query from the TextField
        String searchText = tf_search.getText().toLowerCase();

        // If the search query is empty, show all reclamations
        if (searchText.isEmpty()) {
            showReclamation();
            return;
        }

        // Filter the list of reclamations based on the search query
        ArrayList<Reclamation> filteredList = new ArrayList<>();
        for (Reclamation reclamation : sr.readAll()) {
            if (reclamation.getName().toLowerCase().contains(searchText)
                    || reclamation.getLastName().toLowerCase().contains(searchText)
                    || reclamation.getCategorieRec().toLowerCase().contains(searchText)
                    || reclamation.getDescriRec().toLowerCase().contains(searchText)
                    || reclamation.getStatutRec().toLowerCase().contains(searchText)
                    || reclamation.getDateRec().toString().toLowerCase().contains(searchText)) {
                filteredList.add(reclamation);
            }
        }

        // Update the TableView with the filtered list
        TableViewA.setItems(FXCollections.observableArrayList(filteredList));
    }


    public void navigateToAdminPage(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the response page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionReclamationAdmin.fxml"));
            Parent root = fxmlLoader.load();




            // Show the response page
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the FXML file fails
        }
    }
    @FXML
    void exporttoexcel(ActionEvent event) {
        try {
            String filename="Reclamation.xlsx";
            File file=new File("C:\\Users\\Rania\\Desktop\\integ\\src\\main\\resources\\excel\\"+filename);
            ExportToExcel.exportToExcel(sr.readAll(), file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
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

    public void showResponse() {
        // Récupérer toutes les réponses
        ArrayList<Response> responses = ss.readAll();

        // Remplir la map RecRes avec les clés et les valeurs
        for (Response response : responses) {
            int id = response.getIdRec();
            String rec = sr.getRecFromIdRec(id);
            if (rec != null) {
                RecRes.put(rec, response.getContenuRep());
            }
        }
        // Convertir la map RecRes en une liste d'entrées (clé, valeur)
        ObservableList<Map.Entry<String, String>> entryList = FXCollections.observableArrayList(RecRes.entrySet());

        // Lier les colonnes du TableView aux propriétés de Map.Entry
       // contenuRec.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
      //  contenuRes.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        // Mettre à jour les données du TableView avec la liste d'entrées
        //TableViewR.setItems(entryList);
    }


}
