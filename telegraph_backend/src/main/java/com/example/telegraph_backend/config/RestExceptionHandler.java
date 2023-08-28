package com.example.telegraph_backend.config;

import com.example.telegraph_backend.exception.AuthenticationFailedException;
import com.example.telegraph_backend.exception.RequestValidationException;
import com.example.telegraph_backend.exception.UniqueObjectException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {RequestValidationException.class})
    public ResponseEntity<String> requestValidationExceptionHandler(
        RequestValidationException e
    ){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(value = {AuthenticationFailedException.class})
    public ResponseEntity<String> authenticationFailedExceptionHandler(
            AuthenticationFailedException e
    ){
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(value = {UniqueObjectException.class})
    public ResponseEntity<String> uniqueObjectException(
            UniqueObjectException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<String> objectNotFoundExceptionHandler(
            ObjectNotFoundException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }




}
