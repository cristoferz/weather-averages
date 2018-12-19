package com.weather.queryapi;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@AllArgsConstructor
public class WeatherData {
	private @JsonProperty("main") WeatherValues values;
	private @JsonProperty("dt_txt") @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date;
	
	public WeatherData() {
		
	}
	
	
}
