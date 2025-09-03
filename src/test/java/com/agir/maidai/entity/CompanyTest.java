package com.agir.maidai.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCompany() {
        Company company = new Company(1, "Valid Company");
        var violations = validator.validate(company);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidBlankName() {
        Company company = new Company (1, "");
        var violations = validator.validate(company);
        assertFalse(violations.isEmpty());
        //assertEquals("Nome não pode ficar em branco", violations.iterator().next().getMessage());
    }

    @Test
    void testAddProject() {
        Company company = new Company(1, "Test Company");
        Project project = new Project();
        company.getProjectList().add(project);

        assertEquals(1, company.getProjectList().size());
        assertEquals(project, company.getProjectList().get(0));
    }
}
