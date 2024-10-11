package br.pucminas.moeda.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidadorCPF.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPF {

    String message() default "CPF inválido!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
