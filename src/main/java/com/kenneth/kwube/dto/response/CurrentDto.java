package com.kenneth.kwube.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentDto {
    private ConditionDto condition;
    @JsonProperty("temp_c")
    private double tempC;
}