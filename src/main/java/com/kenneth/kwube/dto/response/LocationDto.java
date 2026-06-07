package com.kenneth.kwube.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private String name;
    private String region;
    private String country;
}