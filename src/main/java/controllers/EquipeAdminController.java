package controllers;

import entities.Equipe;
import entities.Tournoi;
import entities.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.EquipeService;
import service.JoueurService;
import service.TournoiService;
import service.UserService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EquipeAdminController {
    private List<Equipe> Elist;
    private final EquipeService es = new EquipeService();
    private final JoueurService js= new JoueurService();
    private final Equipe equipe = new Equipe();
    private final TournoiService tournoiService = new TournoiService();

    @FXML
    private TableView<Equipe> equipeTable;

    @FXML
    private TableColumn<Equipe, String> TColumn;

    @FXML
    private TableColumn<Equipe, String> nomUColumn;

    @FXML
    private TableColumn<Equipe, String> nomColumn;

    @FXML
    private TableColumn<Equipe, String> statutColumn;



    @FXML
    private TextField idUE;

    @FXML
    private TextField nomE;

    @FXML
    private TextField statutE;

    @FXML
    private ComboBox<String> nomT;


    public void showEquipe() throws IOException {
        Elist = es.readAll();
        nomColumn.setCellValueFactory(new PropertyValueFactory<Equipe, String>("nomE"));
        TColumn.setCellValueFactory(cellData -> {
            Equipe equipe = cellData.getValue();
            String nomTournoi = equipe.getTournoi().getNomT();
            return new SimpleStringProperty(nomTournoi);
        });
        nomUColumn.setCellValueFactory(cellData -> {
            Equipe equipe = cellData.getValue();
            String nomUser = equipe.getUser().getPrenomU();
            return new SimpleStringProperty(nomUser);
        });
        statutColumn.setCellValueFactory(new PropertyValueFactory<Equipe, String>("statutE"));

        if (equipeTable != null && equipeTable instanceof TableView<Equipe>) {
            ((TableView<Equipe>) equipeTable).setItems(FXCollections.observableArrayList(Elist));
        }

    }

    public void initialize() {
        List<String> tournoiNames = tournoiService.readAllNames();
        ObservableList<String> observableNames = FXCollections.observableArrayList(tournoiNames);
        nomT.setItems(observableNames);}


    @FXML
    void ajouter(ActionEvent event) {

        String nom = nomE.getText();
        // Récupérer l'ID du tournoi sélectionné dans la ComboBox
        String nomTournoi = nomT.getValue();
        int IDTE = tournoiService.getIdByName(nomTournoi);

        int IDUE = Integer.parseInt(idUE.getText());
        String Statut = statutE.getText();

        // Création des instances
        EquipeService equipeService = new EquipeService();
        TournoiService tournoiService = new TournoiService();
        UserService userservice = new UserService();

        // read by id Tournoi et User
        Tournoi tournoi = tournoiService.readById(IDTE);
        User user = userservice.readById(IDUE);

        // Création équipe avec Tournoi et User récupérés
        Equipe e = new Equipe(nom, tournoi, user, Statut);


        equipeService.add(e);

        // maj
        List<Equipe> equipes = equipeService.readAll();

        //maj TableView
        equipeTable.getItems().setAll(equipes);

        // alerte succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'equipe a été ajoutée avec succès !");
        alert.showAndWait();
    }

    @FXML
    void selection(MouseEvent event) {
        Equipe selectedEquipe = equipeTable.getSelectionModel().getSelectedItem();
        if (selectedEquipe != null) {
            // Remplir les champs avec  tournoi sélectionné
            nomE.setText(selectedEquipe.getNomE());
            // nomT.setValue(selectedEquipe.getTournoi().getNomT());
            idUE.setText(String.valueOf(selectedEquipe.getUser().getIdU()));
            statutE.setText(selectedEquipe.getStatutE());


        }
    }
    @FXML
    void supprimer(ActionEvent event)  {
        // Récupérer la ligne sélectionnée
        Equipe equipe = equipeTable.getSelectionModel().getSelectedItem();

        if (equipe != null) {
            // Supprimer les joueurs associés à l'équipe
            js.deleteJoueursByEquipeId(equipe.getIdE());
            supprimerLigne(equipe);
            es.delete(equipe);
        } else {
            // message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
            alert.showAndWait();
        }

    }

    private void supprimerLigne(Equipe equipe) {

        // Supp ligne sélectionnée
        equipeTable.getItems().remove(equipe);

        es.delete(equipe);


    }
    @FXML
    void modifier(ActionEvent event) {
        // Vérifier élément sélectionné
        Equipe equipeSelectionne = equipeTable.getSelectionModel().getSelectedItem();
        if (equipeSelectionne != null) {
            // Récupérer les valeurs
            String nom = nomE.getText();
            String statut = statutE.getText();
            String nomTournoi = nomT.getValue();
            int IDTE = tournoiService.getIdByName(nomTournoi);
            int IDUE = Integer.parseInt(idUE.getText());

            TournoiService tournoiService = new TournoiService();
            UserService userService = new UserService();

            Tournoi tournoi = tournoiService.readById(IDTE);
            User user = userService.readById(IDUE);


            // maj  equipe sélectionné
            equipeSelectionne.setNomE(nom);
            equipeSelectionne.setTournoi(tournoi);
            equipeSelectionne.setUser(user);
            equipeSelectionne.setStatutE(statut);




            // maj TableView
            equipeTable.refresh();

            //  appel update
            EquipeService es = new EquipeService();
            es.update(equipeSelectionne);

            nomE.clear();
            statutE.clear();
            idUE.clear();
            nomT.getSelectionModel().clearSelection();
        } else {
            // message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un élément à modifier.");
            alert.showAndWait();
        }
    }

    public void backMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTournoi.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1300, 800);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");

        // Afficher la nouvelle fenêtre
        stage.show();
    }


    public void toTournoi(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTournoi.fxml"));
        Parent root = loader.load();

        AfficherTournoiController controller = loader.getController();
        controller.showTournoi();

        // Créer une nouvelle scène avec la vue chargée
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Equipes");

        stage.show();
    }
}



