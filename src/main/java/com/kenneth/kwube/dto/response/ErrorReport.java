package com.kenneth.kwube.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorReport {
    private int statusCode;
    private String message;
    private LocalDateTime timeStamp;
}
