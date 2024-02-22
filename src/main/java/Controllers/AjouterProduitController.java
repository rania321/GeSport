package Controllers;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.Service.ProduitService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AjouterProduitController {

    @FXML
    private TextField desctf;

    @FXML
    private TextField imagetf;

    @FXML
    private TextField nomtf;

    @FXML
    private TextField prixtf;

    @FXML
    private TextField qtf;
    @FXML
    private TextField referencetf;


    @FXML
    private Button boutonajp;

    private final ProduitService ps = new ProduitService();

    Date utilDate = new Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    @FXML
    void ajouterp(ActionEvent event)  throws IOException {
        String nomt = nomtf.getText();
        String descri = desctf.getText();
        float prix = Float.parseFloat(prixtf.getText());
        int qt = Integer.parseInt(qtf.getText());
        String imaget = imagetf.getText();
        int ref = Integer.parseInt(referencetf.getText());

        if (nomt.isEmpty() || descri.isEmpty()  || imaget.isEmpty()) {
            // Afficher un message d'erreur si les champs obligatoires ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Information manquante !");
            alert.showAndWait();
            return;
        }
        ps.add(new Produit(nomt,descri,prix, qt, sqlDate, imaget,ref ));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml") );
        Parent root = loader.load();
    }}

