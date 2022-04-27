package com.behoh.events.validation.iniciofim;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * Se ambos os valores 'inicio' e 'fim' forem fornecidos,
 * é validado se a data 'inicio' antecede a data 'fim',
 * caso alguma das datas não seja fornecida, a validação
 * é ignorada.
 */
public class InicioFimValidator
        implements ConstraintValidator<ValidarInicioFim, InicioFim> {

    @Override
    public boolean isValid(
            InicioFim inicioFim,
            ConstraintValidatorContext constraintValidatorContext) {

        LocalDateTime inicio = inicioFim.getParsedInicio();
        LocalDateTime fim = inicioFim.getParsedFim();

        // ignora caso alguma das datas seja null
        if(inicio == null || fim == null){
            return true;
        }

        // data 'inicio' vem antes da data 'fim'?
        return inicio.isBefore(fim);
    }
}
