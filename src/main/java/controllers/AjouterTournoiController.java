package controllers;

import entities.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import service.TournoiService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterTournoiController {

    private final TournoiService ts = new TournoiService();
    private final Tournoi t = new Tournoi();

    @FXML
    private DatePicker DdebutT;

    @FXML
    private TextField DescriT;

    @FXML
    private DatePicker DfinT;

    @FXML
    private TextField StatutT;

    @FXML
    private TextField nomT;

    @FXML
    void ajouter(ActionEvent event) {

        // Convertir les valeurs des DatePicker en java.util.Date
        Date dateDebut = java.sql.Date.valueOf(DdebutT.getValue());
        Date dateFin = java.sql.Date.valueOf(DfinT.getValue());
       // try {
            ts.add(new Tournoi(nomT.getText(), dateDebut, dateFin, DescriT.getText(), StatutT.getText()));
       // }catch ( RuntimeException e)
       /* {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }*/

    }
}
