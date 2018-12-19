package com.weather.api;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class APIResponse {
	private List<APIResponseAverage> averages;
	
	public APIResponse() {
		this.averages = new ArrayList<>();
	}
	
}
