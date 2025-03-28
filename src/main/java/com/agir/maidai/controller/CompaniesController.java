package com.agir.maidai.controller;

import com.agir.maidai.entity.Company;

import com.agir.maidai.service.CompanyServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompaniesController extends AbstractCrudController<Company, Integer> implements CrudController<Company, Integer>{

    public CompaniesController(CompanyServiceImpl companyService) {
        super(companyService, "company", "companies");
    }

    @Override
    protected Company createNewEntity() {
        return new Company();
    }

    @Override
    protected String getCreateSuccessMessage() {
        return "Empresa criada com sucesso";
    }

    @Override
    protected String getUpdateSuccessMessage() {
        return "Os dados da empresa foram alterados";
    }

    @Override
    protected String getDeleteSuccessMessage() {
        return "A empresa foi removida";
    }
}
