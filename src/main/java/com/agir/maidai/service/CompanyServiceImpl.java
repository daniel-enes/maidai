package com.agir.maidai.service;

import com.agir.maidai.entity.Company;
import com.agir.maidai.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl extends AbstractCrudService<Company, Integer> implements CompanyService{

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }

    public void create(Company company) {
        validateSave(company);
        super.create(company);
    }

    public void update(Company company) {
        validateSave(company);
        super.update(company);
    }

    public void validateSave(Company company) {

        String trimmedName = company.getName().trim();
        company.setName(trimmedName);

        Optional<Company> companyWithSameName = companyRepository.findByName(trimmedName);

        String sameNameError = "Esse nome já está sendo usado por outra empresa.";

        if(companyWithSameName.isPresent()) {
            if(company.getId() == null || !companyWithSameName.get().getId().equals(company.getId())) {
                throw new IllegalArgumentException(sameNameError);
            }
        }
    }
}
