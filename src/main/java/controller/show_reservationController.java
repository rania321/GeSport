package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import controllers.LoginUserControllers;
import entities.QRCodeGenerator;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ReservationService;
import service.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class show_reservationController {

    @FXML
    private TableColumn<?, ?> CVActiviteR;

    @FXML
    private TableColumn<?, ?> CVDateR;

    @FXML
    private TableColumn<?, ?> CVHeureR;

    @FXML
    private TableColumn<?, ?> CVStatutR;

    @FXML
    private ImageView IVBtnDelete;

    @FXML
    private ImageView IVBtnUpdate;

    @FXML
    private TableView<Reservation> TableViewR;

    @FXML
    private TextField activiteR;

    @FXML
    private Button annulerR;

    @FXML
    private DatePicker dateR;

    @FXML
    private ComboBox<String> heureR;

    @FXML
    private Button updateR;

    @FXML
    private Label activiteR1;

    @FXML
    private Label choisirHeure;

    @FXML
    private ImageView imageviewR;

    @FXML
    private ImageView imageviewUpdate;

    @FXML
    private AnchorPane anchorpanemodifier;

    private static User loggedInUser;

    ReservationService rs = new ReservationService();
    Reservation reservation = new Reservation();

    public void initialize() throws IOException {
        loggedInUser = LoginUserControllers.getLoggedInUser();
        // Initialiser le ComboBox avec des données
        ObservableList<String> options = FXCollections.observableArrayList(
                "08:00",
                "09:30","11:00","12:30","14:00","15:30","17:00"
        );
        heureR.setItems(options);
        anchorpanemodifier.setVisible(false);
        imageviewUpdate.setVisible(false);

        TableViewR.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Vérifie si c'est un simple clic
                Reservation selectedReservation = (Reservation) TableViewR.getSelectionModel().getSelectedItem();
                if (selectedReservation != null) {
                    anchorpanemodifier.setVisible(true);
                    imageviewR.setVisible(false);
                    imageviewUpdate.setVisible(true);
                    // Afficher les informations de la séance sélectionnée dans le formulaire
                    displayReservationInfo(selectedReservation);
                }
            }
        });
        rs.supprimerReservationsPerimees();
        ShowReservation();
    }

    @FXML
    void checkDate() {
       if (dateR.getValue() != null) {
            // Charger les horaires disponibles pour la date sélectionnée
           Reservation selectedReservation = (Reservation) TableViewR.getSelectionModel().getSelectedItem();
           List<String> horairesDisponibles = rs.getAvailableHoursForActivityAndDate(selectedReservation.getActivite(), dateR.getValue());

           // Mettre à jour le ComboBox avec les horaires disponibles
           heureR.setItems(FXCollections.observableArrayList(horairesDisponibles));

           // Rendre le ComboBox visible
           heureR.setVisible(true);
           choisirHeure.setVisible(true);
        }
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

        CVActiviteR.setCellValueFactory(new PropertyValueFactory<>("activiteNom"));
        CVDateR.setCellValueFactory(new PropertyValueFactory<>("DateDebutR"));
        CVHeureR.setCellValueFactory(new PropertyValueFactory<>("HeureR"));
        CVStatutR.setCellValueFactory(new PropertyValueFactory<>("statutR"));

        if (TableViewR != null && TableViewR instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Reservation>) TableViewR).setItems(FXCollections.observableArrayList(filtredResList));
        }

    }
    @FXML
    void delete_reservation(ActionEvent event) throws IOException {
// Récupérer l'activité sélectionnée dans la TableView
        Reservation selectedReservation = TableViewR.getSelectionModel().getSelectedItem();

        // Vérifier si une activité est sélectionnée
        if (selectedReservation != null) {

            // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer la réservation");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer la réservation sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();

            // Si l'utilisateur confirme la suppression, procéder
            if (result.isPresent() && result.get() == ButtonType.OK) {

                // Supprimer l'activité de la base de données
                rs.delete(selectedReservation);

                // Rafraîchir l'affichage des activités dans la TableView
                ShowReservation();
                anchorpanemodifier.setVisible(false);
                imageviewUpdate.setVisible(false);
                imageviewR.setVisible(true);
            }
        } else {
            // Afficher un message si aucune activité n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune réservation sélectionnée");
            alert.setHeaderText("Aucune réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à supprimer.");
            alert.showAndWait();
        }
    }



    @FXML
    void update_reservation(ActionEvent event) throws IOException {
// Récupérer les informations modifiées depuis le formulaire

        Date date = java.sql.Date.valueOf(dateR.getValue());
        String heure = heureR.getSelectionModel().getSelectedItem();

        // Mettre à jour la séance dans la base de données
        Reservation selectedReservation = TableViewR.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
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
            selectedReservation.setDateDebutR(date);
            selectedReservation.setHeureR(heure);
            selectedReservation.setStatutR("En cours");
            // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de modification");
            alert.setHeaderText("Modifier la réservation");
            alert.setContentText("Êtes-vous sûr de vouloir modifier la réservation sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();
            // Si l'utilisateur confirme la suppression, procéder
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Mettre à jour la séance dans la base de données
                rs.update(selectedReservation);
                // Rafraîchir l'affichage des séances dans la TableView
                ShowReservation();
                anchorpanemodifier.setVisible(false);
                imageviewUpdate.setVisible(false);
                imageviewR.setVisible(true);
            }
        }
        else {
            // Afficher un message si aucune activité n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Réservation sélectionnée");
            alert.setHeaderText("Aucune Réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à modifier.");
            alert.showAndWait();
        }
    }
    private void displayReservationInfo(Reservation r) {
        //dateR.setValue(r.getDateDebutR());
        // Convert java.sql.Date to LocalDate
        java.sql.Date sqlDate= (Date) r.getDateDebutR();
        java.time.LocalDate localDate = sqlDate.toLocalDate();


        activiteR1.setText(r.getActivite().getNomA());
        // Afficher les informations de réservation dans les champs de formulaire
        //activiteR.setText(r.getActiviteNom());
      /*  dateR.setValue(localDate);
        heureR.setValue(r.getHeureR());*/

        //nomid.setValue(a.getNom());
    }

    User user = new User(); // Récupérer l'utilisateur connecté à partir de votre système d'authentification
    UserService us = new UserService();
    User u = us.readById(2);
    @FXML
    void pdf (ActionEvent event) {
        Reservation selectedReservation = TableViewR.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            if(selectedReservation.getStatutR().equals("confirmée")) {
                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("src/main/java/pdf/output.pdf"));
                    document.open();

                    // Ajouter un cadre autour de la page
                    Rectangle rect = new Rectangle(577, 825, 18, 15);
                    rect.enableBorderSide(1);
                    rect.enableBorderSide(2);
                    rect.enableBorderSide(4);
                    rect.enableBorderSide(8);
                    rect.setBorderColor(new BaseColor(23, 68, 122));
                    rect.setBorderWidth(5);
                    document.add(rect);

                    LineSeparator ls = new LineSeparator();
                    ls.setLineColor(new BaseColor(135, 206, 235)); // Couleur bleu ciel

                    document.add(new Chunk(ls));

                    Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(27, 117, 187));
                    Chunk chunk = new Chunk("Confirmation de la Reservation", font);
                    Paragraph p = new Paragraph(chunk);
                    p.setAlignment(Element.ALIGN_CENTER); // Centrer le texte
                    document.add(p);
                    // Ajouter la date actuelle
                    String date = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
                    Font dateFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK); // Date en noir

                    Chunk dateChunk = new Chunk(date, dateFont);
                    Paragraph dateParagraph = new Paragraph(dateChunk);
                    dateParagraph.setAlignment(Element.ALIGN_CENTER); // Centrer la date
                    document.add(new Chunk(ls)); // Ajouter un autre séparateur de ligne
                    document.add(dateParagraph);
                    // Ajouter une autre ligne horizontale de couleur bleu ciel
                    document.add(new Chunk(ls));
                    // Paragraphe dédié au client
                    Font clientFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                    Paragraph clientParagraph = new Paragraph();
                    clientParagraph.add(new Phrase("Cher(e) " + loggedInUser.getNomU() + " " + loggedInUser.getPrenomU() + ",\n\n", clientFont));
                    clientParagraph.add(new Phrase("Merci d'avoir réservé une activité dans notre centre sportif. Nous sommes impatients de vous accueillir et de vous aider à atteindre vos objectifs de remise en forme.\n\n", clientFont));
                    clientParagraph.add(new Phrase("Ci-dessous, vous trouverez les détails de votre réservation :\n\n", clientFont));
                    document.add(clientParagraph);
                    // Ajouter des espaces vides avant le tableau
                    document.add(new Paragraph("\n\n\n\n\n"));

                    // Ajouter une image à droite du texte
                    Image img1 = Image.getInstance("C:\\Users\\Rania\\Desktop\\gesport\\gesport\\src\\main\\resources\\img\\reservation (1).png");
                    img1.scaleToFit(40f, 40f); // Redimensionner l'image
                    img1.setAbsolutePosition(500f, 760); // Positionner l'image
                    document.add(img1);
                    PdfPTable table = new PdfPTable(1);

                    Font fonte = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

                    PdfPCell cell;
                    p = new Paragraph("Nom de l'activité' : " + selectedReservation.getActiviteNom());
                    cell = new PdfPCell(p);
                    cell.setBorder(Rectangle.BOX);
                    cell.setPadding(10);
                    cell.setBackgroundColor(BaseColor.WHITE); // Gris clair
                    table.addCell(cell);

                    p = new Paragraph("Nom et prénom du client : " + loggedInUser.getNomU() + " " + loggedInUser.getPrenomU(), fonte);
                    cell = new PdfPCell(p);
                    cell.setBorder(Rectangle.BOX);
                    cell.setPadding(10);
                    cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
                    table.addCell(cell);

                    p = new Paragraph("Date de l'activité: " + selectedReservation.getDateDebutR(), fonte);
                    cell = new PdfPCell(p);
                    cell.setBorder(Rectangle.BOX);
                    cell.setPadding(10);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                    p = new Paragraph("Heure de l'activité: " + selectedReservation.getHeureR(), fonte);
                    cell = new PdfPCell(p);
                    cell.setBorder(Rectangle.BOX);
                    cell.setPadding(10);
                    cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
                    table.addCell(cell);


                    document.add(table);

                    // Ajouter des espaces vides après le tableau
                    document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n"));

                    // Ajouter une autre photo en haut à gauche
                    Image img2 = Image.getInstance("C:\\Users\\Rania\\Desktop\\gesport\\gesport\\src\\main\\resources\\img\\logo.png");
                    img2.scaleToFit(80f, 40f); // Redimensionner l'image
                    img2.setAbsolutePosition(50f, 760f); // Positionner l'image
                    document.add(img2);

                    // Générer le QR code pour le texte de réservation
                    String reservationText = "Activite : " + selectedReservation.getActiviteNom();
                    byte[] qrCodeBytes = QRCodeGenerator.generateQRCodeImage(reservationText, 200, 200);

                    // Ajouter le QR code à votre document PDF
                    Image qrCodeImage = Image.getInstance(qrCodeBytes);
                    qrCodeImage.setAbsolutePosition(200f, 120f); // Positionnez l'image comme vous le souhaitez
                    qrCodeImage.setAlignment(Element.ALIGN_CENTER);
                    document.add(qrCodeImage);
                    // Ajouter une autre photo à droite tout en bas
                   /* Image img3 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\.jpg");
                    img3.scaleAbsolute(100f, 70); // Redimensionner l'image
                    img3.setAbsolutePosition(450f, 50f); // Positionner l'image
                    document.add(img3);*/
                    document.add(new Paragraph("\n\n\n\n\n\n"));

                    Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
                    Paragraph footer = new Paragraph("Centre Sportif, 123 Rue de la Paix, 75000 Ariana,nTel : 99646424 ,Email : info@centresportif.com", footerFont);
                    footer.setAlignment(Element.ALIGN_CENTER);
                    document.add(footer);

                    document.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("PDF Generated");
                    alert.setContentText("Reservation information has been saved to reservations.pdf");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        else {
                // Afficher un message si aucune activité n'est sélectionnée
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Réservation en cours");
                alert.setHeaderText("Réservation en cours");
                alert.setContentText("Veuillez sélectionner une réservation ayant un statut confirmé.");
                alert.showAndWait();
            }
        }else {
            // Afficher un message si aucune activité n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Réservation sélectionnée");
            alert.setHeaderText("Aucune Réservation sélectionnée");
            alert.setContentText("Veuillez sélectionner une réservation à télécharger.");
            alert.showAndWait();
        }
    }
    @FXML
    void accueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(); // Créez une nouvelle instance de Stage
            stage.setScene(new Scene(root));
            stage.setTitle("Interface utilisateur"); // Titre de la fenêtre
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
        stage.setTitle("Restaurant");

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
        stage.setTitle("Restaurant");

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
        stage.setTitle("Restaurant");

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

