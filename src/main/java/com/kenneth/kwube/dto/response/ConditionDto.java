package com.kenneth.kwube.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ConditionDto {
    private String text;
    private String icon;
}