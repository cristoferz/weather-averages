package com.weather.api;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.weather.queryapi.WeatherAPIResponse;

@Service
public class WeatherService {
	private static final String OWM_URL = "https://api.openweathermap.org/data/2.5/forecast";
	private static final String OWM_API = "07c5b9d6f3566e2e7eb576ec175af91e";
	private RestTemplate restTemplate;
	public WeatherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public WeatherAPIResponse getAPIResponse(String city) {
		try {
			return restTemplate.getForObject(OWM_URL+"?q="+city+"&cnt=32&APPID="+OWM_API+"&units=metric", WeatherAPIResponse.class);
		} catch(HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new WeatherCityNotFoundException(city);
			} else {
				throw ex;
			}
		}
	}
	
	public APIResponse loadData(WeatherAPIResponse response) {
		APIResponse result = new APIResponse();
		LocalDateTime localDateTime;
		if (!response.getList().isEmpty()) {
			localDateTime = response.getList().get(0).getDate().toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();  
		} else {
			localDateTime = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
		}
				
		for(int i=0;i<3;i++) {
			APIResponseAverage avg = new APIResponseAverage(Date.from(localDateTime.plusDays(i).toInstant(ZoneOffset.UTC)));
			avg.setDailyAvgTemperature(response.getList().stream()
					.filter(value->avg.isDaily(value.getDate()))
					.mapToDouble(value->value.getValues().getTemp())
					.average()
					.orElse(0));
			avg.setDailyAvgPressure(response.getList().stream()
					.filter(value->avg.isDaily(value.getDate()))
					.mapToDouble(value->value.getValues().getPressure())
					.average()
					.orElse(0));
			avg.setNightlyAvgTemperature(response.getList().stream()
					.filter(value->avg.isNightly(value.getDate()))
					.mapToDouble(value->value.getValues().getTemp())
					.average()
					.orElse(0));
			avg.setNightlyAvgPressure(response.getList().stream()
					.filter(value->avg.isNightly(value.getDate()))
					.mapToDouble(value->value.getValues().getPressure())
					.average()
					.orElse(0));
			result.getAverages().add(avg);
		}
		return result;
	}
	
	public String healthChecker() {
		return "OK";
	}
}
