package com.behoh.events.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Lida com exeções lançadas durante requisições.
 * Retornando um Response.
 */
@ControllerAdvice
public class ExceptionController {

    // Erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleBindException (
            MethodArgumentNotValidException ex){

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    errors.add(error.getDefaultMessage());
                });

        return new ResponseEntity(
                errors, HttpStatus.BAD_REQUEST );
    }

}
