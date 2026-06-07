package com.kenneth.kwube.controller;

import com.kenneth.kwube.dto.request.RequestDto;
import com.kenneth.kwube.dto.response.ResponseDto;
import com.kenneth.kwube.dto.response.WeatherResponseDto;
import com.kenneth.kwube.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class kwubeController {

    private final WeatherService weatherService;

    @PostMapping("/kwube")
    public ResponseEntity<ResponseDto> parseInput(
            @RequestBody RequestDto payload
    ){
        return null;
    }

    @GetMapping("/weather/current")
    public ResponseEntity<WeatherResponseDto> getCurrentWeather(@RequestParam String location) {
        WeatherResponseDto response = weatherService.getCurrentWeather(location);
        return ResponseEntity.ok(response);
    }
}
