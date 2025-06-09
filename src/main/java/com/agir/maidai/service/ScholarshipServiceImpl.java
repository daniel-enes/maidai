package com.agir.maidai.service;

import com.agir.maidai.entity.Project;
import com.agir.maidai.entity.Scholarship;
import com.agir.maidai.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Map;

@Service
public class ScholarshipServiceImpl extends AbstractCrudService<Scholarship, Integer> implements ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    @Autowired
    protected ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository) {
        super(scholarshipRepository);
        this.scholarshipRepository = scholarshipRepository;
    }

    @Override
    public Page<Scholarship> findAllByOrderByPersonNameAsc(Pageable pageable) {
        return scholarshipRepository.findAllByOrderByPersonNameAsc(pageable);
    }

    @Override
    public Page<Scholarship> findAll(Pageable pageable, Map<String, String> filters) {
        if(filters.containsKey("status")) {
           String status = filters.get("status");
           return this.findByStatus(pageable, status);
        } else if (filters.containsKey("scholarshipHolder")) {
            String scholarshipHolder = filters.get("scholarshipHolder");
            return this.findByScholarshipHolder(pageable, scholarshipHolder);
        } else {
            return null;
        }
    }

    @Override
    public Page<Scholarship> findByStatus(Pageable pageable, String status) {
        return scholarshipRepository.findByStatus(pageable , status);
    }

    @Override
    public Page<Scholarship> findByScholarshipHolder(Pageable pageable, String name) {
        return scholarshipRepository.findByScholarshipHolder(pageable, name);
    }

    public void validateSave(Scholarship scholarship, Errors errors) {

        if(scholarship.getStart() != null && scholarship.getEnd() != null) {
            if (scholarship.getStart().isEqual((scholarship.getEnd()))) {
                errors.rejectValue("end", "dates.equal", "A data final não pode ser igual a data inicial.");
            } else if (scholarship.getEnd().isBefore(scholarship.getStart())) {
                errors.rejectValue("end", ".dates.invalid", "A data final não pode ser anterior à data inicial.");
            }
        }
    }


}
