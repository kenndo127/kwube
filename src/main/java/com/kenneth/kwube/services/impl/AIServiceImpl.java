package com.kenneth.kwube.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.*;
import com.kenneth.kwube.dto.response.ExchangeRateResponseDto;
import com.kenneth.kwube.dto.response.ResponseDto;
import com.kenneth.kwube.dto.response.WeatherResponseDto;
import com.kenneth.kwube.exceptions.*;
import com.kenneth.kwube.services.AIService;
import com.kenneth.kwube.services.ElevenLabsService;
import com.kenneth.kwube.services.ExchangeRateService;
import com.kenneth.kwube.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;


@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final WeatherService weatherService;
    private final ExchangeRateService exchangeRateService;
    private final ElevenLabsService elevenLabsService;

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
                                    "\"result\": \"<if anything other than weather or currency, your response strictly must be in Igbo. if weather, just the city name." +
                                    "if currency or exchange rate, just the currency pair in this format: BASE to QUOTE e.g USD to NGN>\"}"                    )))
                    .build();

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash-lite", igboText, config);
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
            } else if (intention.contains("exchange rate") || intention.contains("currency")) {
                String[] currencies = dto.getResult().split(" to ");
                String base = currencies[0].trim();
                String quote = currencies[1].trim();
                ExchangeRateResponseDto rateResponse = exchangeRateService.fetchExchangeRate(base, quote);
                Double rate = rateResponse.getConversionRates().get(quote.toUpperCase());
                dto.setResult("1 " + base.toUpperCase() + " = " + rate + " " + quote.toUpperCase());
            }

            GenerateContentResponse igboResponse = client.models.generateContent(
                    "gemini-2.5-flash-lite",
                    "Convert this to a natural spoken Igbo sentence, return only the Igbo text, nothing else: " + dto.getResult(),
                    null);
            String igboSpeechText = igboResponse.text();
            dto.setResult(igboSpeechText); // returns igbo text to the result instead of igbo


            byte[] audioBytes = elevenLabsService.textToSpeech(igboSpeechText);
            String base64Audio = Base64.getEncoder().encodeToString(audioBytes);
            dto.setAudio(base64Audio);

            return dto;
        } catch (WeatherApiException | ExchangeRateApiException | ElevenLabsApiException e){
            throw e;
        } catch (Exception e){
            throw new GeminiApiException("Gemini API Call Failed: " + e.getMessage());
        }
    }
}
