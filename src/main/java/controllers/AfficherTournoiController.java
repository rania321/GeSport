package controllers;

import entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Worker;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import org.controlsfx.control.Notifications;
import service.EquipeService;
import service.InscriTournoiService;
import service.JoueurService;
import service.TournoiService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AfficherTournoiController {
    @FXML
    private DatePicker DdebutT;
    @FXML
    private WebView mapWebView;

    @FXML
    private TextField DescriT;

    @FXML
    private DatePicker DfinT;

    @FXML
    private RadioButton enCoursRadio;

    @FXML
    private RadioButton termineRadio;

    @FXML
    private RadioButton aVenirRadio;

    @FXML
    private TextField nomT;

    private List<Tournoi> Tlist;

    private List<InscriTournoi> Ilist;

    private final TournoiService ts = new TournoiService();
    private final Tournoi tournoi = new Tournoi();
    private final EquipeService equipeService = new EquipeService();
    private final Equipe equipe = new Equipe();
    private final JoueurService joueurService = new JoueurService();
    private final Joueur joueur = new Joueur();
    private final InscriTournoi it = new InscriTournoi();
    private final InscriTournoiService its = new InscriTournoiService();
    private SortedList<Tournoi> sortedTournois;


    @FXML
    private TableView<Tournoi> tournoiTable;


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


    @FXML
    private TableView<InscriTournoi> inscriTable;
    @FXML
    private TableColumn<InscriTournoi, String> equipeColumn;
    @FXML
    private Button supprimerIns;
    @FXML
    private Label lesinscriptions;

    @FXML
    private TextField rechercheTextField;
    @FXML
    private ComboBox<String> comboBoxTri;

    private static User loggedInUser;

    public void showTournoi() throws IOException {
        Tlist = ts.readAll();


        nomColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nomT"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateDebutT"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateFinT"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("DescriT"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("statutT"));

        if (tournoiTable != null && tournoiTable instanceof TableView<Tournoi>) {
            ((TableView<Tournoi>) tournoiTable).setItems(FXCollections.observableArrayList(Tlist));
        }

      /*  //  sélection à la table tournoiTable
        tournoiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Afficher les inscriptions du tournoi sélectionné
                showInscriptions(newSelection.getIdT());
            }
        });*/


    }


    private void showInscriptions(int tournoiId) {
        // Récupération des inscriptions pour le tournoi spécifié
        Ilist = its.getInscriptionsByTournoi(tournoiId);

        // Configuration des colonnes de la table inscriTable
        equipeColumn.setCellValueFactory(cellData -> {
            InscriTournoi is = cellData.getValue();
            String nomEquipe = is.getE().getNomE();
            return new SimpleStringProperty(nomEquipe);
        });


        // Ajout des données à la table inscriTable
        inscriTable.setItems(FXCollections.observableArrayList(Ilist));
    }

    public void initialize() {

        loggedInUser = LoginUserControllers.getLoggedInUser();

        try {
            showTournoi();
            rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    recherchetournoi(newValue); // Appel de la méthode de recherche avec le nouveau texte
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        inscriTable.setVisible(false);
        equipeColumn.setVisible(false);
        supprimerIns.setVisible(false);
        lesinscriptions.setVisible(false);


        WebEngine webEngine = mapWebView.getEngine();
        webEngine.load(getClass().getResource("/Maps.html").toExternalForm());
        // loadMap(tournoi.getDescriT());



        comboBoxTri.setItems(FXCollections.observableArrayList("Nom", "Date de début", "Date de fin"));

    }


    @FXML
    void ajouter(ActionEvent event) {
        String nom = nomT.getText();
        // contrôle de saisie sur le nom
        if (ts.readAll().stream().anyMatch(t -> t.getNomT().equals(nom))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Un tournoi avec le même nom existe déjà.");
            alert.showAndWait();
            return;
        }

        // Convertir les valeurs des DatePicker en java.util.Date
        Date dateDebut = java.sql.Date.valueOf(DdebutT.getValue());
        Date dateFin = java.sql.Date.valueOf(DfinT.getValue());

        String Description = DescriT.getText();
        loadMap(Description);


        String Statut;

        //  bouton radio
        if (enCoursRadio.isSelected()) {
            Statut = "En cours";
        } else if (termineRadio.isSelected()) {
            Statut = "Terminé";
        } else if (aVenirRadio.isSelected()) {
            Statut = "À venir";
        } else {
            // Aucun bouton radio sélectionné,  valeur par défaut
            Statut = "À venir";
        }

        Tournoi t = new Tournoi(nom, dateDebut, dateFin, Description, Statut);
        TournoiService ts = new TournoiService();
        ts.add(t);
        // maj  liste des tournois depuis  service
        List<Tournoi> tournois = ts.readAll();

        // maj   des données de la TableView
        tournoiTable.getItems().setAll(tournois);

        nomT.clear();
        DescriT.clear();
        DdebutT.setValue(null);
        DfinT.setValue(null);
        clearRadioButtons();

        //  alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le tournoi a été ajouté avec succès !");
        alert.showAndWait();

        showNotification();

    }


    @FXML
    void supprimer(ActionEvent event) {
        // Récupérer la ligne sélectionnée dans la TableView
        Tournoi tournoi = tournoiTable.getSelectionModel().getSelectedItem();

        if (tournoi != null) {

            supprimerLigne(tournoi);
        } else {
            //  message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne à supprimer.");
            alert.showAndWait();
        }
        showNotificationsupp();
    }

    private void supprimerLigne(Tournoi tournoi) {

        // Récupérer toutes les équipes associées à ce tournoi
        List<Equipe> equipes = equipeService.readAllEquipesByTournoiId(tournoi.getIdT());

        // Supprimer toutes les équipes associées à ce tournoi
        for (Equipe equipe : equipes) {
            // Récupérer les joueurs de cette équipe et les supprimer
            List<Joueur> joueurs = joueurService.getJoueurbyEquipe(equipe.getIdE());
            for (Joueur joueur : joueurs) {
                joueurService.delete(joueur);
            }
            // Supprimer l'équipe
            equipeService.delete(equipe);
        }

        // Maintenant que les équipes associées sont supprimées, vous pouvez supprimer le tournoi
        tournoiTable.getItems().remove(tournoi);
        ts.delete(tournoi);

        nomT.clear();
        DescriT.clear();
        DdebutT.setValue(null);
        DfinT.setValue(null);
        clearRadioButtons();


    }


    public void selection(MouseEvent mouseEvent) {
        Tournoi selectedTournoi = tournoiTable.getSelectionModel().getSelectedItem();
        if (selectedTournoi != null) {
            // Remplir les champs
            nomT.setText(selectedTournoi.getNomT());
            DescriT.setText(selectedTournoi.getDescriT());

            String Description = selectedTournoi.getDescriT();
            loadMap(Description);


            clearRadioButtons();
            //  bouton radio
            switch (selectedTournoi.getStatutT()) {
                case "En cours":
                    enCoursRadio.setSelected(true);
                    break;
                case "Terminé":
                    termineRadio.setSelected(true);
                    break;
                case "À venir":
                    aVenirRadio.setSelected(true);
                    break;
                default:
                    // Aucun statut correspondant
                    enCoursRadio.setSelected(false);
                    termineRadio.setSelected(false);
                    aVenirRadio.setSelected(false);
                    break;
            }
            // Convertir les dates
            Date dateDebut = selectedTournoi.getDateDebutT();
            Date dateFin = selectedTournoi.getDateFinT();

            //  dates-->Instant-->LocalDate
            LocalDate localDateDebut = Instant.ofEpochMilli(dateDebut.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateFin = Instant.ofEpochMilli(dateFin.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            // maj DatePicker
            DdebutT.setValue(localDateDebut);
            DfinT.setValue(localDateFin);

            List<InscriTournoi> Ilist = its.getInscriptionsByTournoi(selectedTournoi.getIdT());
            if (!Ilist.isEmpty()) {
                inscriTable.setVisible(true);
                equipeColumn.setVisible(true);
                supprimerIns.setVisible(true);
                lesinscriptions.setVisible(true);

                showInscriptions(selectedTournoi.getIdT());
            } else {

                inscriTable.setVisible(false);
                equipeColumn.setVisible(false);
                supprimerIns.setVisible(false);
                lesinscriptions.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Aucune inscription n'a encore été enregistrée pour ce tournoi");
                alert.showAndWait();

            }
        } else {
            inscriTable.setVisible(false);
            equipeColumn.setVisible(false);
            supprimerIns.setVisible(false);
            lesinscriptions.setVisible(false);

        }
    }

    @FXML
    void modifier(ActionEvent event) {

        // Vérifier si un élément est sélectionné dans la TableView
        Tournoi tournoiSelectionne = tournoiTable.getSelectionModel().getSelectedItem();
        if (tournoiSelectionne != null) {
            // Récupérer les valeurs
            String nom = nomT.getText();
            String Description = DescriT.getText();
            loadMap(Description);
            // Récupérer le statut boutons radio
            String statut;
            if (enCoursRadio.isSelected()) {
                statut = "En cours";
            } else if (termineRadio.isSelected()) {
                statut = "Terminé";
            } else if (aVenirRadio.isSelected()) {
                statut = "À venir";
            } else {
                // Aucun bouton radio sélectionné
                statut = "À venir";
            }


            // Convertir les valeurs des DatePicker
            Date dateDebut = java.sql.Date.valueOf(DdebutT.getValue());
            Date dateFin = java.sql.Date.valueOf(DfinT.getValue());

            // maj
            tournoiSelectionne.setNomT(nom);
            tournoiSelectionne.setDateDebutT(dateDebut);
            tournoiSelectionne.setDateFinT(dateFin);
            tournoiSelectionne.setDescriT(Description);
            tournoiSelectionne.setStatutT(statut);

            // maj TableView
            tournoiTable.refresh();

            //appel update
            TournoiService ts = new TournoiService();
            ts.update(tournoiSelectionne);



            nomT.clear();
            DescriT.clear();
            DdebutT.setValue(null);
            DfinT.setValue(null);
            clearRadioButtons();
        } else {
            //message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un élément à modifier.");
            alert.showAndWait();
        }
    }


    public void backMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardBack.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1300, 800);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");

        // Afficher la nouvelle fenêtre
        stage.show();
    }


    public void toEquipe(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipeAdmin.fxml"));
        Parent root = loader.load();

        EquipeAdminController controller = loader.getController();
        controller.showEquipe();

        // Créer une nouvelle scène avec la vue chargée
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Equipes");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void clearRadioButtons() {
        enCoursRadio.setSelected(false);
        termineRadio.setSelected(false);
        aVenirRadio.setSelected(false);

    }

    public void supprimerIns(ActionEvent actionEvent) {
        // Récupérer l'inscription sélectionnée dans la TableView
        InscriTournoi inscription = inscriTable.getSelectionModel().getSelectedItem();

        if (inscription != null) {
            // Récupérer l'équipe associée à cette inscription
            Equipe equipe = inscription.getE();

            // Supprimer l'équipe de l'inscription sélectionnée
            equipeService.delete(equipe);

            // maj liste des inscriptions affichée dans la table
            showInscriptions(inscription.getT().getIdT());

            //  message de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("L'équipe de l'inscription a été supprimée avec succès !");
            successAlert.showAndWait();
        } else {
            // Aucune inscription sélectionnée erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Veuillez sélectionner une inscription à supprimer.");
            errorAlert.showAndWait();
        }
    }


    public void recherchetournoi(String searchText) throws IOException {
        List<Tournoi> searchResult = new ArrayList<>();
        for (Tournoi tournoi : Tlist) {
            if (tournoi.getNomT().toLowerCase().contains(searchText.toLowerCase())) {
                searchResult.add(tournoi);
            }
        }
        // Mettre à jour la TableView avec les résultats de la recherche
        tournoiTable.setItems(FXCollections.observableArrayList(searchResult));
    }

    public void calender(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
        Parent root = loader.load();

        CalendarController controller = loader.getController();


        // Créer une nouvelle scène avec la vue chargée
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Calendrier des Tournoi");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    public void statEquipe(ActionEvent actionEvent) throws IOException {

        // Créer une nouvelle instance de StatEquipeController
        StatEquipeController controller = new StatEquipeController();

        // Charger les données d'équipes
        EquipeService equipeService = new EquipeService();
        List<Equipe> equipes = equipeService.readAll();

        // Créer un AnchorPane pour afficher le graphique
        AnchorPane anchorPane = new AnchorPane();

        // Passer les données au contrôleur avec l'AnchorPane
        controller.setData(equipes, anchorPane);

        // Créer une nouvelle scène avec l'AnchorPane
        Scene scene = new Scene(anchorPane);

        // Créer un nouveau stage
        Stage stage = new Stage();
        stage.setTitle("Statistiques des équipes");
        stage.setScene(scene);

        // Afficher le stage
        stage.show();


    }


    private void loadMap(String DescriT) {
        WebEngine webEngine = mapWebView.getEngine();

        // Générer le contenu HTML avec l'URL de la carte correcte
        String htmlContent = generateMapHtml(DescriT);

        // Charger le contenu HTML dans la WebView
        webEngine.loadContent(htmlContent);
    }

    private String generateMapHtml(String DescriT) {
        // Construct the map URL based on the club name, governorate, and city
        String mapUrl = "https://maps.google.com/maps?q=" +

                DescriT.replace(" ", "%20") + "&t=k&z=16&output=embed";

        // Generate HTML content with the correct map URL
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Google Maps Example</title>\n" +
                "    <style>\n" +
                "        /* Adjust the size and position of the map */\n" +
                "        #mapouter {\n" +
                "            position: relative;\n" +
                "            text-align: right;\n" +
                "            height: 500px; /* Adjust the height as needed */\n" +
                "            width: 500px; /* Adjust the width as needed */\n" +
                "        }\n" +
                "\n" +
                "        #gmap_canvas2 {\n" +
                "            overflow: hidden;\n" +
                "            background: none !important;\n" +
                "            height: 500px; /* Adjust the height as needed */\n" +
                "            width: 500px; /* Adjust the width as needed */\n" +
                "        }\n" +
                "\n" +
                "        #gmap_canvas {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            border: 0;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"mapouter\">\n" +
                "    <div id=\"gmap_canvas2\">\n" +
                "        <iframe id=\"gmap_canvas\"\n" +
                "                src=\"" + mapUrl + "\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }


    public void onTriComboBoxChanged(ActionEvent actionEvent) {
        String colonneSelectionnee = comboBoxTri.getValue();
        if (colonneSelectionnee != null) {
            switch (colonneSelectionnee) {
                case "Nom":
                    // Tri par nomT
                    sortedTournois = new SortedList<>(FXCollections.observableArrayList(Tlist),
                            Comparator.comparing(Tournoi::getNomT));


                    break;
                case "Date de début":
                    // Tri par DateDebutT
                    sortedTournois = new SortedList<>(FXCollections.observableArrayList(Tlist),
                            Comparator.comparing(Tournoi::getDateDebutT));
                    break;
                case "Date de fin":
                    // Tri par DateFinT
                    sortedTournois = new SortedList<>(FXCollections.observableArrayList(Tlist),
                            Comparator.comparing(Tournoi::getDateFinT));
                    break;

            }
            // Mettre à jour la TableView avec la liste triée
            tournoiTable.setItems(sortedTournois);
        }
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
            notifications.text("Tournoi ajouté avec succès");
            notifications.title("Message de succès");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showNotificationsupp() {
        try {
            Image image = new Image("img/notification.png");

            ImageView imageView = new ImageView(image);
            // Définir la largeur et la hauteur souhaitées de l'image
            imageView.setFitWidth(50); // Largeur de l'image
            imageView.setFitHeight(50); // Hauteur de l'image

            Notifications notifications = Notifications.create();
            notifications.graphic(imageView); // Utiliser l'ImageView modifié
            notifications.text("un tournoi a été supprimer");
            notifications.title("Message de succès");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

