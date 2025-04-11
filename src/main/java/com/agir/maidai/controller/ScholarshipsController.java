package com.agir.maidai.controller;

import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.Project;
import com.agir.maidai.entity.Scholarship;
import com.agir.maidai.entity.ScholarshipType;
import com.agir.maidai.service.PersonService;
import com.agir.maidai.service.ProjectService;
import com.agir.maidai.service.ScholarshipService;
import com.agir.maidai.service.ScholarshipTypeService;
import com.agir.maidai.util.ModelAttributes;
import com.agir.maidai.util.RedirectAttributesWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/scholarships")
public class ScholarshipsController extends AbstractCrudController<Scholarship, Integer> implements CrudController<Scholarship, Integer>{

    private final ScholarshipService scholarshipService;
    private final ProjectService projectService;
    private final PersonService personService;
    private final ScholarshipTypeService scholarshipTypeService;

    @Autowired
    public ScholarshipsController(ScholarshipService scholarshipService, ProjectService projectService, PersonService personService, ScholarshipTypeService scholarshipTypeService) {
        super(scholarshipService, "scholarship", "scholarships");
        this.scholarshipService = scholarshipService;
        this.projectService = projectService;
        this.personService = personService;
        this.scholarshipTypeService = scholarshipTypeService;
    }

    @Override
    @GetMapping("/create")
    public String create(Model model, RedirectAttributes redirectAttributes) {

        List<Project> projectList = projectService.findAll();
        List<Person> personList = personService.findAll();
        List<ScholarshipType> scholarshipTypeList = scholarshipTypeService.findAll();

        new ModelAttributes(model)
                .add("projectList", projectList)
                .add("personList", personList)
                .add("scholarshipTypeList", scholarshipTypeList)
                .apply();


        return super.create(model, redirectAttributes);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {

        List<Project> projectList = projectService.findAll();
        List<Person> personList = personService.findAll();
        List<ScholarshipType> scholarshipTypeList = scholarshipTypeService.findAll();

        new ModelAttributes(model)
                .add("projectList", projectList)
                .add("personList", personList)
                .add("scholarshipTypeList", scholarshipTypeList)
                .apply();
        return super.edit(id, model, redirectAttributes);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    @Override
    protected Scholarship createNewEntity() {
        return new Scholarship();
    }

    @Override
    protected String getCreateSuccessMessage() {
        return "Bolsa criada com sucesso.";
    }

    @Override
    protected String getUpdateSuccessMessage() {
        return "Bolsa teve seus dados atualizados.";
    }

    @Override
    protected String getDeleteSuccessMessage() {
        return "Bolsa removida do banco de dados.";
    }
}
