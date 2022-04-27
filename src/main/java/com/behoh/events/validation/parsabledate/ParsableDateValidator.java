package com.behoh.events.validation.parsabledate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Verifica se a String pode ser convertida em uma data válida.
 */
public class ParsableDateValidator
        implements ConstraintValidator<ParsableDate, String> {

    @Override
    public boolean isValid(
            String string,
            ConstraintValidatorContext constraintValidatorContext) {

        try {
            LocalDateTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
}
