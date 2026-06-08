package com.kenneth.kwube.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    @NotBlank(message = "Null and Empty fields are not allowed!")
    private String igboText;
}
