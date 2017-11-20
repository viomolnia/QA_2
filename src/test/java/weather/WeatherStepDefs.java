package weather;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import weather.model.*;

import java.io.IOException;
import java.math.BigDecimal;

public class WeatherStepDefs {
    private String city;
    private WeatherRequester weatherRequester = new WeatherRequester();
    private WeatherResponse weatherResponse;

    @Given("City name is (.*)")
    public void set_city_name (String cityName) {
        city = cityName;
    }

    @When("Request weather information")
    public void request_weather_information () throws IOException {
        weatherResponse = weatherRequester.getWeather(city);
    }

    @Then("Coordinates are lon: (.*) and lat: (.*)")
    public void check_coordinates (BigDecimal lon, BigDecimal lat) {
        Assert.assertEquals(lon, weatherResponse.getCoord().getLon());
        Assert.assertEquals(lat, weatherResponse.getCoord().getLat());
    }

    @Then("Weathers size is (.*)")
    public void check_weathers_size (int size) {
        Assert.assertEquals(weatherResponse.getWeather().size(), size);
    }

    @Then("Weather with index (.*) has id: (.*), main: (.*), description: (.*), icon: (.*)")
    public void check_weather_by_idx (int index, Long id, String main, String description, String icon) {
        Weather weather = weatherResponse.getWeather().get(index);
        Assert.assertEquals(id, weather.getId());
        Assert.assertEquals(main, weather.getMain());
        Assert.assertEquals(description, weather.getDescription());
        Assert.assertEquals(icon, weather.getIcon());
    }

    @Then("Base is (.*)")
    public void check_base (String base) {
        Assert.assertEquals(weatherResponse.getBase(), base);
    }

    @Then("Main has temp: (.*), pressure: (.*), humidity: (.*), temp_min: (.*), temp_max: (.*)")
    public void check_main (BigDecimal temp, int pressure, int humidity, BigDecimal tempMin, BigDecimal tempMax) {
        Main main = weatherResponse.getMain();
        Assert.assertEquals(main.getTemp(), temp);
        Assert.assertEquals(main.getPressure(), pressure);
        Assert.assertEquals(main.getHumidity(), humidity);
        Assert.assertEquals(main.getTemp_min(), tempMin);
        Assert.assertEquals(main.getTemp_max(), tempMax);
    }

    @Then("Visibility is (.*)")
    public void check_visibility (int visibility) {
        Assert.assertEquals(weatherResponse.getVisibility(), visibility);
    }

    @Then("Wind has speed: (.*), deg: (.*)")
    public void check_wind (BigDecimal speed, int deg) {
        Wind wind = weatherResponse.getWind();
        Assert.assertEquals(wind.getSpeed(), speed);
        Assert.assertEquals(wind.getDeg(), deg);
    }

    @Then("Dt is (.*)")
    public void check_dt (String dt) {
        Assert.assertEquals(weatherResponse.getDt(), dt);
    }

    @Then("Sys has type: (.*), id: (.*), message: (.*), country: (.*), sunrise: (.*), sunset: (.*)")
    public void check_sys (int type, Long id, BigDecimal message, String country, String sunrise, String sunset) {
        Sys sys = weatherResponse.getSys();
        Assert.assertEquals(sys.getType(), type);
        Assert.assertEquals(sys.getId(), id);
        Assert.assertEquals(sys.getMessage(), message);
        Assert.assertEquals(sys.getCountry(), country);
        Assert.assertEquals(sys.getSunrise(), sunrise);
        Assert.assertEquals(sys.getSunset(), sunset);
    }

    @Then("Id is (.*)")
    public void check_id (Long id) {
        Assert.assertEquals(weatherResponse.getId(), id);
    }

    @Then("Name is (.*)")
    public void check_name (String name) {
        Assert.assertEquals(weatherResponse.getName(), name);
    }

    @Then("Cod is (.*)")
    public void check_cod (int cod) {
        Assert.assertEquals(weatherResponse.getCod(), cod);
    }

}
