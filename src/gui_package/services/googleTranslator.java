package gui_package.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class googleTranslator {

    private static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String translate(String text, String targetLanguage) {
        try {
            text = text.trim();

            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=" + targetLanguage + "&dt=t&q=" + encodeValue(text);
            URL apiUrl = URI.create(url).toURL();

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            // Xử lý phản hồi JSON
            return processTranslationResponse(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String processTranslationResponse(String jsonResponse) {
        Gson gson = new Gson();

        // Phân tích JSON thành một JsonArray, ta lấy phần tử đầu tiên vì nó chứa từ đã được dịch
        JsonArray jsonArray = gson.fromJson(jsonResponse, JsonArray.class).get(0).getAsJsonArray();

        // API có thể tách câu được dịch ra làm các thành phần nhỏ
        // Do đó, JsonArray chứa nhiều Elements, mỗi Element lại là một JsonArray con
        // Trong đó, phần tử đầu tiên của chúng là cụm từ đã được dịch, ta trích xuất chúng
        StringBuilder translation = new StringBuilder();
        for (JsonElement miniArray : jsonArray) {
            if (miniArray != null) {
                translation.append(miniArray.getAsJsonArray().get(0).getAsString());
            }
        }

        return translation.toString();
    }
}