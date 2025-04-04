package com.agir.maidai.controller;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.Company;
import com.agir.maidai.entity.Project;
import com.agir.maidai.service.AdvisorService;
import com.agir.maidai.service.CompanyServiceImpl;
import com.agir.maidai.service.ProjectServiceImpl;
import com.agir.maidai.util.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectsController extends AbstractCrudController<Project, Integer> implements CrudController<Project, Integer> {

    private final ProjectServiceImpl projectServiceImpl;
    private final CompanyServiceImpl companyService;
    private final AdvisorService advisorService;

    @Autowired
    public ProjectsController(ProjectServiceImpl projectServiceImpl, CompanyServiceImpl companyService, AdvisorService advisorService) {
        super(projectServiceImpl, "project", "projects");
        this.projectServiceImpl = projectServiceImpl;
        this.companyService = companyService;
        this.advisorService = advisorService;
    }

    @Override
    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {

        List<Advisor> advisorList = advisorService.findAll();
        new ModelAttributes(model)
                .add("advisorList", advisorList)
                .apply();
        return super.show(id, model);
    }

    @Override
    @GetMapping("/create")
    public String create(Model model) {

        List<Company> companyList  = companyService.findAll();
        new ModelAttributes(model)
                .add("companyList", companyList)
                .apply();
        return super.create(model);
    }

    @Override
    @PostMapping
    public String store(Project entity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        List<Company> companyList = companyService.findAll();
        new ModelAttributes(model)
                .add("companyList", companyList)
                .apply();
        return super.store(entity, bindingResult, model, redirectAttributes);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        List<Company> companyList = companyService.findAll();
        new ModelAttributes(model)
                .add("companyList", companyList)
                .apply();
        return super.edit(id, model);
    }

    @Override
    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, Project entity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        List<Company> companyList = companyService.findAll();
        new ModelAttributes(model)
                .add("companyList", companyList)
                .apply();
        return super.update(id, entity, bindingResult, model, redirectAttributes);
    }

    @Override
    protected Project createNewEntity() {
        return new Project();
    }

    @Override
    protected String getCreateSuccessMessage() {
        return "Projeto criado com sucesso";
    }

    @Override
    protected String getUpdateSuccessMessage() {
        return "Os dados do Projeto foram atualizados";
    }

    @Override
    protected String getDeleteSuccessMessage() {
        return "O projeto foi apagado do banco de dados";
    }
}
