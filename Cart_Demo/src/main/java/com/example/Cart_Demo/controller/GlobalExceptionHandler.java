package com.example.Cart_Demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //blanket exceptions
    @ExceptionHandler(Exception.class)
    public String handleException(){
        return "error";
    }
}
