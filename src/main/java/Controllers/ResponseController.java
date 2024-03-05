package controllers;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import entities.Response;
import Services.ServiceResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ResponseController {
    @FXML
    private TableView<Response> responseTableView;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<Response, Integer> idColumn;

    @FXML
    private TableColumn<Response, Integer> reclamationIdColumn;

    @FXML
    private TableColumn<Response, Date> dateColumn;

    @FXML
    private TableColumn<Response, String> contentColumn;

    @FXML
    private TextField reclamationIdField;

    @FXML
    private TextField contentField;

    private int selectedResponseId = 0;

    private ServiceResponse serviceResponse = new ServiceResponse();
    @FXML


    // Method to set the reclamation ID
    public void setReclamationId(int reclamationId) {
        reclamationIdField.setText(String.valueOf(reclamationId));
    }

    @FXML
    public void initialize() {
        showResponses();
    }


    public void showResponses() {
        ArrayList<Response> responses = serviceResponse.readAll();
        ObservableList<Response> responseObservableList = FXCollections.observableList(responses);
        responseTableView.setItems(responseObservableList);

      //  idColumn.setCellValueFactory(new PropertyValueFactory<>("idRep"));
        reclamationIdColumn.setCellValueFactory(new PropertyValueFactory<>("idRec"));
        dateColumn.setCellValueFactory(cellData -> {
            Response response = cellData.getValue();
            Timestamp timestamp = response.getDateRep();
            Date date = new Date(timestamp.getTime());
            return new SimpleObjectProperty<>(date);
        });
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("contenuRep"));
    }

    @FXML
    void addResponse(ActionEvent event) {
        int reclamationId = Integer.parseInt(reclamationIdField.getText());
        String content = contentField.getText();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

        Response newResponse = new Response(0, reclamationId, currentTimestamp, content);
        serviceResponse.add(newResponse);
        showResponses();
        showNotification();
    }

    @FXML
    void deleteResponse(ActionEvent event) {
        Response selectedResponse = responseTableView.getSelectionModel().getSelectedItem();
        if (selectedResponse != null) {
            serviceResponse.delete(selectedResponse);
            showResponses();
        } else {
            showAlert("Please select a response to delete.");
        }
    }

    @FXML
    void updateResponse(ActionEvent event) {
        String content = contentField.getText();
        Response response = responseTableView.getSelectionModel().getSelectedItem();
        if (response != null) {
            response.setContenuRep(content);
            serviceResponse.update(response);
            showResponses();
        } else {
            showAlert("Please select a response to update.");
        }
    }

    @FXML
    void getResponseData(javafx.scene.input.MouseEvent event) {
        Response response = responseTableView.getSelectionModel().getSelectedItem();
        if (response != null) {
            selectedResponseId = response.getIdRep();
            reclamationIdField.setText(String.valueOf(response.getIdRec()));
            contentField.setText(response.getContenuRep());
        } else {
            selectedResponseId = 0;
            reclamationIdField.clear();
            contentField.clear();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void toReservation(ActionEvent actionEvent) {
    }
    private void showNotification() {
        try {
            Image image = new Image("img/notification.png");

            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Response added successfully");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reclamation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionReclamationAdmin.fxml"));
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
