package controllers;

import entities.Equipe;
import entities.Joueur;
import entities.Tournoi;
import entities.User;
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
import javafx.stage.Stage;
import service.EquipeService;
import service.JoueurService;
import service.TournoiService;
import service.UserService;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EquipeClientController {

    private List<Equipe> Elist;
    private final EquipeService es = new EquipeService();


    private final TournoiService tournoiService = new TournoiService();
    @FXML
    private TableView<Equipe> equipeTable;
    List<Joueur> listeDesJoueurs = new ArrayList<>();


    @FXML
    private TableColumn<Equipe, String> TColumn;

    @FXML
    private TableColumn<Equipe, String> nomUColumn;

    @FXML
    private TableColumn<Equipe, String> nomColumn;

    @FXML
    private TableColumn<Equipe, String> statutColumn;


    @FXML
    private TableView<Joueur> joueurTable;

    @FXML
    private TableColumn<Joueur, String> nomJoueurColumn;

    @FXML
    private TextField nomE;

    @FXML
    private ComboBox<String> nomT;
    @FXML
    private TextField joueur;

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
        nomT.setItems(observableNames);

        // Initialisation de la TableView des joueurs
        nomJoueurColumn.setCellValueFactory(new PropertyValueFactory<>("joueur"));


    }

    @FXML
    public void ajouter(ActionEvent actionEvent) {
        String nom = nomE.getText();
        // Récupérer l'ID du tournoi sélectionné dans la ComboBox
        String nomTournoi = nomT.getValue();
        int IDTE = tournoiService.getIdByName(nomTournoi);


        String Statut = "inscrite";

        // Création instances
        EquipeService equipeService = new EquipeService();
        TournoiService tournoiService = new TournoiService();
        UserService userservice = new UserService();

        // read by id Tournoi et User
        Tournoi tournoi = tournoiService.readById(IDTE);
        User user = userservice.readById(1);

        // Création équipe avec Tournoi et User récupérés
        Equipe e = new Equipe(nom, tournoi, user, Statut);

        equipeService.add(e);

        // Mise à jour de la liste Elist
        Elist = es.readAll();

        // Mise à jour de la TableView
        equipeTable.getItems().setAll(Elist);

        // Alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'équipe a été ajoutée avec succès !");
        alert.showAndWait();

        // Ajouter un joueur à la dernière équipe ajoutée
        ajouterJ(null);
    }


    public void ajouterJ(ActionEvent actionEvent) {
        // Récupérer la dernière équipe ajoutée à la liste des équipes
        Equipe derniereEquipe = Elist.get(Elist.size() - 1);


        // Récupérer le texte saisi par l'utilisateur à partir du champ de texte joueurTextField
        String nomJoueur = joueur.getText();
        // Si le nom du joueur n'est pas vide
        if (!nomJoueur.isEmpty()) {
            // Créer un nouvel objet Joueur avec le nom récupéré et l'équipe correspondante
            Joueur nouveauJoueur = new Joueur(nomJoueur, derniereEquipe);

            // Ajouter le nouvel objet Joueur à la liste des joueurs
            listeDesJoueurs.add(nouveauJoueur);
            // Mettre à jour la TableView des joueurs
            joueurTable.getItems().setAll(listeDesJoueurs);
            joueur.clear();

            JoueurService joueurService=new JoueurService();
            // Ajouter le nouvel objet Joueur à la liste des joueurs
            joueurService.add(nouveauJoueur);
            joueurService.supprimerJoueursVides();
        }



    }


    public void supprimerJ(ActionEvent actionEvent) {
            Joueur joueur = joueurTable.getSelectionModel().getSelectedItem();
            if (joueur != null) {
                JoueurService joueurService = new JoueurService();
                try {
                    // Supprimer le joueur de la base de données en utilisant son identifiant
                    joueurService.deleteById(joueur.getIdJoueur());

                    // Supprimer le joueur de la TableView
                    joueurTable.getItems().remove(joueur);

                    // Afficher un message de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Le joueur a été supprimé avec succès !");
                    alert.showAndWait();
                } catch (RuntimeException e) {
                    // En cas d'erreur, afficher un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur lors de la suppression du joueur : " + e.getMessage());
                    alert.showAndWait();
                }
            } else {
                // Si aucun joueur n'est sélectionné, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
                alert.showAndWait();
            }
        }

    @FXML
    void tournois(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournoiClient.fxml"));
        Parent root = loader.load();
        // Appel de showTournoi()
        TournoiClientController controller = loader.getController();
        controller.showTournoi();

        // Créer une nouvelle scène
        Scene scene = new Scene(root, 1300, 800);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Tournois");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    }