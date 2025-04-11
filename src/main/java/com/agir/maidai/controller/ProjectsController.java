package com.agir.maidai.controller;

import com.agir.maidai.entity.*;
import com.agir.maidai.service.*;
import com.agir.maidai.util.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
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
    public ProjectsController(ProjectServiceImpl projectServiceImpl,
                              CompanyServiceImpl companyService,
                              AdvisorService advisorService) {
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
    public String create(Model model, RedirectAttributes redirectAttributes) {

        List<Company> companyList  = companyService.findAll();
        List<Advisor> advisorList = advisorService.findAll();

        new ModelAttributes(model)
                .add("companyList", companyList)
                .add("advisorList", advisorList)
                .apply();
        return super.create(model, redirectAttributes);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        List<Company> companyList = companyService.findAll();
        List<Advisor> advisorList = advisorService.findAll();
        new ModelAttributes(model)
                .add("companyList", companyList)
                .add("advisorList", advisorList)
                .apply();
        return super.edit(id, model, redirectAttributes);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {}

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
