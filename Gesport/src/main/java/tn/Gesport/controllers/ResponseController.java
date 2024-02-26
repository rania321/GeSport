package tn.Gesport.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.Gesport.models.Response;
import tn.Gesport.services.ServiceResponse;

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
        ArrayList<Response> responses = serviceResponse.getAll();
        ObservableList<Response> responseObservableList = FXCollections.observableList(responses);
        responseTableView.setItems(responseObservableList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("idRep"));
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

    public void backMenu(ActionEvent actionEvent) {
    }

    public void toReservation(ActionEvent actionEvent) {
    }
}
