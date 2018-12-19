package com.weather.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.weather.queryapi.WeatherAPIResponse;


@RestController
public class WeatherRestController {
	private WeatherService weatherService;
	
	public WeatherRestController(WeatherService weatherService) {
		super();
		this.weatherService = weatherService;
	}
	
	@GetMapping("/healthcheck")
	String healthCheck() {
		return weatherService.healthChecker();
	}

	@RequestMapping(value="/data", method=RequestMethod.GET, params="CITY")
	ResponseEntity<APIResponse> query(@RequestParam("CITY") String city) {
		try {
			WeatherAPIResponse response = weatherService.getAPIResponse(city);
			APIResponse result = this.weatherService.loadData(response);
			return new ResponseEntity<APIResponse>(result, HttpStatus.OK);
		} catch(HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new WeatherCityNotFoundException(city);
			} else {
				throw ex;
			}
		}
	}
}
