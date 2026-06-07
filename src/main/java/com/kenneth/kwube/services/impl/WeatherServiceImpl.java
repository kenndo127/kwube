package com.kenneth.kwube.services.impl;

import com.kenneth.kwube.client.WeatherApiClient;
import com.kenneth.kwube.dto.response.WeatherResponseDto;
import com.kenneth.kwube.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiClient weatherApiClient;

    @Value("${kwube.weatherapi.key}")
    private String apiKey;

    @Override
    public WeatherResponseDto getCurrentWeather(String location) {
        return weatherApiClient.getCurrentWeather(apiKey, location);
    }
}
