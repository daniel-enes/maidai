package com.agir.maidai.controller;

import com.agir.maidai.entity.*;
import com.agir.maidai.service.*;
import com.agir.maidai.util.ModelAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;

@Controller
@RequestMapping("/projects")
public class ProjectsController extends AbstractCrudController<Project, Integer> implements CrudController<Project, Integer> {

    private final ProjectService projectService;
    private final CompanyServiceImpl companyService;
    private final PersonService personService;

    @Autowired
    public ProjectsController(ProjectService projectService,
                              CompanyServiceImpl companyService,
                              PersonService personService) {
        super(projectService, "project", "projects");
        this.projectService = projectService;
        this.companyService = companyService;
        this.personService = personService;
    }

    @Override
    public String index(Model model, HttpServletRequest request) {
        int page = getIntParameter(request, "page", 0);
        int size = getIntParameter(request, "size", 10);
        String sort = request.getParameter("sort");
        if (sort == null) sort = ""; // Default sort

        String name = "";
        if(request.getParameter("name") != null) {
            name = request.getParameter("name").trim();
        }

        String yearNotice = null;
        if(request.getParameter("yearNotice") != null &&
                !request.getParameter("yearNotice").isEmpty()) {
            yearNotice = request.getParameter("yearNotice").trim();
        }

        Pageable pageable;

        if(sort.isEmpty()) {
            pageable = PageRequest.of(page, size);
        }
        else {
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, direction, sortField);
        }

        Page<Project> projectList;

        if(yearNotice != null) {
            if(yearNotice.equals("null")) {
                System.out.println("ANO DO EDITAL: " + yearNotice);
                projectList = projectService.findByYearNoticeIsNull(pageable);
            } else {
                projectList = projectService.findByYearNotice(yearNotice, pageable);
            }
        }
        else if(StringUtils.hasText(name)) {
            projectList = projectService.findByNameContainingIgnoreCase(name, pageable);
        } else {
            // No filters - get all
            projectList = projectService.findAll(pageable);
        }

        List<String> yearNoticeList =  projectService.findDistinctByYearNotice();

        new ModelAttributes(model)
                .add("searchName", name)
                .add("yearNotice", yearNotice)
                .add("yearNoticeList", yearNoticeList)
                .add("baseViewPath", baseViewPath)
                .add("entityList", projectList)
                .add("sort", sort)
                .apply();
        return baseViewPath + "/list";
    }

    @Override
    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {

        List<Person> advisorList = personService.findAllAdvisors();
        new ModelAttributes(model)
                .add("advisorList", advisorList)
                .apply();
        return super.show(id, model);
    }

    @Override
    @GetMapping("/create")
    public String create(Model model, RedirectAttributes redirectAttributes) {

        List<Company> companyList  = companyService.findAll();
        List<Person> advisorList = personService.findAllAdvisors();

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
        List<Person> advisorList = personService.findAllAdvisors();
        new ModelAttributes(model)
                .add("companyList", companyList)
                .add("advisorList", advisorList)
                .apply();
        return super.edit(id, model, redirectAttributes);
    }

    // Used to convert LocalDate to String and vice versa
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
