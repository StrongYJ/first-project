package com.greenart.firstproject.error;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice("com.greenart.firstproject.api")
public class ErrorRestControllerAdvice {
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult notFoundExHandle(NoSuchElementException e) {
        return new ErrorResult("No Resource", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult notFoundExHandle(IllegalArgumentException e) {
        return new ErrorResult("Bad Request", e.getMessage());
    }


}
