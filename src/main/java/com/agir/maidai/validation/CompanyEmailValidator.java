package com.agir.maidai.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CompanyEmailValidator implements ConstraintValidator<CompanyEmail, String> {

    private static final String COMPANY_DOMAIN = "id.uff.br";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email == null) {
            return false;
        }
        return email.endsWith("@" + COMPANY_DOMAIN);
    }
}
