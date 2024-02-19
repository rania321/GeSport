package controllers;



import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AfficherUserController {

    @FXML
    private TextField emailU;

    @FXML
    private TextField listU;

    @FXML
    private TextField mdpU;

    @FXML
    private TextField nomU;

    @FXML
    private TextField prenomU;

    @FXML
    private TextField roleU;

    public void setEmailU(String emailU) {
        this.emailU.setText(emailU);
    }

    public void setListU(String listU) {
        this.listU.setText(listU);
    }

    public void setMdpU(String mdpU) {
        this.mdpU.setText(mdpU);
    }

    public void setNomU(String nomU) {
        this.nomU.setText(nomU);
    }

    public void setPrenomU(String prenomU) {
        this.prenomU.setText(prenomU);
    }

    public void setRoleU(String roleU) {
        this.roleU.setText(roleU);
    }
}

