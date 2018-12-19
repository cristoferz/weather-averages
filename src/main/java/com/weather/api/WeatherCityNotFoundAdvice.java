package com.weather.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WeatherCityNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(WeatherCityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String cityNotFoundHandler(WeatherCityNotFoundException ex) {
		return ex.getMessage();
	}
}
