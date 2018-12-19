package com.weather.queryapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@AllArgsConstructor
public class WeatherValues {
	private @JsonProperty("temp") double temp;
	private @JsonProperty("temp_min") double minTemp;
	private @JsonProperty("temp_max") double maxTemp;
	private @JsonProperty("pressure") double pressure;
	private @JsonProperty("humidity") double humidity;
	
	public WeatherValues() {
		
	}
}
