package Controllers;

import entities.Produit;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.MainFX;

import java.awt.event.ActionEvent;

public class ItemsController {

    @FXML
    private ImageView imagep;

    @FXML
    private Label nomp;

    @FXML
    private Label prixp;
    private Produit produit;

    public void setData(Produit produit) {
        this.produit = produit;
        nomp.setText("aaaa");
        //prixp.setText(MainFX.CURRENCY + produit.getPrixP());
       // Image image = new Image(getClass().getResourceAsStream(produit.getImageP()));
       // imagep.setImage(image);
    }

    /*public void setData(Produit produit) {
        this.produit = produit;
        nomp.setText(produit.getNomP());
        prixp.setText(MainFX.CURRENCY + produit.getPrixP());
        Image image = new Image(getClass().getResourceAsStream(produit.getImageP()));
        imagep.setImage(image);
    }*/
}
