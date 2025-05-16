package com.example.music_recommendation_api.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    private final String message;
    private final  LocalDateTime timestamp;
    private final String error;

    public ExceptionResponse (String message, String error){
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }
}
