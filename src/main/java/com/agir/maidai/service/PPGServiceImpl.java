package com.agir.maidai.service;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.repository.PPGRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class PPGServiceImpl extends AbstractCrudService<PPG, Integer> implements PPGService{

    private final PPGRepository ppgRepository;

    public PPGServiceImpl(PPGRepository ppgRepository) {
        super(ppgRepository);
        this.ppgRepository = ppgRepository;
    }

    @Override
    public void validateSave(PPG ppg, Errors errors) {
        String trimmedName = ppg.getName().trim();
        ppg.setName(trimmedName);

        // Check for duplicate name
        Optional<PPG> ppgWithSameName = ppgRepository.findByName(ppg.getName());

        if(ppgWithSameName.isPresent() &&
                (ppg.getId() == null || !ppgWithSameName.get().getId().equals(ppg.getId()))) {
            errors.rejectValue("name", "name.duplicate", "Esse nome já está sendo usado por outra empresa.");
        }
    }
}
