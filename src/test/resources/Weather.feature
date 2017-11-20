Feature: Test of weather api
  Scenario: Test town coordinates
    Given City name is London
    When Request weather information
    Then Coordinates are lon: -0.13 and lat: 51.51
    Then Weathers size is 1
    Then Weather with index 0 has id: 300, main: Drizzle, description: light intensity drizzle, icon: 09d
    Then Base is stations
    Then Main has temp: 280.32, pressure: 1012, humidity: 81, temp_min: 279.15, temp_max: 281.15
    Then Visibility is 10000
    Then Wind has speed: 4.1, deg: 80
    Then Dt is 1485789600
    Then Sys has type: 1, id: 5091, message: 0.0103, country: GB, sunrise: 1485762037, sunset: 1485794875
    Then Id is 2643743
    Then Name is London
    Then Cod is 200