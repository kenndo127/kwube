package com.kenneth.kwube.services;

import com.kenneth.kwube.dto.response.WeatherResponseDto;

public interface WeatherService {
    WeatherResponseDto getCurrentWeather(String location);
}
