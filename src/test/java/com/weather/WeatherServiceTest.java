package com.weather;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weather.api.APIResponse;
import com.weather.api.WeatherService;
import com.weather.queryapi.WeatherAPIResponse;
import com.weather.queryapi.WeatherData;
import com.weather.queryapi.WeatherValues;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceTest {
	
	@Autowired
	private WeatherService service;

	@Test
	public void healthCheck() throws Exception {
		assertThat(service.healthChecker()).contains("OK");
	}
	
	@Test
	public void simpleCase() throws Exception {
		WeatherAPIResponse data = new WeatherAPIResponse();
		data.setList(new ArrayList<>());
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
		// Day 1 - Daily
		data.getList().add(new WeatherData(new WeatherValues(10, 5, 15, 1000, 10), Date.from(now.toLocalDate().atTime(7, 0).toInstant(ZoneOffset.UTC))));
		data.getList().add(new WeatherData(new WeatherValues(20, 15, 25, 800, 10), Date.from(now.toLocalDate().atTime(17, 0).toInstant(ZoneOffset.UTC))));
		// Day 1 - Nightly
		data.getList().add(new WeatherData(new WeatherValues(5, 1, 8, 915.3d, 10), Date.from(now.toLocalDate().atTime(22, 0).toInstant(ZoneOffset.UTC))));
		// Day 2 - Daily (No data)
		// Day 2 - Nightly (Border time 6h must affect 2 periods)
		data.getList().add(new WeatherData(new WeatherValues(5, 1, 8, 500, 10), Date.from(now.toLocalDate().plusDays(2).atTime(6, 0).toInstant(ZoneOffset.UTC))));
		// Day 3 - Daily (Border time 18h must affect 2 periods)
		data.getList().add(new WeatherData(new WeatherValues(15, 1, 8, 1000, 10), Date.from(now.toLocalDate().plusDays(2).atTime(18, 0).toInstant(ZoneOffset.UTC))));
		
		
		
		
		APIResponse response = service.loadData(data);
		
		// Day 1 - Daily
		assertThat(response.getAverages().get(0).getDailyAvgTemperature()).isEqualTo(15);
		assertThat(response.getAverages().get(0).getDailyAvgPressure()).isEqualTo(900);
		// Day 1 - Nightly
		assertThat(response.getAverages().get(0).getNightlyAvgTemperature()).isEqualTo(5);
		assertThat(response.getAverages().get(0).getNightlyAvgPressure()).isEqualTo(915.3);
		// Day 2 - Daily (must return 0)
		assertThat(response.getAverages().get(1).getDailyAvgTemperature()).isEqualTo(0);
		assertThat(response.getAverages().get(1).getDailyAvgPressure()).isEqualTo(0);
		
		// Day 2 - Nightly
		assertThat(response.getAverages().get(1).getNightlyAvgTemperature()).isEqualTo(5);
		assertThat(response.getAverages().get(1).getNightlyAvgPressure()).isEqualTo(500);
		
		// Day 3 - Daily
		assertThat(response.getAverages().get(2).getDailyAvgTemperature()).isEqualTo(10);
		assertThat(response.getAverages().get(2).getDailyAvgPressure()).isEqualTo(750);
		
	}
}
