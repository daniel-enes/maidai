package com.agir.maidai.controller;

import com.agir.maidai.entity.Company;
import com.agir.maidai.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CompaniesController companiesController;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCompany = new Company(1, "Test Company");
    }

    @Test
    void index_shouldReturnListView() {

        Page<Company> page = new PageImpl<>(Collections.singletonList(testCompany));
        when(companyService.findAll(any(Pageable.class))).thenReturn(page);
        when(request.getParameterMap()).thenReturn(new HashMap<>());

        String viewName = companiesController.index(model, request);

        assertEquals("companies/list", viewName);
        verify(model).addAttribute("entityList", page);
        verify(model).addAttribute("baseViewPath", "companies");
    }

    @Test
    void show_shouldReturnViewWithCompany() {

        when(companyService.find(1)).thenReturn(testCompany);

        String viewName = companiesController.show(1, model);

        assertEquals("companies/view", viewName);
        verify(model).addAttribute("company", testCompany);
    }

    @Test
    void create_shouldReturnFormViewWithNewCompany() {

        String viewName = companiesController.create(model, redirectAttributes);

        assertEquals("companies/form", viewName);
        verify(model).addAttribute(eq("company"), any(Company.class));
    }

    @Test
    void store_shouldRedirectToListWhenSuccess() {

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = companiesController.store(testCompany, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/companies", viewName);
        verify(companyService).create(testCompany);
        verify(redirectAttributes).addFlashAttribute("success", "Empresa criada com sucesso");
    }

    @Test
    void store_shouldRedirectBackWhenValidationsFails() {

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = companiesController.store(testCompany, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/companies/create", viewName);
        verify(redirectAttributes).addFlashAttribute("errors", bindingResult.getAllErrors());
        verify(redirectAttributes).addFlashAttribute("company", testCompany);
    }

    @Test
    void edit_shouldReturnFormViewWithExistingCompany() {

        when(companyService.find(1)).thenReturn(testCompany);

        String viewName = companiesController.edit(1, model, redirectAttributes);

        assertEquals("companies/form", viewName);
        verify(model).addAttribute("company", testCompany);
    }

    @Test
    void update_shouldReturnRedirectToListWhenSuccess() {

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = companiesController.update(1, testCompany, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/companies", viewName);
        verify(companyService).update(testCompany);
        verify(redirectAttributes).addFlashAttribute("success", "Os dados da empresa foram alterados");
    }

    @Test
    void update_shouldRedirectBackWhenValidationFails() {

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = companiesController.update(1, testCompany, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/companies/1/edit", viewName);
        verify(redirectAttributes).addFlashAttribute("errors", bindingResult.getAllErrors());
        verify(redirectAttributes).addFlashAttribute("company", testCompany);
    }

    @Test
    void delete_shouldRedirectToListWithSuccessMessage() {

        String viewName = companiesController.delete(1, redirectAttributes);

        assertEquals("redirect:/companies", viewName);
        verify(companyService).delete(1);
        verify(redirectAttributes).addFlashAttribute("success", "A empresa foi removida");
    }
}
