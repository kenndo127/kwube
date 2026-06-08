package com.kenneth.kwube.exceptions;

import com.kenneth.kwube.dto.response.ErrorReport;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<ErrorReport> handleWeatherServiceException(WeatherApiException e) {
        return buildResponse(502, e.getMessage());
    }

    @ExceptionHandler(GeminiApiException.class)
    public ResponseEntity<ErrorReport> handleGeminiServiceException(GeminiApiException e) {
        return buildResponse(502, e.getMessage());
    }

    @ExceptionHandler(ExchangeRateApiException.class)
    public ResponseEntity<ErrorReport> handleExchangeRateApiException(ExchangeRateApiException e) {
        return buildResponse(502, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorReport> handleBadRequestException(BadRequestException e) {
        return buildResponse(400, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorReport> handleGenericException(Exception e) {
        return buildResponse(500, "An unexpected error occurred");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorReport> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Invalid request");
        return buildResponse(400, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorReport> handleUnreadableMessage(HttpMessageNotReadableException e) {
        return buildResponse(400, "Invalid request format");
    }

    private ResponseEntity<ErrorReport> buildResponse(int status, String message) {
        return ResponseEntity
                .status(status)
                .body(ErrorReport.builder()
                        .statusCode(status)
                        .message(message)
                        .timeStamp(LocalDateTime.now())
                        .build());
    }
}
