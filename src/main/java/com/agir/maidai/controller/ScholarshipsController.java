package com.agir.maidai.controller;

import com.agir.maidai.entity.*;
import com.agir.maidai.repository.ScholarshipRepository;
import com.agir.maidai.service.PersonService;
import com.agir.maidai.service.ProjectService;
import com.agir.maidai.service.ScholarshipService;
import com.agir.maidai.service.ScholarshipTypeService;
import com.agir.maidai.util.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    @GetMapping("/vigencia")
    public String vigencia() {

        //System.out.println("CHegou aqui INICIO");
        List<Scholarship> scholarships = scholarshipService.findAll();

        LocalDate currentDate = LocalDate.now();

        scholarships.stream().filter(scholarship -> scholarship.getStatus() != null)
                .forEach(scholarship -> {
                    //System.out.println("CHegou aqui");
                    String newStatus = scholarship.getEnd().isBefore(currentDate)
                            ? "vigência expirada"
                            : "vigente";
//                    scholarship.setStatus(newStatus);
//                    scholarshipService.update(scholarship);
                    // Only update if status has changed
                    if (!newStatus.equals(scholarship.getStatus())) {
                        scholarship.setStatus(newStatus);
                        scholarshipService.update(scholarship); // Use service instead of repository directly
                    }
                });
        return "redirect:/scholarships";
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
