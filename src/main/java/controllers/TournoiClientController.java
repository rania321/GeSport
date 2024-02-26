package controllers;

import entities.Tournoi;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.TournoiService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TournoiClientController {

    private List<Tournoi> Tlist;
    private final TournoiService ts = new TournoiService();
    private final Tournoi tournoi = new Tournoi();

    @FXML
    private TableView<Tournoi> tournoiTable;

    @FXML
    private TableColumn<Tournoi, Integer> idColumn;

    @FXML
    private TableColumn<Tournoi, String> nomColumn;

    @FXML
    private TableColumn<Tournoi, Date> dateDebutColumn;

    @FXML
    private TableColumn<Tournoi, Date> dateFinColumn;

    @FXML
    private TableColumn<Tournoi, String> descriptionColumn;

    @FXML
    private TableColumn<Tournoi, String> statutColumn;




    public void showTournoi() throws IOException {
        Tlist = ts.readAll();
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdT()).asObject());
        nomColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nomT"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateDebutT"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateFinT"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("DescriT"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("statutT"));

        if (tournoiTable != null && tournoiTable instanceof TableView<Tournoi>) {
            ((TableView<Tournoi>) tournoiTable).setItems(FXCollections.observableArrayList(Tlist));
        }

    }

    @FXML
    void inscrire(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipeClient.fxml"));
        try {
            Parent root = loader.load();
            EquipeClientController ec = loader.getController();
            ec.showEquipe();
            Scene scene = new Scene(root, 1300, 800);
            Stage primaryStage = (Stage) tournoiTable.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }



}