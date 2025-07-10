package com.agir.maidai.controller;

import com.agir.maidai.entity.Company;
import com.agir.maidai.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompaniesControllerTest {

    @Mock
    private CompanyService companyService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CompaniesController companiesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {

        String viewName = companiesController.create(model, redirectAttributes);

        assertEquals("companies/form", viewName);

    }

    @Test
    void testStoreSuccess() {

        Company company = new Company(1, "Test Company");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = companiesController.store(company, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/companies", viewName);
    }
}
