package com.agir.maidai.controller;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.PPG;
import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.PersonType;
import com.agir.maidai.service.AdvisorService;
import com.agir.maidai.service.PPGService;
import com.agir.maidai.service.PersonService;
import com.agir.maidai.service.PersonTypeService;
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
    private AdvisorService advisorService;

    @Autowired
    public PeopleController(PersonService personService, PersonTypeService personTypeService, PPGService ppgService, AdvisorService advisorService) {
        super(personService, "person", "people");
        this.personService = personService;
        this.personTypeService = personTypeService;
        this.ppgService = ppgService;
        this.advisorService = advisorService;
    }


    @Override
    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {

        Person person = personService.find(id);

        if(person.getId() != null) {

            PersonType personType = person.getPersonType();

            if("orientador".equals(personType.getType())) {
                Advisor advisor = advisorService.find(id);
                List<PPG> ppgList = ppgService.findAll();

                new ModelAttributes(model)
                    .add("ppgList", ppgList)
                    .add("advisor", advisor)
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
