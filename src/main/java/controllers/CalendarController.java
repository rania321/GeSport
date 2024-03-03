package controllers;

import entities.CalendarTournoi;
import entities.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.TournoiService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CalendarController implements Initializable {

    Date dateFocus;
    Date today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    private final TournoiService tournoiService = new TournoiService();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = new Date();
        today = new Date();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        // Décrémenter d'un mois
        Calendar cal = Calendar.getInstance(); // Renommage de la variable
        cal.setTime(dateFocus);
        cal.add(Calendar.MONTH, -1);
        dateFocus = cal.getTime();
        calendar.getChildren().clear(); // Effacer les enfants
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        // Incrémenter d'un mois
        Calendar cal = Calendar.getInstance(); // Renommage de la variable
        cal.setTime(dateFocus);
        cal.add(Calendar.MONTH, 1);
        dateFocus = cal.getTime();
        calendar.getChildren().clear(); // Effacer les enfants
        drawCalendar();
    }

    private void drawCalendar() {
        // Mettre à jour l'année et le mois affichés
        Calendar calendarInstance = Calendar.getInstance();
        calendarInstance.setTime(dateFocus);
        year.setText(String.valueOf(calendarInstance.get(Calendar.YEAR)));
        month.setText(String.valueOf(calendarInstance.get(Calendar.MONTH) + 1));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        Map<Integer, List<Tournoi>> calendarActivityMapD = getCalendarTournoiDD(dateFocus);
        Map<Integer, List<Tournoi>> calendarActivityMapF = getCalendarTournoiDF(dateFocus);

        // Récupérer le nombre de jours dans le mois
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(dateFocus);
        int monthMaxDate = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Déterminer le décalage pour le premier jour du mois
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int dateOffset = tempCal.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.DARKBLUE);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<Tournoi> tournoisDebut = calendarActivityMapD.get(currentDate);
                        List<Tournoi> tournoisFin = calendarActivityMapF.get(currentDate);

                        if (tournoisDebut != null) {
                            createCalendarActivity(tournoisDebut, rectangleHeight, rectangleWidth, stackPane);
                        }
                        if (tournoisFin != null) {
                            createCalendarActivity(tournoisFin, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    tempCal.set(Calendar.DAY_OF_MONTH, currentDate);
                    if (tempCal.getTime().equals(today)) {
                        rectangle.setStroke(Color.RED);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private Map<Integer, List<Tournoi>> getCalendarTournoiDD(Date dateFocus) {
        List<Tournoi> allTournois = tournoiService.readAll();
        Map<Integer, List<Tournoi>> calendarActivityMap = new HashMap<>();

        // Convertir la date en calendrier pour extraire le mois et l'année
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFocus);
        int month = calendar.get(Calendar.MONTH) + 1; // Janvier est 0, donc on ajoute 1
        int year = calendar.get(Calendar.YEAR);

        // Parcourir tous les tournois de la base de données
        for (Tournoi tournoi : allTournois) {
            // Convertir la date de début du tournoi en calendrier pour extraire le mois et l'année
            Calendar tournoiCalendar = Calendar.getInstance();
            tournoiCalendar.setTime(tournoi.getDateDebutT());

            int tournoiMonth = tournoiCalendar.get(Calendar.MONTH) + 1;
            int tournoiYear = tournoiCalendar.get(Calendar.YEAR);

            // Vérifier si le tournoi se déroule dans le mois en cours
            if (tournoiMonth == month && tournoiYear == year) {
                // Extraire le jour du mois
                int dayOfMonth = tournoiCalendar.get(Calendar.DAY_OF_MONTH);

                // Vérifier si la liste des tournois pour ce jour existe déjà
                if (!calendarActivityMap.containsKey(dayOfMonth)) {
                    calendarActivityMap.put(dayOfMonth, new ArrayList<>());
                }

                // Ajouter le tournoi à la liste correspondante
                calendarActivityMap.get(dayOfMonth).add(tournoi);
            }
        }

        return calendarActivityMap;
    }

    private Map<Integer, List<Tournoi>> getCalendarTournoiDF(Date dateFocus) {
        List<Tournoi> allTournois = tournoiService.readAll();
        Map<Integer, List<Tournoi>> calendarActivityMap = new HashMap<>();

        // Convertir la date en calendrier pour extraire le mois et l'année
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFocus);
        int month = calendar.get(Calendar.MONTH) + 1; // Janvier est 0, donc on ajoute 1
        int year = calendar.get(Calendar.YEAR);

        // Parcourir tous les tournois de la base de données
        for (Tournoi tournoi : allTournois) {
            // Convertir la date de fin du tournoi en calendrier pour extraire le mois et l'année
            Calendar tournoiCalendar = Calendar.getInstance();
            tournoiCalendar.setTime(tournoi.getDateFinT());

            int tournoiMonth = tournoiCalendar.get(Calendar.MONTH) + 1;
            int tournoiYear = tournoiCalendar.get(Calendar.YEAR);

            // Vérifier si le tournoi se déroule dans le mois en cours
            if (tournoiMonth == month && tournoiYear == year) {
                // Extraire le jour du mois
                int dayOfMonth = tournoiCalendar.get(Calendar.DAY_OF_MONTH);

                // Vérifier si la liste des tournois pour ce jour existe déjà
                if (!calendarActivityMap.containsKey(dayOfMonth)) {
                    calendarActivityMap.put(dayOfMonth, new ArrayList<>());
                }

                // Ajouter le tournoi à la liste correspondante
                calendarActivityMap.get(dayOfMonth).add(tournoi);
            }
        }

        return calendarActivityMap;
    }

    private void createCalendarActivity(List<Tournoi> tournois, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < tournois.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    // Clic sur "..." affiche tous les tournois pour la date donnée
                    System.out.println(tournois);
                });
                break;
            }
            Tournoi tournoi = tournois.get(k);
            Text nomTournoi = new Text();

            // Ajuster la police et la taille du texte
            nomTournoi.setText(tournoi.getNomT());
            nomTournoi.setFont(Font.font("Arial", FontWeight.BOLD, 9)); // Exemple : police Arial, gras, taille 14

            // Vérifier si la date de focus est entre la date de début et de fin du tournoi
            if (dateFocus.compareTo(tournoi.getDateDebutT()) >= 0 && dateFocus.compareTo(tournoi.getDateFinT()) <= 0) {
                nomTournoi.setText("Fin: " + tournoi.getNomT());
            } else {
                nomTournoi.setText(tournoi.getNomT());
            }

            calendarActivityBox.getChildren().add(nomTournoi);

            nomTournoi.setOnMouseClicked(mouseEvent -> {
                // Clic sur le nom du tournoi (début)
                System.out.println("Nom du tournoi : " + tournoi.getNomT());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.1);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.1);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.1);
        calendarActivityBox.setStyle("-fx-background-color:#ef7a55");
        stackPane.getChildren().add(calendarActivityBox);
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