package com.weather.queryapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class City {
	private Long id;
	private String name;
	private @JsonProperty("coord") Coordinates coordinates;
	private String country;
	private int population;
	
	public City() {
		
	}
}
