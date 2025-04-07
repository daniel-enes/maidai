package com.agir.maidai.controller;

import com.agir.maidai.entity.*;
import com.agir.maidai.service.*;
import com.agir.maidai.util.ModelAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectsController extends AbstractCrudController<Project, Integer> implements CrudController<Project, Integer> {

    private final ProjectServiceImpl projectServiceImpl;
    private final CompanyServiceImpl companyService;
    private final AdvisorService advisorService;
    private final AdvisorProjectServiceImpl advisorProjectService;

    @Autowired
    public ProjectsController(ProjectServiceImpl projectServiceImpl,
                              CompanyServiceImpl companyService,
                              AdvisorService advisorService, AdvisorProjectServiceImpl advisorProjectService) {
        super(projectServiceImpl, "project", "projects");
        this.projectServiceImpl = projectServiceImpl;
        this.companyService = companyService;
        this.advisorService = advisorService;

        this.advisorProjectService = advisorProjectService;
    }

    @Autowired
    private HttpServletRequest request;

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
        List<Advisor> advisorList = advisorService.findAll();

        new ModelAttributes(model)
                .add("companyList", companyList)
                .add("advisorList", advisorList)
                .apply();
        return super.create(model);
    }

    @Override
    @PostMapping
    public String store(Project entity,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {

        Integer advisorId = Integer.valueOf(request.getParameter("advisorId"));
        Integer coAdvisorId = request.getParameter("coAdvisorId") != null ? Integer.valueOf(request.getParameter("coAdvisorId"))
                : null;

        // Send the lists to the model case occurs a error
        List<Company> companyList = companyService.findAll();
        new ModelAttributes(model)
                .add("companyList", companyList)
                .apply();

        if(bindingResult.hasErrors()) {
            new ModelAttributes(model)
                    .add("errors", bindingResult.getAllErrors())
                    .add(entityName, entity)
                    .apply();
            return baseViewPath + "/form";
        }

        try {
            projectServiceImpl.create(entity);

            AdvisorProjectId id = new AdvisorProjectId();
            id.setProjectId(entity.getId());
            id.setAdvisorId(advisorId);

            Advisor advisor = advisorService.find(advisorId);
            Advisor coAdvisor = (coAdvisorId != null) ? advisorService.find(coAdvisorId) : null;

            AdvisorProject relationship = new AdvisorProject();
            relationship.setId(id);
            relationship.setProject(entity);
            relationship.setAdvisor(advisor);
            relationship.setCoAdvisor(coAdvisor);

            advisorProjectService.create(relationship);

            redirectAttributes.addFlashAttribute("success", "Projeto cadastado com sucesso.");
            return "redirect:/" + baseViewPath;
        } catch (Exception e) {
            List<ObjectError> errors = new ArrayList<>();
            errors.add(new ObjectError("globalError", e.getMessage()));

            new ModelAttributes(model)
                    .add("errors", errors)
                    .add(entityName, entity)
                    .apply();

            return baseViewPath + "/form";
        }
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
