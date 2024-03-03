package controllers;

import entities.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EquipeAdminController {
    private List<Equipe> Elist;
    private final EquipeService es = new EquipeService();
    private List<Joueur> Jlist;
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
    private TableView<Joueur> joueurTable;

    @FXML
    private TableColumn<Joueur, String> nomJoueurColumn;


    @FXML
    private TextField joueur;
    @FXML
    private Button ajouterj;
    @FXML
    private Button supprimerj;
    @FXML
    private Label lesjoueurtxt;





    @FXML
    private TextField nomE;

    @FXML
    private RadioButton inscriteRadio;

    @FXML
    private RadioButton qualifieeRadio;

    @FXML
    private RadioButton elimineeRadio;

    @FXML
    private ComboBox<String> nomT;

    @FXML
    private TextField rechercheTextField;

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

       /* // écouteur de sélection
        equipeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Afficher les nom des joueurs
                showJoueurs(newSelection.getIdE());
            }
        });*/

    }


    private void showJoueurs(int equipeid) {
        // Récupération des joueurs pour l'équipe spécifiée
        Jlist = js.getJoueurbyEquipe(equipeid);

        // Création d'une liste  joueurs
        ObservableList<Joueur> observableJoueurs = FXCollections.observableArrayList(Jlist);

        // Ajout des joueurs à la table
        joueurTable.setItems(observableJoueurs);
    }

    public void initialize() {

        try {
            showEquipe();
            rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    rechercheEquipe(newValue); // Appel de la méthode de recherche avec le nouveau texte
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //combobox
        List<String> tournoiNames = tournoiService.readAllNames();
        ObservableList<String> observableNames = FXCollections.observableArrayList(tournoiNames);
        nomT.setItems(observableNames);
        //tab J
        nomJoueurColumn.setCellValueFactory(new PropertyValueFactory<>("joueur"));

        // Rendre invisibles les éléments de la table des joueurs, le bouton "Ajouter Joueur" et le champ texte du joueur
        joueurTable.setVisible(false);
        ajouterj.setVisible(false);
        supprimerj.setVisible(false);
        joueur.setVisible(false);
        lesjoueurtxt.setVisible(false);

        }





    @FXML
    void ajouter(ActionEvent event) {

        String nom = nomE.getText();
        // Récupérer l'ID du tournoi sélectionné dans la ComboBox
        String nomTournoi = nomT.getValue();
        // controle de saisie pour le nom
        if (Elist.stream().anyMatch(equipe -> equipe.getNomE().equals(nom) && equipe.getTournoi().getNomT().equals(nomTournoi))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une équipe avec le même nom existe déjà pour ce tournoi.");
            alert.showAndWait();
            return;
        }



        String statut;
        if (inscriteRadio.isSelected()) {
            statut = "Inscrite";
        } else if (qualifieeRadio.isSelected()) {
            statut = "Qualifiée";
        } else if (elimineeRadio.isSelected()) {
            statut = "Éliminée";
        } else {
            statut = "Inscrite";
        }

        // Création des instances
        EquipeService equipeService = new EquipeService();
        TournoiService tournoiService = new TournoiService();
        UserService userservice = new UserService();

        // read by id Tournoi et User
        int IDTE = tournoiService.getIdByName(nomTournoi);
        Tournoi tournoi = tournoiService.readById(IDTE);
        User user = userservice.readById(1);

        // Création équipe avec Tournoi et User récupérés
        Equipe e = new Equipe(nom, tournoi, user, statut);


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
            nomT.setValue(selectedEquipe.getTournoi().getNomT());
            // Effacer la sélection des autres RadioButtons
            clearRadioButtons();
            // Sélectionner le RadioButton correspondant au statut de l'équipe
            switch (selectedEquipe.getStatutE()) {
                case "Inscrite":
                    inscriteRadio.setSelected(true);
                    break;
                case "Qualifiée":
                    qualifieeRadio.setSelected(true);
                    break;
                case "Éliminée":
                    elimineeRadio.setSelected(true);
                    break;
                default:
                    break;
            }
            List<Joueur> Jlist = js.getJoueurbyEquipe(selectedEquipe.getIdE());
            if (!Jlist.isEmpty()) {
                joueurTable.setVisible(true);
                ajouterj.setVisible(true);
                joueur.setVisible(true);
                supprimerj.setVisible(true);
                lesjoueurtxt.setVisible(true);

                showJoueurs(selectedEquipe.getIdE());
            } else {
                // Si la liste des joueurs associée à cette équipe est vide, afficher une alerte
                joueurTable.setVisible(true);
                ajouterj.setVisible(true);
                joueur.setVisible(true);
                supprimerj.setVisible(true);
                lesjoueurtxt.setVisible(true);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("La liste des joueurs pour cette équipe est vide.");
                alert.showAndWait();
            }
        }else {
        // Si aucune équipe n'est sélectionnée, masquer la table des joueurs, le bouton et le champ de texte
        joueurTable.setVisible(false);
        ajouterj.setVisible(false);
        joueur.setVisible(false);
        supprimerj.setVisible(false);
        lesjoueurtxt.setVisible(false);
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
        Equipe equipeSelectionne = equipeTable.getSelectionModel().getSelectedItem();
        if (equipeSelectionne != null) {
            // Récupérer les valeurs
            String nom = nomE.getText();

            String statut;
            if (inscriteRadio.isSelected()) {
                statut = "Inscrite";
            } else if (qualifieeRadio.isSelected()) {
                statut = "Qualifiée";
            } else if (elimineeRadio.isSelected()) {
                statut = "Éliminée";
            } else {
                statut = "Inscrite";
            }
            String nomTournoi = nomT.getValue();
            int IDTE = tournoiService.getIdByName(nomTournoi);


            TournoiService tournoiService = new TournoiService();
            UserService userService = new UserService();

            Tournoi tournoi = tournoiService.readById(IDTE);



            // maj  equipe sélectionné
            equipeSelectionne.setNomE(nom);
            equipeSelectionne.setTournoi(tournoi);
            equipeSelectionne.setStatutE(statut);




            // maj TableView
            equipeTable.refresh();

            //  appel update
            EquipeService es = new EquipeService();
            es.update(equipeSelectionne);

            nomE.clear();
            nomT.getSelectionModel().clearSelection();
            // Effacer la sélection des autres RadioButtons
            clearRadioButtons();
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

    @FXML
    void clearRadioButtons() {
        inscriteRadio.setSelected(false);
        qualifieeRadio.setSelected(false);
        elimineeRadio.setSelected(false);
    }
    @FXML
    public void supprimerJ(ActionEvent actionEvent) {
        // Récupérer le joueur sélectionné dans la TableView joueurTable
        Joueur joueur = joueurTable.getSelectionModel().getSelectedItem();

        // Vérifier si un joueur est sélectionné
        if (joueur != null) {
            // confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Supprimer le joueur");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer ce joueur ?");

            //  attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // confirmé
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Créer une instance du service JoueurService
                JoueurService joueurService = new JoueurService();

                try {
                    // Supprimer le joueur de la base de données en utilisant le service
                    joueurService.delete(joueur);

                    // Supprimer le joueur de la TableView
                    joueurTable.getItems().remove(joueur);

                    // Afficher un message de succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Le joueur a été supprimé avec succès !");
                    successAlert.showAndWait();
                } catch (RuntimeException e) {
                    // En cas d'erreur, afficher un message d'erreur
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Erreur lors de la suppression du joueur : " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        } else {
            // Si aucun joueur n'est sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un joueur à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    public void ajouterj(ActionEvent actionEvent) {

        // Récupérer l'équipe sélectionnée dans la table des équipes
        Equipe equipeSelectionnee = equipeTable.getSelectionModel().getSelectedItem();

        // Vérifier si une équipe est sélectionnée
        if (equipeSelectionnee != null) {
            // Récupérer le nom du joueur à partir du champ de texte nomJoueurTextField
            String nomJoueur = joueur.getText();

            // Vérifier si le nom du joueur n'est pas vide
            if (!nomJoueur.isEmpty()) {
                // Créer une instance de la classe Joueur avec le nom du joueur et l'ID de l'équipe sélectionnée
                Joueur nouveauJoueur = new Joueur();
                nouveauJoueur.setJoueur(nomJoueur);
                nouveauJoueur.setEquipe(equipeSelectionnee);

                // Utiliser le service JoueurService pour ajouter le joueur à la base de données
                JoueurService joueurService = new JoueurService();
                joueurService.add(nouveauJoueur);

                // Mettre à jour la TableView des joueurs pour afficher le nouveau joueur
                List<Joueur> joueurs = joueurService.getJoueurbyEquipe(equipeSelectionnee.getIdE());
                ObservableList<Joueur> observableJoueurs = FXCollections.observableArrayList(joueurs);
                joueurTable.setItems(observableJoueurs);

                // Effacer le champ de texte nomJoueurTextField après l'ajout du joueur
                joueur.clear();

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Le joueur a été ajouté avec succès à l'équipe !");
                successAlert.showAndWait();
            } else {
                // Si le champ de texte est vide, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer le nom du joueur.");
                alert.showAndWait();
            }
        } else {
            // Si aucune équipe n'est sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une équipe.");
            alert.showAndWait();
        }
    }

    public void rechercheEquipe(String searchText) throws IOException  {
        List<Equipe> searchResult = new ArrayList<>();
        for (Equipe equipe : Elist) {
            if (equipe.getNomE().toLowerCase().contains(searchText.toLowerCase())) {
                searchResult.add(equipe);
            }
        }
        // Mettre à jour la TableView avec les résultats de la recherche
        equipeTable.setItems(FXCollections.observableArrayList(searchResult));
    }
}



