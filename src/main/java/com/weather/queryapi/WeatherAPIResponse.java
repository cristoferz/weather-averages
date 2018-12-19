package com.weather.queryapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class WeatherAPIResponse {
	private @JsonProperty("cnt") int lines;
	private City city;
	private List<WeatherData> list;
	
	public WeatherAPIResponse() {
	}
}
