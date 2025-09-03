package com.agir.maidai.service;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.repository.PPGRepository;
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

public class PPGServiceImplTest {

    @Mock
    private PPGRepository ppgRepository;

    @InjectMocks
    private PPGServiceImpl ppgService;

    private PPG ppg;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ppg = new PPG(1, "Teste PPG");
    }

    @Test
    void testValidateSaveWithValidPpg() {

        Errors errors = new BeanPropertyBindingResult(ppg, "ppg");

        when(ppgRepository.findByName(anyString())).thenReturn(Optional.empty());

        ppgService.validateSave(ppg, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void testValidateSaveWithPpgDuplicateName() {

        PPG existingPPG = new PPG(2, "Teste PPG");

        Errors errors = new BeanPropertyBindingResult(ppg, "ppg");

        when(ppgRepository.findByName(anyString())).thenReturn(Optional.of(existingPPG));

        ppgService.validateSave(ppg, errors);

        assertTrue(errors.hasErrors());
        assertEquals("name.duplicate", errors.getFieldError("name").getCode());
    }
}
