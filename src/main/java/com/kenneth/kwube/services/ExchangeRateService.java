package com.kenneth.kwube.services;

import com.kenneth.kwube.dto.response.ExchangeRateResponseDto;

public interface ExchangeRateService {
    ExchangeRateResponseDto fetchExchangeRate(String baseCurrency, String quoteCurrency);
}
