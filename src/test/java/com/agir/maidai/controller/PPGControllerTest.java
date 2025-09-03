package com.agir.maidai.controller;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.service.PPGService;
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

public class PPGControllerTest {

    @Mock
    private PPGService ppgService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PPGController ppgController;

    private PPG testPpg;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testPpg = new PPG(1, "Test PPG");
    }

    @Test
    void index_shouldReturnPpgListView() {

        Page<PPG> page = new PageImpl<>(Collections.singletonList(testPpg));
        when(ppgService.findAll(any(Pageable.class))).thenReturn(page);
        when(request.getParameterMap()).thenReturn(new HashMap<>());

        String viewName = ppgController.index(model, request);

        assertEquals("ppgs/list", viewName);
        verify(model).addAttribute("entityList", page);
        verify(model).addAttribute("baseViewPath", "ppgs");
    }

    @Test
    void show_shouldReturnViewWithPpg() {
        when(ppgService.find(1)).thenReturn(testPpg);

        String viewName = ppgController.show(1, model);

        assertEquals("ppgs/view", viewName);
        verify(model).addAttribute("ppg", testPpg);
    }

    @Test
    void create_shouldReturnFormViewWithNewPpg() {
        String viewName = ppgController.create(model, redirectAttributes);

        assertEquals("ppgs/form", viewName);
        verify(model).addAttribute(eq("ppg"), any(PPG.class));
    }

    @Test
    void store_shouldRedirectToPpgListWhenSuccess() {

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ppgController.store(testPpg, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/ppgs", viewName);
        verify(ppgService).create(testPpg);
        verify(redirectAttributes).addFlashAttribute("success", "Programa de Pós-graduação criado com sucesso.");
    }

    @Test
    void store_shouldRedirectBackWhenPpgValidationsFails() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ppgController.store(testPpg, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/ppgs/create", viewName);
        verify(redirectAttributes).addFlashAttribute("errors", bindingResult.getAllErrors());
        verify(redirectAttributes).addFlashAttribute("ppg", testPpg);
    }

    @Test
    void edit_shouldReturnFormViewWithExistingPpg() {
        when(ppgService.find(1)).thenReturn(testPpg);

        String viewName = ppgController.edit(1, model, redirectAttributes);

        assertEquals("ppgs/form", viewName);
        verify(model).addAttribute("ppg", testPpg);
    }

    @Test
    void update_shouldReturnRedirectToPpgListWhenSuccess() {

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ppgController.update(1, testPpg, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/ppgs", viewName);
        verify(ppgService).update(testPpg);
        verify(redirectAttributes).addFlashAttribute("success", "Programa de Pós-graduação editado com sucesso.");
    }

    @Test
    void update_shouldRedirectBackWhenPpgValidationFails() {

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ppgController.update(1, testPpg, bindingResult, model, redirectAttributes);

        assertEquals("redirect:/ppgs/1/edit", viewName);
        verify(redirectAttributes).addFlashAttribute("errors", bindingResult.getAllErrors());
        verify(redirectAttributes).addFlashAttribute("ppg", testPpg);
    }

    @Test
    void delete_shouldRedirectToPpgListWIthSuccessMessage() {

        String viewName = ppgController.delete(1, redirectAttributes);

        assertEquals("redirect:/ppgs", viewName);
        verify(ppgService).delete(1);
        verify(redirectAttributes).addFlashAttribute("success", "Programa de Pós-graduação removido");
    }
}
