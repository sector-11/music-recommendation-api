package com.example.music_recommendation_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchElementException(NoSuchElementException e) {
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), e.getClass().getCanonicalName());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
