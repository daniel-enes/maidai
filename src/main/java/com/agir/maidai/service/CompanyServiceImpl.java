package com.agir.maidai.service;

import com.agir.maidai.entity.Company;
import com.agir.maidai.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CompanyServiceImpl extends AbstractCrudService<Company, Integer> implements CompanyService{

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }


    public void create(Company company) {

        company.setCreatedAt(new Date(System.currentTimeMillis()));
        company.setUpdatedAt(new Date(System.currentTimeMillis()));

        super.create(company);
    }

}
