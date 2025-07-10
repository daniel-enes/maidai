package com.agir.maidai.service;

import com.agir.maidai.entity.Company;
import com.agir.maidai.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        company = new Company(1, "Test Company");
    }

    @Test
    void testValidateSaveWithValidCompany() {

        Errors errors = new BeanPropertyBindingResult(company, "company");

        when(companyRepository.findByName(anyString())).thenReturn(Optional.empty());

        companyService.validateSave(company, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void testValidateSaveWithDuplicateName() {
        Company existingCompany = new Company(2, "Test Company");

        Errors errors = new BeanPropertyBindingResult(company, "company");

        when(companyRepository.findByName(anyString())).thenReturn(Optional.of(existingCompany));

        companyService.validateSave(company, errors);

        assertTrue(errors.hasErrors());

        assertEquals("name.duplicate", errors.getFieldError("name").getCode());
    }

}
