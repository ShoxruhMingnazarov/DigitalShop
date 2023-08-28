package com.example.telegraph_backend.exception;

public class ObjectsNotFoundException extends RuntimeException{

    public ObjectsNotFoundException(String message){
        super(message);
    }
}
