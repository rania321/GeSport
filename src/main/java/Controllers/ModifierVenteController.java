package controllers;
import entities.Produit;
import entities.Vente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.example.Service.ProduitService;
import org.example.Service.VenteService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
public class ModifierVenteController {
    @FXML
    private Button modification;

    @FXML
    private TextField montantV;

    @FXML
    private TextField quantiteV;
    VenteService vs = new VenteService();
    Date utilDate = new Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    private int idv;

    public void setVenteId(int id) {
        this.idv = id;
        // Vous pouvez effectuer d'autres opérations liées à la venteId si nécessaire
        System.out.println("ID de la vente dans le nouveau contrôleur : " + idv);

    }

    public void modifier(ActionEvent actionEvent) {
        if (!vs.isNumeric(montantV.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour le prix !");
            alert.showAndWait();
            return;
        }
        if (!vs.isNumericInt(quantiteV.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour la quantité !");
            alert.showAndWait();
            return;
        }
        float montant =Float.parseFloat(montantV.getText());
        int quantite = Integer.parseInt(quantiteV.getText());
        Vente v = new Vente();

        v.setIdV(this.idv);
        v.setMontantV(montant);
        v.setQuantitéV(quantite);
        v.setDateV(sqlDate);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de modification");
        alert.setHeaderText("Modifier la réservation");
        alert.setContentText("Êtes-vous sûr de vouloir modifier la réservation sélectionnée ?");

        Optional<ButtonType> result = alert.showAndWait();
        // Si l'utilisateur confirme la suppression, procéder
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Mettre à jour la séance dans la base de données
            vs.update(v);
        }
    }
}
