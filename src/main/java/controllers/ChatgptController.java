package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatgptController {
    public TextArea inputTextArea;
    public Label responseLabel;

    private static final String API_KEY = "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";
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL_NAME = "gpt-3.5-turbo-1106";

    @FXML
    private void sendMessage() {
        String userMessage = inputTextArea.getText();


        // Envoyer une requête à l'API de ChatGPT
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_ENDPOINT))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString("{\"model\": \"" + MODEL_NAME + "\", \"prompt\": \"" + userMessage + "\"}"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Traiter la réponse de l'API
            String chatGPTResponse = response.body();
            responseLabel.setText(chatGPTResponse);

        } catch (IOException | InterruptedException e) {
            // Gérer les erreurs de manière appropriée
            e.printStackTrace();
        }
    }
}

