package com.agir.maidai.service;

import com.agir.maidai.entity.Company;
import com.agir.maidai.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

@Service
public class CompanyServiceImpl extends AbstractCrudService<Company, Integer> implements CompanyService{

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }

    @Override
    public Page<Company> findAll(Pageable pageable, Map<String, String> parameters) {
        // Verify if it's filtered by status
        if (parameters.containsKey("name")) {
            String name = parameters.get("name");
            return this.findByNameContainingIgnoreCase(name, pageable);
        }
        return null;
    }

    @Override
    public Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return companyRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public void validateSave(Company company, Errors errors) {

        // Trim name and validate
        String trimmedName = company.getName().trim();
        company.setName(trimmedName);

        // Check for duplicate name
        Optional<Company> companyWithSameName = companyRepository.findByName(company.getName());

        if(companyWithSameName.isPresent() &&
                (company.getId() == null || !companyWithSameName.get().getId().equals(company.getId()))) {
            errors.rejectValue("name", "name.duplicate", "Esse nome já está sendo usado por outra empresa.");
        }
    }

}
