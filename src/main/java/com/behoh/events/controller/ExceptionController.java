package com.behoh.events.controller;

import com.behoh.events.exception.EntidadeNaoEncontradaException;
import com.behoh.events.exception.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Lida com exeções lançadas durante requisições.
 * Retornando um Response com status e mensagem adequada.
 */
@ControllerAdvice
public class ExceptionController {

    // Erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleBindException (
            MethodArgumentNotValidException ex){

        // mapeando erros para String
        String[] erros = ex.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .toArray(String[]::new);

        return buildResponse(
                HttpStatus.BAD_REQUEST, erros );
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    protected ResponseEntity handleRegraDeNegocio (
            RegraDeNegocioException ex) {

        return buildResponse(
                HttpStatus.CONFLICT, ex.getMessage() );

    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    protected ResponseEntity handleEntityNotFound (
            EntidadeNaoEncontradaException ex){

        return buildResponse(
                HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity buildResponse(
            HttpStatus status,
            String... erros){

        Map<String, String[]> map = new HashMap<>();
        map.put("erros", erros);

        return new ResponseEntity<>( map, status );
    }

}
