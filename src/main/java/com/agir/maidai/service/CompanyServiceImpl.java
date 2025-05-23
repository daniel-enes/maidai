package com.agir.maidai.service;

import com.agir.maidai.entity.Company;
import com.agir.maidai.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class CompanyServiceImpl extends AbstractCrudService<Company, Integer> implements CompanyService{

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }
//
//    public void create(Company company) {
//        //validateSave(company);
//        super.create(company);
//    }
//
//    public void update(Company company) {
//        //validateSave(company);
//        super.update(company);
//    }

    @Override
    public Errors validateSave(Company company, Errors errors) {

//        String trimmedName = company.getName().trim();
//        company.setName(trimmedName);
//
//        Optional<Company> companyWithSameName = companyRepository.findByName(trimmedName);
//
//        String sameNameError = "Esse nome já está sendo usado por outra empresa.";
//
//        if(companyWithSameName.isPresent()) {
//            if(company.getId() == null || !companyWithSameName.get().getId().equals(company.getId())) {
//                throw new IllegalArgumentException(sameNameError);
//            }
//        }
//    }
        // Trim name and validate
        String trimmedName = company.getName().trim();
        company.setName(trimmedName);

        // Check for duplicate name
        Optional<Company> companyWithSameName = companyRepository.findByName(company.getName());

        if(companyWithSameName.isPresent() &&
                (company.getId() == null || !companyWithSameName.get().getId().equals(company.getId()))) {
            errors.rejectValue("name", "name.duplicate", "Esse nome já está sendo usado por outra empresa.");
        }

        return errors;
    }

}
