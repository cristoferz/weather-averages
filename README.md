# Weather Averages for next 3 days

This project is meant to calculate the average temperature and pressure for the next 3 days on the specified city through an API.

# Running

This application is bundled with the maven wrapper, so it's possible to run it using this command or using already installed `mvn`:

```
./mvnw spring-boot:run
```

This will start the web application listening on port 8080. 

# Testing

For testing the application you can run the following command:

```
./mvnw test
```

It will run the following tests:
- SmokeTest: Simple test to ensure the controller is created
- HttpRequestTest: Test Http layer to ensure web server is fully created
- ApplicationTest: Test application layer 
- WeatherServiceTest: Test Weather Service to ensure that healthChecker and average weather calculations are working as expected.

# Decisions

Spring Boot was used to implementing this application according to recommendations and because all necessary tools can be easily implemented using it. 

To access the OpenWeatherMap API was used Spring RestTemplate.

To create the REST web service was used the Spring RestController.

Every request to the API starts a request to OpenWeatherMap 5 days / 3 hours forecast with information for the next 4 days, to ensure that the requested period will be covered and to reduce the response data. This information is then processed using Streams API to calculate the necessary averages for the next 3 days from the date of request and the result is returned back through the API.