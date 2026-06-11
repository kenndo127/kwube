package com.kenneth.kwube.controller;

import com.kenneth.kwube.dto.request.RequestDto;
import com.kenneth.kwube.dto.response.ResponseDto;
import com.kenneth.kwube.services.AIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class kwubeController {

    private final AIService aiService;

    @Operation(summary = "Send Igbo text to be translated and processed")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "502", description = "External API failure")
    @ApiResponse(responseCode = "500", description = "Internal Server failure")
    @PostMapping("/kwube")
    public ResponseEntity<ResponseDto> parseInput(
            @Valid @RequestBody RequestDto payload
    ){
        ResponseDto responseDto = aiService.chat(payload.getIgboText());
        return ResponseEntity.ok(responseDto);
    }
}
