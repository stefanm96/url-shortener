package com.stefanmichel.urlshortener.exception;

public class CustomHashUnavailableException extends RuntimeException {

    public CustomHashUnavailableException() {
        super("Custom Hash is unavailable, please choose a new one or leave it empty.");
    }
}
