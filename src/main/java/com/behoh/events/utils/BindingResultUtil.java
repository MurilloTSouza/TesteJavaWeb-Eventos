package com.behoh.events.utils;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por fornecer processos comuns
 * utilizando validation.BindingResult
 */

public class BindingResultUtil {

    /**
     * Constroi response.BAD_REQUEST listando
     * errors de validação.
     * @param bindingResult Erros de validação.
     * @return lista de erros.
     */
    public static List<String> listErrors(
            BindingResult bindingResult){

        // listando erros
        List<String> errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        return errors;
    }
}
