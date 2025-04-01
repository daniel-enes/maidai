package com.agir.maidai.controller;

import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.PersonType;
import com.agir.maidai.service.PersonService;
import com.agir.maidai.service.PersonTypeService;
import com.agir.maidai.util.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController extends AbstractCrudController<Person, Integer>  implements CrudController<Person, Integer>{

    private PersonService personService;
    private PersonTypeService personTypeService;

    @Autowired
    public PeopleController(PersonService personService, PersonTypeService personTypeService) {
        super(personService, "person", "people");
        this.personService = personService;
        this.personTypeService = personTypeService;
    }

    @Override
    @GetMapping("/create")
    public String create(Model model) {

        List<PersonType> personTypeList = personTypeService.findAll();
        new ModelAttributes(model)
                .add("personTypeList", personTypeList)
                .apply();
        return super.create(model);
    }

    @Override
    @PostMapping
    public String store(Person entity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        List<PersonType> personTypeList = personTypeService.findAll();
        new ModelAttributes(model)
                .add("personTypeList", personTypeList)
                .apply();
        return super.store(entity, bindingResult, model, redirectAttributes);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        List<PersonType> personTypeList = personTypeService.findAll();
        new ModelAttributes(model)
                .add("personTypeList", personTypeList)
                .apply();
        return super.edit(id, model);
    }

    @Override
    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, Person entity, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        List<PersonType> personTypeList = personTypeService.findAll();
        new ModelAttributes(model)
                .add("personTypeList", personTypeList)
                .apply();
        return super.update(id, entity, bindingResult, model, redirectAttributes);
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
