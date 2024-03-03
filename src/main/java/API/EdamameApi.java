package API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class EdamameApi {
public static int apii(String q, double qte){
    try {
        String apiKey = "aa80e10a8cf28d62a7d0f73334229f29";
        String appId = "ac176870";
        String query = q;
        double quantity = qte; // Spécifiez la quantité en grammes, on prend ici 100g à titre d'exemple

        // Encodage de l'ingrédient pour l'URL
        String encodedIngredient = URLEncoder.encode(query, "UTF-8");

        // Construire l'URL avec la quantité spécifiée
        String urlString = "https://api.edamam.com/api/nutrition-data?app_id=" + appId + "&app_key=" + apiKey + "&ingr=" + quantity + "g%20" + encodedIngredient;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Analyser la réponse JSON
            System.out.println(response.toString());

            // Extraire le nombre de calories si disponible
            int calories = extractCalories(response.toString());
            System.out.println("Nombre de calories : " + calories);
            return calories;
        } else {
            System.out.println("Erreur HTTP : " + responseCode);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1;
}

    private static int extractCalories(String jsonResponse) {
        // Utilisez Gson pour analyser la réponse JSON
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonResponse).getAsJsonObject();

        // Accédez à la propriété "calories" dans la structure JSON
        int calories = jsonObject.getAsJsonPrimitive("calories").getAsInt();

        return calories;
    }
}

