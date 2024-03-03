package controller;


        import controllers.LoginUserControllers;
        import entities.Activite;
        import entities.Data;
        import entities.Reservation;
        import entities.User;
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
        import javafx.scene.image.ImageView;
        import javafx.stage.Stage;
        import service.ReservationService;
        import service.UserService;

        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.sql.Date;
        import java.sql.SQLException;
        import java.text.SimpleDateFormat;
        import java.time.LocalDate;
        import java.time.ZoneId;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;
        import java.util.stream.Collectors;

public class add_reservationController {

    @FXML
    private TableColumn<?, ?> CVActiviteR;

    @FXML
    private TableColumn<?, ?> CVDateR;

    @FXML
    private TableColumn<?, ?> CVHeureR;

    @FXML
    private TableColumn<?, ?> CVStatutR;

    @FXML
    private ImageView ImageActiviteR;

    @FXML
    private TableView<Reservation> TableViewR;

    @FXML
    private Label activiteR;

    @FXML
    private Button ajouterR;

    @FXML
    private Button annulerR;

    @FXML
    private DatePicker dateR;

    @FXML
    private ComboBox<String> heureR;


    @FXML
    private Button updateR;

    @FXML
    private ComboBox<String> CBactiviteR;

    private Activite activite ;
    private Reservation reservation;
    public static ObservableList<Reservation> obReservation;
    ReservationService rs = new ReservationService();

    private ObservableList<String> heuresReservees = FXCollections.observableArrayList();

    private static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    // Méthode pour obtenir l'utilisateur connecté
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public void setActivite(Activite activite) {
        this.activite = activite;
        System.out.println("Activite in setActivite: " + activite);
        // Appeler la méthode d'initialisation avec l'activité si elle n'est pas null
        if (activite != null) {
            try {
                initializeWithActivite(activite);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Activite getActivite() {
        return activite;
    }

    /*void combobox(){
        ObservableList<String> myObservableList1 = FXCollections.observableArrayList(ReservationService.find_nameActivite());
        CBactiviteR.setItems(myObservableList1);
    }*/

    public void initialize() throws IOException {
        loggedInUser = LoginUserControllers.getLoggedInUser();
        // Initialiser le ComboBox avec des données
        ObservableList<String> options = FXCollections.observableArrayList(
                "08:00",
                "09:30","11:00","12:30","14:00","15:30","17:00"
        );
        // Ajouter la logique pour exclure les heures déjà réservées
        if (activite != null) {
            // Obtenez les réservations existantes pour l'activité et la date actuelle
            List<Reservation> reservations = rs.getReservationsByActivityAndDate(activite,LocalDate.now());

            // Exclure les heures déjà réservées de la ComboBox
            for (Reservation reservation : reservations) {
                options.remove(reservation.getHeureR());
            }
        }

        heureR.setItems(options);
        System.out.println("Controller Initialized");
        System.out.println("Activité dans initialize : " + activite);
        /*getActivite();
        if (activite != null) {
            activiteR.setText(activite.getNomA());
            System.out.println("Activité dans initialize : " + activite);
            System.out.println(activite.getNomA());
        } else {
            System.out.println("Activite is null");
        }*/

        //combobox();
        rs.supprimerReservationsPerimees();
        ShowReservation();
    }


    public void initializeWithActivite(Activite activite) throws IOException {
        // Initialisez ici votre contrôleur avec l'activité
        this.activite = activite;
        System.out.println("activite = " + this.activite);

        // Le reste de votre logique d'initialisation...
    }

    @FXML
    void checkDate() {
        if (dateR.getValue() != null) {

            // Charger les horaires disponibles pour l'activité et la date sélectionnée
            List<String> horairesDisponibles = rs.getAvailableHoursForActivityAndDate(activite, dateR.getValue());

            // Mettre à jour le ComboBox avec les horaires disponibles
            heureR.setItems(FXCollections.observableArrayList(horairesDisponibles));

            // Rendre le ComboBox visible
            heureR.setVisible(true);

        }
    }

    @FXML
    void add_reservation(ActionEvent event) throws IOException {

        //String nom = activiteR.getText();
        Date date = Date.valueOf(dateR.getValue());
        String heure = heureR.getSelectionModel().getSelectedItem();
        // Vérification du contrôle de saisie
        if ( date == null || heure == null ) {
            // Afficher un message d'erreur si les champs obligatoires ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return; // Ne pas poursuivre si les champs obligatoires ne sont pas remplis
        }
        LocalDate selectedDate = dateR.getValue();
        LocalDate currentDate = LocalDate.now();

        if (selectedDate == null || selectedDate.isBefore(currentDate)) {
            // Afficher un message d'erreur si la date n'est pas valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de date");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une date ultérieure à la date actuelle.");
            alert.showAndWait();
            return; // Ne pas poursuivre si la date n'est pas valide
        }

        // Ajout de l'impression de message pour vérifier l'état de l'objet activite
        System.out.println("Activite in add_reservation: " + activite);

        User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        UserService us = new UserService();
        User u = us.readById(2);

        List<Reservation> existingReservations = rs.getReservationsByUserAndActivity(loggedInUser, activite);

        if (!existingReservations.isEmpty()) {
            // L'utilisateur a déjà réservé cette activité
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de réservation");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez déjà réservé cette activité.");
            alert.showAndWait();
            return; // Ne pas poursuivre si l'utilisateur a déjà réservé cette activité
        }

       // idA = Artwork_Services.find_idroom(nameRoom) ;
        rs.add(new Reservation(loggedInUser,activite,date,heure,"En cours"));

        // Ajouter l'heure réservée à la liste d'heures réservées spécifique à l'utilisateur
        heuresReservees.add(heure);

        // Mettre à jour la liste d'options du ComboBox
        updateComboBoxOptions();

        // Afficher un message de succès
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Message d'information");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Ajouté avec succès !");
        successAlert.showAndWait();
        ShowReservation();
    }

    private void updateComboBoxOptions() {
        // Obtenez toutes les options disponibles
        ObservableList<String> allOptions = FXCollections.observableArrayList(
                "08:00", "09:30", "11:00", "12:30", "14:00", "15:30", "17:00"
        );

        // Supprimez de la liste d'options les heures réservées spécifiques à l'utilisateur
        allOptions.removeAll(heuresReservees);

        // Mettez à jour la liste d'options du ComboBox
        heureR.setItems(allOptions);
    }

    List<Reservation> resList;

    public void ShowReservation() throws IOException {

        resList = rs.readAll();

        List<Reservation> filtredResList= new ArrayList<>();
        User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
        UserService us = new UserService();
        User u = us.readById(2);



        for (Reservation r:resList) {
            if (r.getUser().equals(loggedInUser)) {
                filtredResList.add(r);
            }
        }

        CVActiviteR.setCellValueFactory(new PropertyValueFactory<>("activite"));

        CVDateR.setCellValueFactory(new PropertyValueFactory<>("DateDebutR"));
        CVHeureR.setCellValueFactory(new PropertyValueFactory<>("HeureR"));
        CVStatutR.setCellValueFactory(new PropertyValueFactory<>("statutR"));

        if (TableViewR != null && TableViewR instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Reservation>) TableViewR).setItems(FXCollections.observableArrayList(filtredResList));
        }

    }


    @FXML
    void accueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Accueil");

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
        stage.setTitle("Activités");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void compte(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void restaurant(ActionEvent event) {

    }

    @FXML
    void tournois(ActionEvent event) {

    }

}
