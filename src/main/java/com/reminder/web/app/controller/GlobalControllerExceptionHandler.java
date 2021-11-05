package com.reminder.web.app.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(value = HttpClientErrorException.class)
    public String responseNotFoundException(HttpClientErrorException httpClientErrorException) {
        switch (httpClientErrorException.getRawStatusCode()) {
            case (400):
                return "400";
            case (404):
                return "404";
            case (403):
                return "403";
            case (405):
                return "405";
            default:
                break;
        }
        return "error";
    }
}