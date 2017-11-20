package weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import weather.model.WeatherResponse;

import java.io.IOException;

class WeatherRequester {
    private String weatherURL = "http://samples.openweathermap.org/data/2.5/weather?q=";
    private String key = ",uk&appid=b1b15e88fa797225412429c1c50c122a1";

    WeatherResponse getWeather(String city) throws IOException {
        String url = weatherURL + city + key;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonResponse = response.getBody();

        return mapper.readValue(jsonResponse, WeatherResponse.class);
    }
}
