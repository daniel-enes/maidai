package com.agir.maidai.controller;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.PersonType;
import com.agir.maidai.service.*;
import com.agir.maidai.util.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController extends AbstractCrudController<Person, Integer>  implements CrudController<Person, Integer>{

    private PersonService personService;
    private PersonTypeService personTypeService;
    private PPGService ppgService;
    private String baseViewPath = "people";
    private String entityName = "person";

    @Autowired
    public PeopleController(PersonService personService, PersonTypeService personTypeService, PPGService ppgService) {
        super(personService, "person", "people");
        this.personService = personService;
        this.personTypeService = personTypeService;
        this.ppgService = ppgService;
    }

    @Override
    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {

        Person person = personService.find(id);

        if(person.getId() != null) {

            PersonType personType = person.getPersonType();

            if("orientador".equals(personType.getType())) {
                List<PPG> ppgList = ppgService.findAll();
                this.entityName = "advisor";
                new ModelAttributes(model)
                    .add("ppgList", ppgList)
                    .apply();
            } else {
                this.entityName = "scholarshiper";
            }
        }

        new ModelAttributes(model)
                .add(entityName, personService.find(id))
                .apply();

        if("scholarshiper".equals(entityName)) {
            return this.baseViewPath + "/scholarshiperView";
        }
        return this.baseViewPath + "/advisorView";
//        return super.show(id, model);
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
