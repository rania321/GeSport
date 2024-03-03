package API;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SpoonacularApi {

    public static String generateMealPlan(String timeFrame, int targetCalories, String diet, String exclude) {
        try {
            String apiKey = "6c3660fd78e5452da245f1e1a68bc566";
            String endpoint = "https://api.spoonacular.com/mealplanner/generate";

            // Créez l'URL de la requête avec les paramètres
            URL url = new URL(String.format(
                    "%s?apiKey=%s&timeFrame=%s&targetCalories=%d&diet=%s&exclude=%s",
                    endpoint, apiKey, timeFrame, targetCalories, diet, exclude));

            // Ouvrez la connexion HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Définissez la méthode de requête (GET)
            connection.setRequestMethod("GET");

            // Lisez la réponse de l'API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
System.out.println(response.toString());
            // Retournez la réponse de l'API
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
