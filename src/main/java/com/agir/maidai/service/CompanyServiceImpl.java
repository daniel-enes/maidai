package com.agir.maidai.service;

import com.agir.maidai.entity.Company;
import com.agir.maidai.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends AbstractCrudService<Company, Integer> implements CompanyService{

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }

    public void create(Company company) {
        if(companyRepository.existsByName(company.getName())) {
            throw new IllegalArgumentException("Esse nome já existe. Tente usar outro.");
        }
        super.create(company);
    }

    public void update(Company company) {
        if(companyRepository.existsByName(company.getName())) {
            throw new IllegalArgumentException("Esse nome já existe. Tente usar outro.");
        }
        super.update(company);
    }

}
