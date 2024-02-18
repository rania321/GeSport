package controllers;

import entities.Tournoi;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.TournoiService;

import java.util.Date;
import java.util.List;

public class AfficherTournoiController {
    private List<Tournoi> Tlist;
    private final TournoiService ts = new TournoiService();


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

    public void showTournoi() {
        Tlist = ts.readAll();
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdT()).asObject());
        nomColumn.setCellValueFactory(new PropertyValueFactory<Tournoi,String>("nomT"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi,Date>("DateDebutT"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<Tournoi,Date>("DateFinT"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tournoi,String>("DescriT"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi,String>("statutT"));

        if (tournoiTable != null && tournoiTable instanceof TableView<Tournoi>){
            ((TableView<Tournoi>) tournoiTable).setItems(FXCollections.observableArrayList(Tlist));
        }

    }


    }


