package tn.Gesport.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.Gesport.controllers.ResponseController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.Gesport.models.Reclamation;
import tn.Gesport.services.ServiceReclamation;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.time.format.DateTimeFormatter;

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
    private TableColumn<Reclamation, Integer> col_id_rec;

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
    private ComboBox<String> tf_stat;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    int id = 0;

    ServiceReclamation sr = new ServiceReclamation();
    // Reclamation reclamation = new Reclamation();


    @FXML
    public void initialize() {
       //tf_stat.setValue("non traitee");
        showReclamation();
    }

    public void showReclamation() {
        ArrayList<Reclamation> reclamations = sr.getAll();
        TableViewA.setItems(FXCollections.observableArrayList(reclamations));

        col_id_rec.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdRec()));
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
            navigateToResponsePage(reclamationId);
        }
    }


    private void navigateToResponsePage(int reclamationId) {
        try {
            // Load the FXML file for the response page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionResponse.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller of the response page
            ResponseController responseController = fxmlLoader.getController();

            // Pass the reclamation ID to the response controller
            responseController.setReclamationId(reclamationId);

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
    void backMenu(ActionEvent event) {
        // Implement your logic for handling the back menu action here
    }
    @FXML
    void btnAddImgA_clicked(ActionEvent event) {
        // Implement your logic for handling the button click event here
    }
    @FXML
    void toReservation(ActionEvent event) {
        // Implement your logic for navigating to the reservation here
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
}
