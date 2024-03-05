package controllers;

import entities.InscriTournoi;
import entities.Tournoi;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import service.TournoiService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TournoiClientController {

    private List<Tournoi> Tlist;
    private final TournoiService ts = new TournoiService();
    private final Tournoi tournoi = new Tournoi();
    private SortedList<Tournoi> sortedTournois;

    @FXML
    private WebView mapWebView;
    @FXML
    private TextField rechercheTextField;
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
    private ComboBox<String> comboBoxTri;
    public void initialize() {


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


        WebEngine webEngine = mapWebView.getEngine();
        webEngine.load(getClass().getResource("/Maps.html").toExternalForm());
        // loadMap(tournoi.getDescriT());

        comboBoxTri.setItems(FXCollections.observableArrayList("Nom", "Date de début", "Date de fin"));

    }


    public void showTournoi() throws IOException {
        // Récupérer tous les tournois depuis le service
        Tlist = ts.readAll();

        // Filtrer les tournois qui n'ont pas le statut "terminé"
        List<Tournoi> filteredTournois = Tlist.stream()
                .filter(tournoi -> !tournoi.getStatutT().equalsIgnoreCase("terminé"))
                .collect(Collectors.toList());

        // Configurer les colonnes de la table avec les données filtrées
        nomColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nomT"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateDebutT"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("DateFinT"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("DescriT"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("statutT"));

        // Afficher les données filtrées dans la TableView
        if (tournoiTable != null && tournoiTable instanceof TableView<Tournoi>) {
            ((TableView<Tournoi>) tournoiTable).setItems(FXCollections.observableArrayList(filteredTournois));
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


    public void selection(MouseEvent mouseEvent) {
        Tournoi selectedTournoi = tournoiTable.getSelectionModel().getSelectedItem();
        if (selectedTournoi != null) {
            // Remplir les champs


            String Description = selectedTournoi.getDescriT();
            loadMap(Description);


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