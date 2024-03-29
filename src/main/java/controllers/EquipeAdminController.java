package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import service.EquipeService;
import service.JoueurService;
import service.TournoiService;
import service.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    @FXML
    private ComboBox<String> comboBoxTri;
    private static User loggedInUser;


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

        comboBoxTri.setItems(FXCollections.observableArrayList("Nom", "Tournoi", "Capitaine","statut"));

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
        User user = userservice.readById(5);

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
        showNotification();
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
                showJoueurs(selectedEquipe.getIdE());
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
        showNotificationsupp();
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
        showNotificationsuppJ();
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
              /*  Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Le joueur a été ajouté avec succès à l'équipe !");
                successAlert.showAndWait();*/
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

        showNotificationJ();
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
    private void showNotificationsupp() {
        try {
            Image image = new Image("img/notification.png");

            ImageView imageView = new ImageView(image);
            // Définir la largeur et la hauteur souhaitées de l'image
            imageView.setFitWidth(50); // Largeur de l'image
            imageView.setFitHeight(50); // Hauteur de l'image

            Notifications notifications = Notifications.create();
            notifications.graphic(imageView); // Utiliser l'ImageView modifié
            notifications.text("une equipe a été supprimer");
            notifications.title("Message de succès");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showNotificationJ() {
        try {
            Image image = new Image("img/notification.png");

            ImageView imageView = new ImageView(image);
            // Définir la largeur et la hauteur souhaitées de l'image
            imageView.setFitWidth(50); // Largeur de l'image
            imageView.setFitHeight(50); // Hauteur de l'image

            Notifications notifications = Notifications.create();
            notifications.graphic(imageView); // Utiliser l'ImageView modifié
            notifications.text("un joueur a ete ajouter");
            notifications.title("Message de succès");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showNotificationsuppJ() {
        try {
            Image image = new Image("img/notification.png");

            ImageView imageView = new ImageView(image);
            // Définir la largeur et la hauteur souhaitées de l'image
            imageView.setFitWidth(50); // Largeur de l'image
            imageView.setFitHeight(50); // Hauteur de l'image

            Notifications notifications = Notifications.create();
            notifications.graphic(imageView); // Utiliser l'ImageView modifié
            notifications.text("un joueur a ete supprimé");
            notifications.title("Message de succès");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @FXML
    void genererEquipe(ActionEvent event) {
// Create a confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Voulez-vous générer les equipes ?");
        confirmationAlert.setContentText("Appuyez sur OK pour générer les equipes.");

        // Show the confirmation alert and wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Check if the user clicked OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User wants to generate the bill
            String fileName = "Equipes.pdf";
            String cn = " ";
            String cp = " ";
            String cq = " ";
            List<Joueur> cl = new ArrayList<>();

            int index = 0;

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                // Création d'un document
                Document document = new Document();
                // Création d'un écrivain PDF associé au document
                PdfWriter.getInstance(document, fileOutputStream);

                // Ouverture du document pour écrire
                document.open();

                document.add(new Paragraph("Bienvenue chez GesPort"));
                document.add(new Paragraph());
                document.add(new Paragraph("Votre PDF :"));
                document.add(new Paragraph());
                document.add(new Paragraph());
                List<Equipe> equipes =  es.readAll();
                while (index < equipes.size()) {
                    Equipe equipe = equipes.get(index);
                    String nomE = equipe.getNomE();
                    String nomT = equipe.getTournoi().getNomT();
                    String stat = equipe.getStatutE();


                    cn= "Equipe n"+ index + ": "+ nomE + " \n" ;
                    document.add(new Paragraph(cn));
                    document.add(new Paragraph());
                    cp= "Tournoi : "+ nomT + " \n" ;

                    document.add(new Paragraph(cp));
                    document.add(new Paragraph());


                    cq= "statut : "+ stat + " \n" ;

                    document.add(new Paragraph(cq));
                    document.add(new Paragraph());

                    String cm = "-----------------------------";
                    document.add(new Paragraph(cm));
                    document.add(new Paragraph());
                    index++;
                }

                document.add(new Paragraph(""));
                document.close();
                System.out.println("PDF généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // User canceled the operation
            System.out.println("Génération de l'equipe annulée par l'admin.");
        }
    }


}



