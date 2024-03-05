package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CaptchaController implements Initializable {
    @FXML
    private Label captchaLabel;

    @FXML
    private TextField captchaCode;

    @FXML
    private Label msg;

    @FXML
    private Button refreshCaptcha;

    private String captcha;

    @FXML
    void onRefreshCaptcha(ActionEvent event) {
        captcha = generateCaptcha();
        captchaLabel.setText(captcha);
        captchaCode.setText("");
    }

    @FXML
    void onSubmit(ActionEvent event) {
        if (captchaCode.getText().equalsIgnoreCase(captcha)) {
            msg.setText("You have entered the correct code!");
            msg.setTextFill(Color.GREEN);

            // Fermer la fenêtre modale du captcha
            Stage stage = (Stage) captchaCode.getScene().getWindow();
            stage.close();
        } else {
            msg.setText("You have entered the wrong code");
            msg.setTextFill(Color.RED);
            captcha = generateCaptcha();
            captchaLabel.setText(captcha);
            captchaCode.setText("");
        }
    }

    private String generateCaptcha() {
        char[] data = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's','t', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
                'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        int max = data.length - 1;
        int min = 0;
        StringBuilder captcha = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
            captcha.append(data[rand]);
        }

        System.out.println(captcha.toString());
        return captcha.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/refresh.png")).toExternalForm());
        refreshCaptcha.setGraphic(imageView);

        captcha = generateCaptcha();
        captchaLabel.setText(captcha);
    }
}