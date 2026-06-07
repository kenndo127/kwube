package com.kenneth.kwube.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.*;
import com.kenneth.kwube.dto.response.ResponseDto;
import com.kenneth.kwube.dto.response.WeatherResponseDto;
import com.kenneth.kwube.services.AIService;
import com.kenneth.kwube.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final WeatherService weatherService;

    @Value("${kwube.gemini.key")
    private String geminiApiKey;

    private static WeatherService staticWeatherService;

    @PostConstruct
    public void init(){
        staticWeatherService = this.weatherService;
    }

    public static String getCurrentWeather(String location) {
        WeatherResponseDto response = staticWeatherService.getCurrentWeather(location);
        return "Location: " + response.getLocation().getName()
                + ", " + response.getLocation().getCountry()
                + " | Temperature: " + response.getCurrent().getTempC() + "°C"
                + " | Condition: " + response.getCurrent().getCondition().getText();
    }

    @Override
    public ResponseDto chat(String igboText) {
        try(Client client = Client.builder().apiKey(geminiApiKey).build()){
            Method weatherMethod = AIServiceImpl.class.getMethod("getCurrentWeather", String.class);
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .tools(Tool.builder().functions(weatherMethod))
                    .systemInstruction(Content.fromParts(Part.fromText("You are a helpful assistant. " +
                            "The user will send you text in Igbo language. " +
                            "Translate it to English, understand the intention, " +
                            "and respond appropriately. " +
                            "If the intention is weather-related, use the getCurrentWeather tool. " +
                            "If it is conversational, reply naturally. " +
                            "Always respond in this exact JSON format: " +
                            "{\"igboText\": \"<original>\", \"translation\": \"<english>\", " +
                            "\"intention\": \"<weather or conversation>\", \"result\": \"<your response>\"}")))
                    .responseMimeType("application/json")
                    .build();

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3.5-flash", igboText, config);
            String json = response.text();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ResponseDto.class);

        } catch (Exception e){
            return ResponseDto.builder() //write a custom exception for this
                    .igboText(igboText)
                    .translation("error")
                    .intention("error")
                    .result("Something went wrong: " + e.getMessage())
                    .build();
        }
    }
}
