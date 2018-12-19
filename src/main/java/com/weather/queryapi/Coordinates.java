package com.weather.queryapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class Coordinates {
	
	private @JsonProperty("lat") double latitude;
	private @JsonProperty("lon") double longitude;
	
	public Coordinates() {
	}
}
