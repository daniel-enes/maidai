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
    public Page<Scholarship> findAll(Pageable pageable, Map<String, String> parameters) {

        // Verify if it's filtered by status
        if(parameters.containsKey("status")) {
            String status = parameters.get("status");
            return this.findByStatus(pageable, status);

        } else if (parameters.containsKey("query") && parameters.containsKey("search")) {

            String query = parameters.get("query");
            String search = parameters.get("search");

            if(search.equals("scholarshipHolder")) {
                return this.findByScholarshipHolder(pageable, query);
            } else if(search.equals("advisor")) {
                return this.findByAdvisor(pageable, query);
            }
            else if(search.equals("project")) {
                return this.findByProject(pageable, query);
            }
            else if(search.equals("company")) {
                return this.findByCompany(pageable, query);
            }
            else {
                return null;
            }
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

    @Override
    public Page<Scholarship> findByAdvisor(Pageable pageable, String name) {
        return scholarshipRepository.findByAdvisor(pageable, name);
    }

    @Override
    public Page<Scholarship> findByProject(Pageable pageable, String name) {
        return scholarshipRepository.findByProject(pageable, name);
    }

    @Override
    public Page<Scholarship> findByCompany(Pageable pageable, String name) {
        return scholarshipRepository.findByCompany(pageable, name);
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
