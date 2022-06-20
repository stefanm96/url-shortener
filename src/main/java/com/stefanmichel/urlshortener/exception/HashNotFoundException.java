package com.stefanmichel.urlshortener.exception;

public class HashNotFoundException extends RuntimeException {

    public HashNotFoundException() {
        super("Hash not found!");
    }
}
