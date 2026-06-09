package com.kenneth.kwube.services.impl;

import com.kenneth.kwube.client.ElevenLabsClient;
import com.kenneth.kwube.dto.response.VoiceResponseDto;
import com.kenneth.kwube.services.ElevenLabsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElevenLabsServiceImpl implements ElevenLabsService {

    private final ElevenLabsClient elevenLabsClient;

    @Value("${kwube.elevenlabsapi.key}")
    private String apiKey;

    @Value("${kwube.elevenlabs.voiceid}")
    private String voiceId;

    @Override
    public byte[] textToSpeech(String text) {
        VoiceResponseDto request = VoiceResponseDto.builder()
                .text(text)
                .modelID("eleven_multilingual_v2")
                .build();
        return elevenLabsClient.textToSpeech(voiceId, apiKey, request);
    }
}
