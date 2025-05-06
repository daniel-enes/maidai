package com.agir.maidai.exception;

public class FieldAwareError {

    private final String field;
    private final String defaultMessage;


    public FieldAwareError(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;
    }

    public String getField() {
        return field;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
