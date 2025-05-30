package com.leetreader.leetReader.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;
}
