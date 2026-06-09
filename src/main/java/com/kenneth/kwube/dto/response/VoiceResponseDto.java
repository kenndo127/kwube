package com.kenneth.kwube.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VoiceResponseDto {
    @JsonProperty("text")
    private String text;

    @JsonProperty("model_id")
    private String modelID;
}
