package com.agir.maidai.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PPGTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testValidPPG() {
        PPG ppg = new PPG(1, "Teste PPG");
        var violations = validator.validate(ppg);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidBlankName() {
        PPG ppg = new PPG(1, "");
        var violations = validator.validate(ppg);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAddPerson() {
        PPG ppg = new PPG(1, "Teste PPG");
        Person person = new Person();
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        ppg.setPersonList(personList);

        assertEquals(1, ppg.getPersonList().size());
        assertEquals(person, ppg.getPersonList().get(0));
    }
}
