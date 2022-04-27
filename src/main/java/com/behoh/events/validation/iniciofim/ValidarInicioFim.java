package com.behoh.events.validation.iniciofim;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Valida se campos de data estão corretos na linha do tempo.
 * Verificando se a data 'inicio' antecede a data 'fim'.
 * Não é responsável por garantir que valores não sejam nulos,
 * caso algum dos valores seja NULL, a comparacao é ignorada,
 * e é considerado válido.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InicioFimValidator.class)
public @interface ValidarInicioFim {

    String message() default "Data de inicio deve anteceder data de fim.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
