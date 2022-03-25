package oril.company.external.api;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import org.springframework.stereotype.Component;

@Component
public class ApiConnection {
    private static final int LAST_HOURS = 24;
    private static final int MAX_RESP_ARR_SIZE = 100;

    public Map<Integer, Double> createdAt(String currencyName) {
        Map<Integer, Double> resultMap = new HashMap<>();
        HttpURLConnection connection = null;
        String query = "https://cex.io/api/price_stats/" + currencyName + "/USD";
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("lastHours", LAST_HOURS)
                    .add("maxRespArrSize", MAX_RESP_ARR_SIZE)
                    .build();
            connection.getOutputStream().write(jsonObject.toString()
                    .getBytes(StandardCharsets.UTF_8));
            connection.getOutputStream().close();
            resultMap = streamReader(connection.getInputStream());
        } catch (NullPointerException | IOException e) {
            new RuntimeException("Can't make a connection!", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return resultMap;
    }

    private Map<Integer, Double> streamReader(InputStream stream) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream,
                StandardCharsets.UTF_8))) {
            String readeLine;
            while ((readeLine = bufferedReader.readLine()) != null) {
                response.append(readeLine.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read a data from a stream "
                    + "in ApiConnection.class", e);
        }
        ApiModel[] apiModel = new Gson().fromJson(response.toString(), ApiModel[].class);
        Map<Integer,Double> priceStaticMap = new HashMap<>();
        for (ApiModel model: apiModel) {
            priceStaticMap.put(model.getTmsp(), model.getPrice());
        }
        return priceStaticMap;
    }
}
