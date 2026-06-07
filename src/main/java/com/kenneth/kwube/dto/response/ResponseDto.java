package com.kenneth.kwube.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {
    private String igboText;
    private String translation;
    private String intention;
    private String result;
}
