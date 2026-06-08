package com.kenneth.kwube.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.*;
import com.kenneth.kwube.dto.response.ResponseDto;
import com.kenneth.kwube.dto.response.WeatherResponseDto;
import com.kenneth.kwube.exceptions.BadRequestException;
import com.kenneth.kwube.exceptions.GeminiApiException;
import com.kenneth.kwube.exceptions.WeatherApiException;
import com.kenneth.kwube.services.AIService;
import com.kenneth.kwube.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final WeatherService weatherService;

    @Value("${kwube.geminiapi.key}")
    private String geminiApiKey;

    @Override
    public ResponseDto chat(String igboText) {

        if(igboText.isBlank()) throw new BadRequestException("Invalid Request Format");

        try(Client client = Client.builder().apiKey(geminiApiKey).build()){
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .systemInstruction(Content.fromParts(Part.fromText(
                            "You are a helpful assistant. " +
                                    "The user will send you text in Igbo language. " +
                                    "Translate it to English and identify the intention as a full descriptive sentence. " +
                                    "IMPORTANT: You must ALWAYS respond with ONLY raw JSON, no markdown, no code blocks. " +
                                    "Use this exact format: " +
                                    "{\"igboText\": \"<original>\", \"translation\": \"<english translation>\", " +
                                    "\"intention\": \"<full sentence describing what the user wants>\", " +
                                    "\"result\": \"<if conversational, your response in Igbo. if weather, just the city name. if currency, just the currency pair e.g USD to NGN>\"}"
                    )))
                    .build();

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash", igboText, config);
            String json = response.text();
            json = json.replaceAll("(?s)```json\\s*", "").replaceAll("(?s)```\\s*", "").trim();

            ObjectMapper mapper = new ObjectMapper();
            ResponseDto dto = mapper.readValue(json, ResponseDto.class);
            String intention = dto.getIntention().toLowerCase();

            if (intention.contains("weather")){
                WeatherResponseDto weatherResponse = weatherService.getCurrentWeather(dto.getResult());
                String weatherResponseString = "Location: " + weatherResponse.getLocation().getName()
                        + " | Temperature: " + weatherResponse.getCurrent().getTempC() + "°C"
                        + " | Condition: " + weatherResponse.getCurrent().getCondition().getText();
                dto.setResult(weatherResponseString);
            }
            return dto;
        } catch (WeatherApiException e){
            throw e;
        } catch (Exception e){
        throw new GeminiApiException("Gemini API Call Failed: " + e.getMessage());
        }
    }
}
