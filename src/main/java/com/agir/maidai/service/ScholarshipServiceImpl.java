package com.agir.maidai.service;

import com.agir.maidai.entity.Project;
import com.agir.maidai.entity.Scholarship;
import com.agir.maidai.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScholarshipServiceImpl extends AbstractCrudService<Scholarship, Integer> implements ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    @Autowired
    protected ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository) {
        super(scholarshipRepository);
        this.scholarshipRepository = scholarshipRepository;
    }

    @Override
    public void create(Scholarship entity) {
        validateSave(entity);
        super.create(entity);
    }

    @Override
    public void update(Scholarship entity) {
        super.update(entity);
    }

    public void validateSave(Scholarship scholarship) {
        if(scholarship.getStart() == null) {
            throw new IllegalArgumentException("Defina a data para quando a bolsa inicia.");
        }

        if(scholarship.getEnd() == null) {
            throw new IllegalArgumentException("Defina a data para quando a bolsa termina.");
        }

        if(scholarship.getProject() == null) {
            throw new IllegalArgumentException("Defina o projeto para o qual a bolsa pertence.");
        }

        if(scholarship.getPerson() == null ||
        scholarship.getPerson().getPersonType() == null ||
        !"bolsista".equalsIgnoreCase(scholarship.getPerson().getPersonType().getType())) {
            throw new IllegalArgumentException("Defina um bolsita.");
        }

        if(scholarship.getScholarshipType() == null) {
            throw new IllegalArgumentException("Determine o tipo de bolsa.");
        }
    }
}
