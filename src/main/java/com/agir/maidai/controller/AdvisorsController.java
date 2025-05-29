package com.agir.maidai.controller;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.service.AdvisorService;
import com.agir.maidai.util.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/advisors")
public class AdvisorsController extends AbstractCrudController<Advisor, Integer>  implements CrudController<Advisor, Integer>{

    private final AdvisorService advisorService;

    @Autowired
    public AdvisorsController(AdvisorService advisorService) {
        super(advisorService, "advisor", "advisors");
        this.advisorService = advisorService;
    }

    @Override
    @PutMapping("/{id}")
    public String update(@PathVariable Integer id,
                         Advisor entity,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            System.out.println("Chegou no if");
            new ModelAttributes(model)
                    .add("errors", bindingResult.getAllErrors())
                    .add(entityName, entity)
                    .apply();

            return baseViewPath + "/form";
        }
        try {
            service.update(entity);
            redirectAttributes.addFlashAttribute("success", getUpdateSuccessMessage());
            return "redirect:/people/"+id;
        } catch (Exception e) {
            System.out.println("Chegou no catch");
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
    protected Advisor createNewEntity() {
        return new Advisor();
    }

    @Override
    protected String getCreateSuccessMessage() {
        return null;
    }

    @Override
    protected String getUpdateSuccessMessage() {
        return "Orientador registrado para o PPG";
    }

    @Override
    protected String getDeleteSuccessMessage() {
        return null;
    }
}
