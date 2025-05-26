package com.agir.maidai.service;

import com.agir.maidai.entity.Project;
import com.agir.maidai.entity.Scholarship;
import com.agir.maidai.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class ScholarshipServiceImpl extends AbstractCrudService<Scholarship, Integer> implements ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    @Autowired
    protected ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository) {
        super(scholarshipRepository);
        this.scholarshipRepository = scholarshipRepository;
    }

    public Errors validateSave(Scholarship scholarship, Errors errors) {

        if(scholarship.getStart() != null && scholarship.getEnd() != null) {
            if(scholarship.getStart().isEqual((scholarship.getEnd()))) {
                errors.rejectValue("end", "dates.equal", "A data final não pode ser igual a data inicial.");
            } else if(scholarship.getEnd().isBefore(scholarship.getStart())) {
                errors.rejectValue("end", ".dates.invalid", "A data final não pode ser anterior à data inicial.");
            }
        }
        return errors;
    }
}
