package com.agir.maidai.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompanyEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyEmail {
    String message() default "E-mail inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
