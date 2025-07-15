package com.agir.maidai.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScholarshipTypeTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testValidScholarshipType() {

        ScholarshipType scholarshipType = new ScholarshipType(1, "MAI");
        var violations = validator.validate(scholarshipType);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidScholarshipTypeBlankType() {

        ScholarshipType scholarshipType = new ScholarshipType(1, "");
        var violations = validator.validate(scholarshipType);
        assertFalse(violations.isEmpty());
    }
}
