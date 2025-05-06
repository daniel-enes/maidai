package com.agir.maidai.service;

import com.agir.maidai.entity.Project;
import com.agir.maidai.entity.Scholarship;
import com.agir.maidai.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
        //validateSave(entity);
        super.create(entity);
    }

    @Override
    public void update(Scholarship entity) {

        super.update(entity);
    }

    public void validateSave(Scholarship scholarship, BindingResult bindingResult) {
        if(scholarship.getStart() == null) {
            bindingResult.rejectValue("start", "required", "Defina a data para quando a bolsa inicia.");
            //throw new IllegalArgumentException("Defina a data para quando a bolsa inicia.");
        }

        if(scholarship.getEnd() == null) {
            bindingResult.rejectValue("end", "required", "Defina a data para quando a bolsa termina.");
            //throw new IllegalArgumentException();
        }

        if(scholarship.getProject() == null) {
            bindingResult.rejectValue("project", "required", "Defina o projeto para o qual a bolsa pertence.");
            //throw new IllegalArgumentException("Defina o projeto para o qual a bolsa pertence.");
        }

        if(scholarship.getPerson() == null ||
        scholarship.getPerson().getPersonType() == null ||
        !"bolsista".equalsIgnoreCase(scholarship.getPerson().getPersonType().getType())) {
            bindingResult.rejectValue("person", "invalid", "Defina um bolsista válido.");
            //throw new IllegalArgumentException("Defina um bolsita.");
        }

        boolean existPerson = scholarshipRepository.existsByPerson(scholarship.getPerson());
        if(scholarship.getPerson() != null && existPerson) {
            bindingResult.rejectValue(
                    "person", "duplicate", "Este bolsista já possui uma bolsa ativa neste projeto."
            );
        }

        if(scholarship.getScholarshipType() == null) {
            bindingResult.rejectValue("scholarshipType", "required", "Determine o tipo de bolsa.");
            //throw new IllegalArgumentException("Determine o tipo de bolsa.");
        }
    }
}
