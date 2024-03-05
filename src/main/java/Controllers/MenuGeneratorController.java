package controllers;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static API.SpoonacularApi.generateMealPlan;

public class MenuGeneratorController {

    @FXML
    private TextField allergie;

    @FXML
    private TextField calorie;

    @FXML
    private Button generer;

    @FXML
    private Label menu;

    @FXML
    private Button link1;

    @FXML
    private Button link2;

    @FXML
    private Button link3;

    @FXML
    private TextField regime;
    private HostServices hostServices;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
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


    public void api(ActionEvent actionEvent) {
        String jsonResponse =generateMealPlan("1", Integer.parseInt(calorie.getText()), regime.getText(), allergie.getText());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String m=" ";
            int i=0;
            String u= " ";
            // Afficher les informations pour chaque repas
            for (JsonNode mealNode : jsonNode.get("meals")) {
                i++;
                String title = mealNode.get("title").asText();
                int readyInMinutes = mealNode.get("readyInMinutes").asInt();
                int servings = mealNode.get("servings").asInt();
                String sourceUrl = mealNode.get("sourceUrl").asText();
                m= m+ i +". \n"+"Titre: " + title + "\n" + "Temps de préparation: " + readyInMinutes + "\n" + "Nombre de personne: " + servings +  "\n \n ";
                System.out.println("Titre: " + title);
                System.out.println("Temps de préparation: " + readyInMinutes);
                System.out.println("Nombre de personne: " + servings);
                System.out.println("Recette compléte: " + sourceUrl);
                System.out.println("------");
                u=u+"\n " + sourceUrl ;
            }

           openRecette(u);

            // Afficher les informations nutritionnelles
            JsonNode nutrientsNode = jsonNode.get("nutrients");
            double calories = nutrientsNode.get("calories").asDouble();
            double protein = nutrientsNode.get("protein").asDouble();
            double fat = nutrientsNode.get("fat").asDouble();
            double carbohydrates = nutrientsNode.get("carbohydrates").asDouble();
            m=m+"Nutriments: " + " \n Calories: " + calories + "\n Proteine: " + protein + "\n Gras: " + fat + "\n Carbohydrates: " + carbohydrates;
            System.out.println("Nutriments:");
            System.out.println("Calories: " + calories);
            System.out.println("Proteine: " + protein);
            System.out.println("Gras: " + fat);
            System.out.println("Carbohydrates: " + carbohydrates);
            menu.setText(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public String getUrl(ActionEvent actionEvent) {
        String jsonResponse =generateMealPlan("1", 2000, "vegetarian", "olives");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String m=" ";
            // Afficher les informations pour chaque repas
            for (JsonNode mealNode : jsonNode.get("meals")) {

                String sourceUrl = mealNode.get("sourceUrl").asText();
                m= m+ "1" +" \n" + sourceUrl + "\n";

                System.out.println("Recette compléte: " + sourceUrl);
                System.out.println("------");
            }

            // Afficher les informations nutritionnelles
            JsonNode nutrientsNode = jsonNode.get("nutrients");
            double calories = nutrientsNode.get("calories").asDouble();
            double protein = nutrientsNode.get("protein").asDouble();
            double fat = nutrientsNode.get("fat").asDouble();
            double carbohydrates = nutrientsNode.get("carbohydrates").asDouble();
            m=m+"\nNutriments: " + " \n Calories: " + calories + "\n Proteine: " + protein + "\n Gras: " + fat + "\n Carbohydrates: " + carbohydrates;
            System.out.println("Nutriments:");
            System.out.println("Calories: " + calories);
            System.out.println("Proteine: " + protein);
            System.out.println("Gras: " + fat);
            System.out.println("Carbohydrates: " + carbohydrates);
            menu.setText(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static List<String> extractUrls(String u) {
        List<String> urls = new ArrayList<>();

        // Divise la chaîne en lignes
        String[] lines = u.split("\n");

        // Parcourt chaque ligne pour extraire l'URL
        for (String line : lines) {
            // Ignore les lignes vides
            if (!line.trim().isEmpty()) {
                // Ajoute l'URL à la liste
                urls.add(line.trim());
            }
        }

        return urls;
    }

    @FXML
    void openRecette(String url) {
        List<String> extractedUrls = extractUrls(url);

        // Affiche les URLs extraites
        for (String urll : extractedUrls) {
            System.out.println(urll);
        }
        if (extractedUrls.size() >= 3) {
            // Stocke chaque URL dans une variable distincte
            String url1 = extractedUrls.get(0);
            String url2 = extractedUrls.get(1);
            String url3 = extractedUrls.get(2);

            // Affiche les URLs
            System.out.println("URL 1: " + url1);
            System.out.println("URL 2: " + url2);
            System.out.println("URL 3: " + url3);
            link1.setOnAction(event -> {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI(url1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            link2.setOnAction(event -> {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI(url2));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            link3.setOnAction(event -> {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI(url3));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            }
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private HostServices getHostServices() {
        return this.hostServices;
    }
    private void openLink(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize () {
        //menu.setText(generateMealPlan("1", 2000, "vegetarian", "olives"));
    }
}
