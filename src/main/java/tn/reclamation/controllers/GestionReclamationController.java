package tn.Gesport.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.Gesport.models.Reclamation;
import tn.Gesport.services.ServiceReclamation;

import java.util.ArrayList;
import java.util.Date;

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
    private TableColumn<Reclamation, Integer> col_id_user;

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
    private TextField tf_id_user;

    @FXML
    private TextField tf_stat;
    int id = 0;

    ServiceReclamation sr = new ServiceReclamation();
    // Reclamation reclamation = new Reclamation();

    @FXML
    public void initialize() {
        showReclamation();
    }

    public void showReclamation(){
        ArrayList<Reclamation> personnes = sr.getAll();
        ObservableList<Reclamation> personneObservableList = FXCollections.observableList(personnes);
        TableViewA.setItems(personneObservableList);

        col_id_rec.setCellValueFactory(new PropertyValueFactory<>("idRec"));
        col_id_user.setCellValueFactory(new PropertyValueFactory<>("idU"));
        col_descri.setCellValueFactory(new PropertyValueFactory<>("descriRec"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("DateRec"));
        col_categ.setCellValueFactory(new PropertyValueFactory<>("CategorieRec"));
        col_statut.setCellValueFactory(new PropertyValueFactory<>("StatutRec"));
    }
    @FXML
    void addReclamation(ActionEvent event) {
        sr.add(new Reclamation(1, Integer.parseInt(tf_id_user.getText()), tf_descri.getText(), java.sql.Date.valueOf(tf_date.getValue()), tf_categ.getText(), tf_stat.getText()));
        showReclamation();
    }

    @FXML
    void backMenu(ActionEvent event) {

    }

    @FXML
    void btnAddImgA_clicked(ActionEvent event) {

    }

    @FXML
    void deleteReclamation(ActionEvent event) {
        sr.delete(sr.getById(id));
        showReclamation();
    }

    @FXML
    void toReservation(ActionEvent event) {

    }

    @FXML
    void updateReclamation(ActionEvent event) {
        Reclamation reclamation = sr.getById(id);
        reclamation.setIdU(Integer.parseInt(tf_id_user.getText()));
        reclamation.setDescriRec(tf_descri.getText());
        reclamation.setDateRec(java.sql.Date.valueOf(tf_date.getValue()));
        reclamation.setCategorieRec(tf_categ.getText());
        reclamation.setStatutRec(tf_stat.getText());
        sr.update(reclamation);
        showReclamation();
    }

    @FXML
    void getRecData(MouseEvent event) {
        Reclamation reclamation = TableViewA.getSelectionModel().getSelectedItem();
        id = reclamation.getIdRec();
        tf_id_user.setText(String.valueOf(reclamation.getIdU()));
        tf_descri.setText(reclamation.getDescriRec());
        tf_categ.setText(reclamation.getCategorieRec());
        tf_stat.setText(reclamation.getStatutRec());
    }
}
