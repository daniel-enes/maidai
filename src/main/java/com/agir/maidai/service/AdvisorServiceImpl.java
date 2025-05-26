package com.agir.maidai.service;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.repository.AdvisorRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class AdvisorServiceImpl extends AbstractCrudService<Advisor, Integer> implements AdvisorService {

    private final AdvisorRepository advisorRepository;

    public AdvisorServiceImpl(AdvisorRepository advisorRepository) {
        super(advisorRepository);
        this.advisorRepository = advisorRepository;
    }

    @Override
    public Errors validateSave(Advisor advisor, Errors errors) {
        return null;
    }
}
