package controller;
import entities.Reservation;
import entities.WeatherData;
import com.google.gson.Gson; // Add Gson library to your project
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherController {

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label clientLabel;

    @FXML
    private TextField cityTextField;

    private static final String API_KEY = "6ea0eeac01b11d2b1556aa2ac966f9eb";
    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    public void initialize() {
        // Nothing to do here for now
    }

    @FXML
    private void handleSearch(ActionEvent event) throws IOException {
        String cityName = cityTextField.getText();

        URL url = new URL(String.format(API_ENDPOINT, cityName, API_KEY));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        JSONObject response = new JSONObject(content.toString());
        String locationName = response.getString("name");
        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");
        double temperature = response.getJSONObject("main").getDouble("temp");


        locationLabel.setText(locationName);
        descriptionLabel.setText(description);
        temperatureLabel.setText(String.format("%.1f °C", temperature - 273.15));
    }
}

