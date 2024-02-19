package controllers;

import entities.Equipe;
import entities.Tournoi;
import entities.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.EquipeService;
import service.TournoiService;
import service.UserService;

import java.io.IOException;
import java.util.List;

public class EquipeClientController {

    private List<Equipe> Elist;
    private final EquipeService es = new EquipeService();
    private final Equipe equipe = new Equipe();
    @FXML
    private TableView<Equipe> equipeTable;
    @FXML
    private TableColumn<Equipe, Integer> idColumn;

    @FXML
    private TableColumn<Equipe, Integer> idTColumn;

    @FXML
    private TableColumn<Equipe, Integer> idUColumn;

    @FXML
    private TableColumn<Equipe, String> nomColumn;

    @FXML
    private TableColumn<Equipe, String> statutColumn;


    @FXML
    private TextField idE;

    @FXML
    private TextField idTE;

    @FXML
    private TextField idUE;

    @FXML
    private TextField nomE;

    @FXML
    private TextField statutE;
    public void showEquipe() throws IOException {
        Elist = es.readAll();
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdE()).asObject());
        nomColumn.setCellValueFactory(new PropertyValueFactory<Equipe, String>("nomE"));
        idTColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTournoi().getIdT()).asObject());
        idUColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUser().getIdU()).asObject());
        statutColumn.setCellValueFactory(new PropertyValueFactory<Equipe, String>("statutE"));

        if (equipeTable != null && equipeTable instanceof TableView<Equipe>) {
            ((TableView<Equipe>) equipeTable).setItems(FXCollections.observableArrayList(Elist));
        }

    }
    @FXML
    public void ajouter(ActionEvent actionEvent) {
            String nom = nomE.getText();
            int IDTE = Integer.parseInt(idTE.getText());
            int IDUE = Integer.parseInt(idUE.getText());
            String Statut = statutE.getText();

            // Création des instances
            EquipeService equipeService = new EquipeService();
            TournoiService tournoiService = new TournoiService();
            UserService userservice = new UserService();

            // Lecture des objets Tournoi et User en fonction de leurs identifiants
            Tournoi tournoi = tournoiService.readById(IDTE);
            User user = userservice.readById(IDUE);

            // Création de l'équipe avec les objets Tournoi et User récupérés
            Equipe e = new Equipe(nom, tournoi, user, Statut);

            // Ajout de l'équipe
            equipeService.add(e);

            // Mettre à jour la liste des equipes depuis le service
            List<Equipe> equipes = equipeService.readAll();

            // Mettre à jour les données de la TableView
            equipeTable.getItems().setAll(equipes);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'equipe a été ajoutée avec succès !");
            alert.showAndWait();
        }
    }

