package controllers;
import Services.UserService;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

//import javax.imageio.IIOException;
import java.io.IOException;
import java.sql.SQLException;

public class AjoutPersonneController {
    private final UserService us = new UserService();
    @FXML
    private TextField emailU;

    @FXML
    private TextField mdpU;

    @FXML
    private TextField nomU;

    @FXML
    private TextField prenomU;

    @FXML
    private TextField roleU;

    @FXML
    void ajouter(ActionEvent event) {
        try {
            us.add(new User(nomU.getText(), prenomU.getText(), emailU.getText(), mdpU.getText(), roleU.getText()));
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            try {
                Parent root=loader.load();
                AfficherUserController au=loader.getController();
                au.setNomU(nomU.getText());
                au.setPrenomU(prenomU.getText());
                au.setEmailU(emailU.getText());
                au.setMdpU(mdpU.getText());
                au.setRoleU(roleU.getText());
                au.setListU(us.readall().toString());
                nomU.getScene().setRoot(root);

            }
            catch (IOException e) {
                System.out.println(e);
            }

        } catch(Exception ex) { // Attraper une exception plus générale
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

}

