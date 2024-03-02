package Controllers;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.example.Service.ProduitService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class ModifierProduitController {

    private int idp;

    @FXML
    private TextField descrim;

    @FXML
    private TextField imagem;

    @FXML
    private Button modification;

    @FXML
    private TextField nomm;

    @FXML
    private TextField prixm;

    @FXML
    private TextField quantitem;
    ProduitService ps = new ProduitService();
    Date utilDate = new Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    public void setProduitId(int id) {
        this.idp = id;
        // Vous pouvez effectuer d'autres opérations liées à la venteId si nécessaire
        System.out.println("ID de la vente dans le nouveau contrôleur : " + idp);
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        String nom = nomm.getText();
        String descr = descrim.getText();
        String image = imagem.getText();
        if (!ps.isNumeric(prixm.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour le prix !");
            alert.showAndWait();
            return;
        }
        if (!ps.isNumericInt(quantitem.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour la quantité !");
            alert.showAndWait();
            return;
        }

        if (nom.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information manquante!");
            alert.setHeaderText(null);
            alert.setContentText("Saisissez un nom !");
            alert.showAndWait();
            return;
        }
            float prix =Float.parseFloat(prixm.getText());
            int stock = Integer.parseInt(quantitem.getText());
            Produit pp = new Produit();
            pp.setIdP(this.idp);
            pp.setNomP(nom);
            pp.setPrixP(prix);
            pp.setStockP(stock);
            pp.setImageP(image);
            pp.setDateAjoutP(sqlDate);
            pp.setDescriP(descr);
            // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de modification");
            alert.setHeaderText("Modifier la réservation");
            alert.setContentText("Êtes-vous sûr de vouloir modifier la réservation sélectionnée ?");

            Optional<ButtonType> result = alert.showAndWait();
            // Si l'utilisateur confirme la suppression, procéder
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Mettre à jour la séance dans la base de données
                ps.update(pp);

            }
        }
    }


