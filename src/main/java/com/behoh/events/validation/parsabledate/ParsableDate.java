package com.behoh.events.validation.parsabledate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Valida se é possível converter a String em uma data (LocalDateTime).
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ParsableDateValidator.class)
public @interface ParsableDate {

    String message() default "Data inválida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
