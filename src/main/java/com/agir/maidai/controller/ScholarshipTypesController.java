package com.agir.maidai.controller;

import com.agir.maidai.entity.ScholarshipType;
import com.agir.maidai.service.ScholarshipTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scholarshipTypes")
public class ScholarshipTypesController extends AbstractCrudController<ScholarshipType, Integer> implements CrudController<ScholarshipType, Integer> {

    private ScholarshipTypeService scholarshipTypeService;

    @Autowired
    public ScholarshipTypesController(ScholarshipTypeService scholarshipTypeService) {
        super(scholarshipTypeService, "scholarshipType", "scholarshipTypes");
        this.scholarshipTypeService = scholarshipTypeService;
    }

    @Override
    protected ScholarshipType createNewEntity() {
        return new ScholarshipType();
    }

    @Override
    protected String getCreateSuccessMessage() {
        return "Tipo de bolsa foi criado.";
    }

    @Override
    protected String getUpdateSuccessMessage() {
        return "O tipo de bolsa foi atualizado.";
    }

    @Override
    protected String getDeleteSuccessMessage() {
        return "O tipo de bolsa foi removido.";
    }
}
