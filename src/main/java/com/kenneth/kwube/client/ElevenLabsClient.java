package com.kenneth.kwube.client;

import com.kenneth.kwube.dto.response.VoiceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "elevenlabs-client", url = "${kwube.elevenlabs.baseurl}")
public interface ElevenLabsClient {

    @PostMapping(value = "/v1/text-to-speech/{voiceId}", consumes = "application/json")
    byte[] textToSpeech(
            @PathVariable("voiceId") String voiceId,
            @RequestHeader("xi-api-key") String apiKey,
            @RequestBody VoiceResponseDto request
    );
}
