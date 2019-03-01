package com.example.filedemo.exception;

public class InvalidPasteException extends RuntimeException{

    public InvalidPasteException(String message) {
        super(message);
    }

    public InvalidPasteException(String message, Throwable cause) {
        super(message, cause);
    }
}




