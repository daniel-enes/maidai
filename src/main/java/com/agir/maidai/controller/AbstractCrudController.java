package com.agir.maidai.controller;

import com.agir.maidai.service.CrudService;
import com.agir.maidai.util.ModelAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCrudController<T, ID> implements CrudController<T, ID>{

    protected final CrudService<T, ID> service;
    protected final String entityName;
    protected final String baseViewPath;

    public AbstractCrudController(CrudService<T, ID> service, String entityName, String baseViewPath) {
        this.service = service;
        this.entityName = entityName;
        this.baseViewPath = baseViewPath;
    }

    @Override
    @GetMapping
    public String index(Model model) {
        model.addAttribute(entityName + "List", service.findAll());
        return baseViewPath + "/list";
    }

    @Override
    @GetMapping("/{id}")
    public String show(ID id, Model model) {
        model.addAttribute(entityName, service.find(id));
        return baseViewPath + "/view";
    }

    @Override
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(entityName, createNewEntity());
        return baseViewPath + "/form";
    }

    @Override
    @PostMapping
    public String store(T entity,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            new ModelAttributes(model)
                    .add("errors", bindingResult.getAllErrors())
                    .add(entityName, entity)
                    .apply();
            return baseViewPath + "/form";
        }

        try {
            service.create(entity);
            redirectAttributes.addFlashAttribute("success", getCreateSuccessMessage());
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
    public String edit(ID id,
                       Model model) {
        model.addAttribute(entityName, service.find(id));
        return baseViewPath + "/form";
    }

    @PutMapping("/{id}")
    @Override
    public String update(ID id,
                         T entity,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            //addFormAttributes(model);
            return baseViewPath + "/form";
        }
        try {
            service.update(entity);
            redirectAttributes.addFlashAttribute("success", getUpdateSuccessMessage());
            return "redirect:/" + baseViewPath;
        } catch (Exception e) {
            //model.addAttribute("error", e.getMessage());
            //addFormAttributes(model);
            return baseViewPath + "/form";
        }
    }

    @Override
    public String delete(ID id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", getDeleteSuccessMessage());
        return "redirect:/" + baseViewPath;
    }
    /*
    @Override
    public String getBaseViewPath() {
        return CrudController.super.getBaseViewPath();
    }

    @Override
    public String getEntityName() {
        return CrudController.super.getEntityName();
    }
    */
    // Template methods to be implemented by concrete controllers
    protected abstract T createNewEntity();
    //protected abstract void addFormAttributes(List list);
    protected abstract String getCreateSuccessMessage();
    protected abstract String getUpdateSuccessMessage();
    protected abstract String getDeleteSuccessMessage();
}
