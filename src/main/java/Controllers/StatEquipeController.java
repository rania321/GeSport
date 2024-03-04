package controllers;
import entities.Equipe;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;

public class StatEquipeController {

    public void initialize() {
        // Initialisation de l'interface utilisateur, si nécessaire
    }

    public void setData(List<Equipe> equipes, AnchorPane anchorPane) {
        // Créer un jeu de données pour les équipes
        DefaultCategoryDataset dataset = createDataset(equipes);

        // Créer le graphique à barres
        JFreeChart barChart = ChartFactory.createBarChart(
                "Statistiques des équipes",
                "Type", "Nombre",
                dataset
        );

        // Convertir le graphique JFreeChart en un graphique JavaFX
        BarChart<String, Number> fxBarChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        fxBarChart.setTitle(barChart.getTitle().getText());

        // Récupérer les séries de données du plot du graphique JFreeChart
        org.jfree.data.category.CategoryDataset jfreeDataset = barChart.getCategoryPlot().getDataset();
        int seriesCount = jfreeDataset.getRowCount();
        for (int i = 0; i < seriesCount; i++) {
            String seriesKey = (String) jfreeDataset.getRowKey(i);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(seriesKey);
            int itemCount = jfreeDataset.getColumnCount();
            for (int j = 0; j < itemCount; j++) {
                String category = (String) jfreeDataset.getColumnKey(j);
                Number value = jfreeDataset.getValue(i, j);
                series.getData().add(new XYChart.Data<>(category, value));
            }
            fxBarChart.getData().add(series);
        }

        // Ajouter le graphique JavaFX au conteneur JavaFX
        anchorPane.getChildren().add(fxBarChart);
    }

    private DefaultCategoryDataset createDataset(List<Equipe> equipes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int inscrites = 0;
        int qualifiees = 0;
        int eliminees = 0;

        // Parcourir les équipes pour calculer les statistiques
        for (Equipe equipe : equipes) {
            String statut = equipe.getStatutE();
            switch (statut) {
                case "inscrite":
                    inscrites++;
                    break;
                case "Qualifiée":
                    qualifiees++;
                    break;
                case "Éliminée":
                    eliminees++;
                    break;
                default:
                    // Ignorer les autres statuts
                    break;
            }
        }

        // Ajouter les données au jeu de données
        dataset.addValue(inscrites, "Inscrite", "Équipes");
        dataset.addValue(qualifiees, "Qualifiée", "Équipes");
        dataset.addValue(eliminees, "Éliminée", "Équipes");

        return dataset;
    }
}