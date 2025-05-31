package com.agir.maidai.controller;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.PersonType;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;

@Controller
@RequestMapping("/people")
public class PeopleController extends AbstractCrudController<Person, Integer>  implements CrudController<Person, Integer>{

    private PersonServiceImpl personService;
    private PersonTypeService personTypeService;
    private PPGService ppgService;

    @Autowired
    public PeopleController(PersonServiceImpl personService, PersonTypeService personTypeService, PPGService ppgService) {
        super(personService, "person", "people");
        this.personService = personService;
        this.personTypeService = personTypeService;
        this.ppgService = ppgService;
    }

    @GetMapping
    @Override
    public String index(Model model,
                        HttpServletRequest request) {

        int page = getIntParameter(request, "page", 0);
        int size = getIntParameter(request, "size", 10);
        String sort = request.getParameter("sort");
        if (sort == null) sort = "name,asc"; // Default sort
        Integer typeId = null;
        if(request.getParameter("typeId") != null &&
                !request.getParameter("typeId").isEmpty()) {
            typeId = Integer.valueOf(request.getParameter("typeId"));
        }

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, direction, sortField);

        Page<Person> personPage = typeId != null
                ? personService.findByPersonType(typeId, pageable)
                : personService.findAll(pageable);

        List<PersonType> personTypeList = personTypeService.findAll();

        new ModelAttributes(model)
                .add("personTypeList", personTypeList)
                .add("typeId", typeId)
                .add("baseViewPath", baseViewPath)
                .add("entityList", personPage)
                .add("sort", sort)
                .apply();
        return baseViewPath + "/list";
    }

    @Override
    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {

        Person person = personService.find(id);

        if(person.getId() != null) {

            PersonType personType = person.getPersonType();

            if("orientador".equals(personType.getType())) {
                List<PPG> ppgList = ppgService.findAll();

                new ModelAttributes(model)
                    .add("ppgList", ppgList)
                    .apply();
            }
        }
        return super.show(id, model);
    }

    @Override
    @GetMapping("/create")
    public String create(Model model, RedirectAttributes redirectAttributes) {

        List<PersonType> personTypeList = personTypeService.findAll();
        new ModelAttributes(model)
                .add("personTypeList", personTypeList)
                .apply();

        return super.create(model, redirectAttributes);

    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        List<PersonType> personTypeList = personTypeService.findAll();
        new ModelAttributes(model)
                .add("personTypeList", personTypeList)
                .apply();
        return super.edit(id, model, redirectAttributes);
    }

    @Override
    protected Person createNewEntity() {
        return new Person();
    }

    @Override
    protected String getCreateSuccessMessage() {
        return "Os dados da pessoa foram cadastrados.";
    }

    @Override
    protected String getUpdateSuccessMessage() {
        return "Os dados da pessoa foram atualizados.";
    }

    @Override
    protected String getDeleteSuccessMessage() {
        return "Os dados da pessoa foram removidos.";
    }
}
