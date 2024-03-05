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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import service.EquipeService;
import service.JoueurService;
import service.TournoiService;
import service.UserService;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
public class EquipeClientController {

    private List<Equipe> Elist;
    private final EquipeService es = new EquipeService();


    private final TournoiService tournoiService = new TournoiService();
    @FXML
    private TableView<Equipe> equipeTable;
    List<Joueur> listeDesJoueurs = new ArrayList<>();

    private final JoueurService js= new JoueurService();


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
    @FXML
    private TextField rechercheTextField;
    @FXML
    private ComboBox<String> comboBoxTri;

    private static User loggedInUser;
    public void showEquipe() throws IOException {
        // Récupérer toutes les équipes depuis le service
        Elist = es.readAll();

        // Filtrer les équipes pour ne garder que celles qui participent à des tournois non terminés
        List<Equipe> filteredEquipes = Elist.stream()
                .filter(equipe -> !equipe.getTournoi().getStatutT().equalsIgnoreCase("terminé"))
                .collect(Collectors.toList());

        // Configurer les colonnes de la table avec les données filtrées
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

        // Afficher les données filtrées dans la TableView
        if (equipeTable != null && equipeTable instanceof TableView<Equipe>) {
            ((TableView<Equipe>) equipeTable).setItems(FXCollections.observableArrayList(filteredEquipes));
        }

    }
    private void showJoueurs(int equipeid) {
        // Récupération des joueurs pour l'équipe spécifiée depuis le service
        listeDesJoueurs = js.getJoueurbyEquipe(equipeid);

        // Création d'une liste observable pour les joueurs
        ObservableList<Joueur> observableJoueurs = FXCollections.observableArrayList(listeDesJoueurs);

        // Ajout des joueurs à la table
        joueurTable.setItems(observableJoueurs);
    }

    public void initialize() {
        loggedInUser = LoginUserControllers.getLoggedInUser();
        // Récupérer tous les noms des tournois depuis le service
        List<String> allTournamentNames = tournoiService.readAllNames();

        // Filtrer les noms des tournois qui ne sont pas terminés
        List<String> nonTerminatedTournamentNames = allTournamentNames.stream()
                .filter(name -> !tournoiService.Tournoitermine(name))
                .collect(Collectors.toList());

        // Mettre à jour la ComboBox avec les noms des tournois non terminés
        ObservableList<String> observableNames = FXCollections.observableArrayList(nonTerminatedTournamentNames);
        nomT.setItems(observableNames);

        // Initialisation de la TableView des joueurs
        nomJoueurColumn.setCellValueFactory(new PropertyValueFactory<>("joueur"));

        comboBoxTri.setItems(FXCollections.observableArrayList("Nom", "Tournoi", "Capitaine","statut"));

        // Appeler la méthode showEquipe() pour afficher les équipes
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
    }

    @FXML
    public void ajouter(ActionEvent actionEvent) {
        String nom = nomE.getText();
        // Récupérer l'ID du tournoi sélectionné dans la ComboBox
        String nomTournoi = nomT.getValue();
        // Vérifier si une équipe avec le même nom existe déjà pour ce tournoi
        if (Elist.stream().anyMatch(equipe -> equipe.getNomE().equals(nom) && equipe.getTournoi().getNomT().equals(nomTournoi))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une équipe avec le même nom existe déjà pour ce tournoi.");
            alert.showAndWait();
            return; // Sortir de la méthode si une équipe avec le même nom existe déjà
        }

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
        Equipe e = new Equipe(nom, tournoi, loggedInUser, Statut);

        equipeService.add(e);

        // Mise à jour de la liste Elist
        Elist = es.readAll();
        // Filtrer les équipes pour ne garder que celles qui participent à des tournois non terminés
        List<Equipe> filteredEquipes = Elist.stream()
                .filter(equipe -> !equipe.getTournoi().getStatutT().equalsIgnoreCase("terminé"))
                .collect(Collectors.toList());


        // Mise à jour de la TableView
        equipeTable.getItems().setAll(filteredEquipes);

        // Alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'équipe a été ajoutée avec succès !");
        alert.showAndWait();

        // Ajouter un joueur à la dernière équipe ajoutée
        ajouterJ(null);
        showNotification();
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

            // Appeler showJoueurs pour afficher les joueurs de la dernière équipe ajoutée
            showJoueurs(derniereEquipe.getIdE());
        }



    }

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
                    // Supprimer le joueur de la base de données
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

    public void calender(ActionEvent actionEvent) throws IOException {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CalendarFront.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur du fichier FXML chargé
        CalendarController controller = loader.getController();

        // Créer une nouvelle scène avec la vue chargée
        Scene scene = new Scene(root);

        // Créer une nouvelle fenêtre avec la nouvelle scène
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Calendrier des Tournoi");

        // Afficher la nouvelle fenêtre
        newStage.show();
    }
    private void showNotification() {
        try {
            Image image = new Image("img/notification.png");

            ImageView imageView = new ImageView(image);
            // Définir la largeur et la hauteur souhaitées de l'image
            imageView.setFitWidth(50); // Largeur de l'image
            imageView.setFitHeight(50); // Hauteur de l'image

            Notifications notifications = Notifications.create();
            notifications.graphic(imageView); // Utiliser l'ImageView modifié
            notifications.text("Equipe ajoutée avec succès");
            notifications.title("Message de succès");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void onTriComboBoxChanged(ActionEvent actionEvent) {
        // Obtenir la valeur sélectionnée dans la ComboBox de tri
        String triSelectionne = comboBoxTri.getValue();

        // Vérifier si une option de tri est sélectionnée
        if (triSelectionne != null) {
            // Trier la TableView en fonction de l'option sélectionnée
            switch (triSelectionne) {
                case "Nom":
                    equipeTable.getSortOrder().clear(); // Effacer les précédents ordres de tri
                    equipeTable.getSortOrder().add(nomColumn); // Ajouter l'ordre de tri par nom
                    break;
                case "Tournoi":
                    equipeTable.getSortOrder().clear();
                    equipeTable.getSortOrder().add(TColumn);
                    break;
                case "Capitaine":
                    equipeTable.getSortOrder().clear();
                    equipeTable.getSortOrder().add(nomUColumn);
                    break;
                case "Statut":
                    equipeTable.getSortOrder().clear();
                    equipeTable.getSortOrder().add(statutColumn);
                    break;
                default:
                    // Ne rien faire si aucune option valide n'est sélectionnée
                    break;
            }
        }
    }

    @FXML
    void accueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(); // Créez une nouvelle instance de Stage
            stage.setScene(new Scene(root));
            stage.setTitle("GeSport"); // Titre de la fenêtre
            stage.show();

            // Fermez la fenêtre actuelle si nécessaire
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void compte(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Compte");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void reclamation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionReclamation.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("reclamation");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void tournois(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TournoiClient.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("tournois");

        // Afficher la nouvelle fenêtre
        stage.show();
    }


    @FXML
    void activite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show_activite.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("activite");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
    @FXML
    void restaurant(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AccueilProduit.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Restaurant");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

}



