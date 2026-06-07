package com.kenneth.kwube.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherResponseDto {
    private LocationDto location;
    private CurrentDto current;
}