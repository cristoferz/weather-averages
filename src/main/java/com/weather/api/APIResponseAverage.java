package com.weather.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class APIResponseAverage {
	private @JsonFormat(pattern="yyyy-MM-dd") Date date;
	private double dailyAvgTemperature;
	private double dailyAvgPressure;
	private double nightlyAvgTemperature;
	private double nightlyAvgPressure;
	
	
	public APIResponseAverage(Date date) {
		super();
		this.date = date;
	}
	
	private LocalDate getLocalDate() {
		return date.toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
	}
	
	public boolean isDaily(Date date) {
		LocalDateTime localDateTime = date.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
		return !getLocalDate().atTime(6, 0).isAfter(localDateTime) &&
				!getLocalDate().atTime(18, 0).isBefore(localDateTime);
	}
	
	public boolean isNightly(Date date) {
		LocalDateTime localDateTime = date.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
		return !getLocalDate().atTime(18, 0).isAfter(localDateTime) &&
			!getLocalDate().plusDays(1).atTime(6, 0).isBefore(localDateTime);
	}
	
		
}
