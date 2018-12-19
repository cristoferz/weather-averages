package com.weather.api;

public class WeatherCityNotFoundException extends RuntimeException {

	public WeatherCityNotFoundException(String city) {
		super("Weather for city "+city+" not found.");
	}

}
