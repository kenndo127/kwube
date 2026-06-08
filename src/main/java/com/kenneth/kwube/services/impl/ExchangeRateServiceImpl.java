package com.kenneth.kwube.services.impl;

import com.kenneth.kwube.client.ExchangeRateApiClient;
import com.kenneth.kwube.dto.response.ExchangeRateResponseDto;
import com.kenneth.kwube.services.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateApiClient exchangeRateApiClient;

    @Value("${kwube.exchangerateapi.key}")
    private String apiKey;

    @Override
    public ExchangeRateResponseDto fetchExchangeRate(String baseCurrency, String quoteCurrency) {
        ExchangeRateResponseDto response = exchangeRateApiClient.fetchExchangeRate(apiKey, baseCurrency.toUpperCase());
        Double rate = response.getConversionRates().get(quoteCurrency.toUpperCase());
        response.getConversionRates().clear();
        response.getConversionRates().put(quoteCurrency.toUpperCase(), rate);
        return response;
    }
}
