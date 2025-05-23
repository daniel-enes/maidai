package com.agir.maidai.validation;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private List<FieldError> errors = new ArrayList<>();
    private boolean isValid = true;

    @Autowired
    public void addError(String fieldName, String message) {
        this.errors.add(new FieldError(fieldName, message));
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<FieldError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

}
