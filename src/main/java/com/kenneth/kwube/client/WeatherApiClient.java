package com.kenneth.kwube.client;

import com.kenneth.kwube.dto.response.WeatherResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather-api", url = "${kwube.weatherapi.baseurl}")
public interface WeatherApiClient {

    @GetMapping("/current.json")
    WeatherResponseDto getCurrentWeather(
            @RequestParam("key") String apiKey,
            @RequestParam("q") String location
    );
}
