package com.agir.maidai.controller;

import com.agir.maidai.entity.*;
import com.agir.maidai.service.PersonService;
import com.agir.maidai.service.ProjectService;
import com.agir.maidai.service.ScholarshipService;
import com.agir.maidai.service.ScholarshipTypeService;
import com.agir.maidai.util.ModelAttributes;
import com.agir.maidai.util.RedirectAttributesWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;

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

//    @GetMapping
//    @Override
//    public String index(Model model,
//                        HttpServletRequest request) {
//
//        int page = getIntParameter(request, "page", 0);
//        int size = getIntParameter(request, "size", 10);
//        String sort = request.getParameter("sort");
//        if (sort == null) sort = ""; // Default sort
//
//        Pageable pageable;
//
//        if(sort.isEmpty()) {
//            pageable = PageRequest.of(page, size);
//        }
//        else {
//            String[] sortParams = sort.split(",");
//            String sortField = sortParams[0];
//            Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
//                    ? Sort.Direction.DESC
//                    : Sort.Direction.ASC;
//            pageable = PageRequest.of(page, size, direction, sortField);
//        }
//
//        Page<Scholarship> scholarships;
//        scholarships = scholarshipService.findAll(pageable);
////        if(sort.equals("person,asc") || sort.equals("person,desc")) {
////            scholarships = scholarshipService.findAllByOrderByPersonNameAsc(pageable);
////        } else {
////            scholarships = scholarshipService.findAll(pageable);
////        }
//
//        new ModelAttributes(model)
//                .add("baseViewPath", baseViewPath)
//                .add("entityList", scholarships)
//                .add("sort", sort)
//                .apply();
//        return baseViewPath + "/list";
//    }

    @Override
    @GetMapping("/create")
    public String create(Model model, RedirectAttributes redirectAttributes) {
        
        addEntitiesToModel(model);
        return super.create(model, redirectAttributes);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {

        addEntitiesToModel(model);
        return super.edit(id, model, redirectAttributes);
    }

    private void addEntitiesToModel(Model model) {
        List<Project> projectList = projectService.findAll();
        List<Person> scholarshipHolders = personService.findAllScholarshipHolders();
        List<ScholarshipType> scholarshipTypeList = scholarshipTypeService.findAll();

        new ModelAttributes(model)
                .add("projectList", projectList)
                .add("personList", scholarshipHolders)
                .add("scholarshipTypeList", scholarshipTypeList)
                .apply();
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
