package com.example.telegraph_backend.exception;

public class UniqueObjectException extends RuntimeException{
    public UniqueObjectException(String message) {
        super(message);
    }
}
