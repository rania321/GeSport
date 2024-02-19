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
import javafx.scene.input.MouseEvent;
import service.EquipeService;
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
    void ajouter(ActionEvent event) {

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

    @FXML
    void selection(MouseEvent event) {
        Equipe selectedEquipe = equipeTable.getSelectionModel().getSelectedItem();
        if (selectedEquipe != null) {
            // Remplir les champs avec les informations du tournoi sélectionné
            nomE.setText(selectedEquipe.getNomE());
            idTE.setText(String.valueOf(selectedEquipe.getTournoi().getIdT())); // Convertir l'entier en chaîne de caractères
            idUE.setText(String.valueOf(selectedEquipe.getUser().getIdU()));
            idE.setText(String.valueOf(selectedEquipe.getIdE()));
            statutE.setText(selectedEquipe.getStatutE());


        }
    }
    @FXML
    void supprimer(ActionEvent event)  {
        // Récupérer la ligne sélectionnée dans la TableView
        Equipe equipe = equipeTable.getSelectionModel().getSelectedItem();

        if (equipe != null) {
            // Appeler la méthode de suppression de la ligne
            supprimerLigne(equipe);
        } else {
            // Afficher un message d'erreur si aucune ligne n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
            alert.showAndWait();
        }

    }

    private void supprimerLigne(Equipe equipe) {

        // Suppression de la ligne sélectionnée
        equipeTable.getItems().remove(equipe);
        // Appel à la méthode de suppression dans le service
        es.delete(equipe);


    }
    @FXML
    void modifier(ActionEvent event) {
        // Vérifier si un élément est sélectionné dans la TableView
        Equipe equipeSelectionne = equipeTable.getSelectionModel().getSelectedItem();
        if (equipeSelectionne != null) {
            // Récupérer les valeurs des champs de texte
            String nom = nomE.getText();
            String statut = statutE.getText();
            int IDTE = Integer.parseInt(idTE.getText());
            int IDUE = Integer.parseInt(idUE.getText());

            TournoiService tournoiService = new TournoiService();
            UserService userService = new UserService();

            Tournoi tournoi = tournoiService.readById(IDTE);
            User user = userService.readById(IDUE);


            // Mettre à jour les informations de l equipe sélectionné
            equipeSelectionne.setNomE(nom);
            equipeSelectionne.setTournoi(tournoi);
            equipeSelectionne.setUser(user);
            equipeSelectionne.setStatutE(statut);




            // Mettre à jour la TableView avec les modifications
            equipeTable.refresh();

            //  appel  méthode de mise à jour dans le service
            EquipeService es = new EquipeService();
            es.update(equipeSelectionne);

            // Réinitialiser les champs de texte après la modification
            idE.clear();
            nomE.clear();
            statutE.clear();
            idUE.clear();
            idTE.clear();
        } else {
            // Afficher un message d'erreur si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un élément à modifier.");
            alert.showAndWait();
        }
    }

    }











