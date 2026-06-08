package com.kenneth.kwube.client;

import com.kenneth.kwube.dto.response.ExchangeRateResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-rate-api", url = "${kwube.exchangerateapi.baseurl}")
public interface ExchangeRateApiClient {

    @GetMapping("/{apiKey}/latest/{baseCurrency}")
    ExchangeRateResponseDto fetchExchangeRate(
            @PathVariable("apiKey") String apiKey,
            @PathVariable("baseCurrency") String baseCurrency
    );
}
