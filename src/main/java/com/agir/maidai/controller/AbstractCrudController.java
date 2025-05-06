package com.agir.maidai.controller;

import com.agir.maidai.exception.FieldAwareError;
import com.agir.maidai.service.CrudService;
import com.agir.maidai.util.ModelAttributes;
import com.agir.maidai.util.RedirectAttributesWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        new ModelAttributes(model)
                .add(entityName + "List", service.findAll())
                .apply();
        return baseViewPath + "/list";
    }

    @Override
    @GetMapping("/{id}")
    public String show(ID id, Model model) {
        new ModelAttributes(model)
                .add(entityName, service.find(id))
                .apply();
        return baseViewPath + "/view";
    }

    @Override
    @GetMapping("/create")
    public String create(Model model, RedirectAttributes redirectAttributes) {

        if(!model.containsAttribute(entityName)) {
            new ModelAttributes(model)
                    .add(entityName, createNewEntity())
                    .apply();
        }

        return baseViewPath + "/form";
    }

    @Override
    @PostMapping
    public String store(T entity,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        /*
        if(bindingResult.hasErrors()) {
            System.out.println("Chegou no BindingResult");
            new RedirectAttributesWrapper(redirectAttributes)
                    .add("errors", bindingResult.getAllErrors())
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/create";
        }

        try {
            service.create(entity);
            redirectAttributes.addFlashAttribute("success", getCreateSuccessMessage());
            return "redirect:/" + baseViewPath;
        } catch (Exception e) {
            System.out.println("Chegou no Catch com o ObjectError");
            List<ObjectError> errors = new ArrayList<>();
            errors.add(new ObjectError("globalError", e.getMessage()));

            new RedirectAttributesWrapper(redirectAttributes)
                    .add("errors", errors)
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/create";
        }
        */

        if(bindingResult.hasErrors()) {
            System.out.println("Chegou no BindingResult"); // REMOVER
            new RedirectAttributesWrapper(redirectAttributes)
                    .add("errors", processErrors(bindingResult))
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/create";
        }
        try {
            service.create(entity);
            redirectAttributes.addFlashAttribute("success", getCreateSuccessMessage());
            return "redirect:/" + baseViewPath;
        } catch (Exception e) {
            System.out.println("Chegou no Catch com o ObjectError"); // REMOVER
            List<ObjectError> errors = new ArrayList<>();
            errors.add(new ObjectError("globalError", e.getMessage()));

            new RedirectAttributesWrapper(redirectAttributes)
                    .add("errors", errors)
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/create";
        }
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(ID id, Model model, RedirectAttributes redirectAttributes) {
        if(!model.containsAttribute(entityName)) {
            new ModelAttributes(model)
                    .add(entityName, service.find(id))
                    .apply();
        }
        return baseViewPath + "/form";
    }

    @Override
    @PutMapping("/{id}")
    public String update(ID id,
                         T entity,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        /*if (bindingResult.hasErrors()) {

            new RedirectAttributesWrapper(redirectAttributes)
                    //.add("errors", bindingResult.getAllErrors())
                    .add("errors", processErrors(bindingResult))
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/"+id+"/edit";
        }*/
        if(bindingResult.hasErrors()) {
            System.out.println("Chegou no BindingResult update"); // REMOVER
            new RedirectAttributesWrapper(redirectAttributes)
                    .add("errors", processErrors(bindingResult))
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/"+id+"/edit";
        }
        try {

            service.update(entity);
            redirectAttributes.addFlashAttribute("success", getUpdateSuccessMessage());
            return "redirect:/" + baseViewPath;
        } catch (Exception e) {
            System.out.println("Chegou no catch update"); // REMOVER
            List<ObjectError> errors = new ArrayList<>();
            errors.add(new ObjectError("globalError", e.getMessage()));

            new RedirectAttributesWrapper(redirectAttributes)
                    .add("errors", errors)
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/"+id+"/edit";
        }
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String delete(ID id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("success", getDeleteSuccessMessage());
        return "redirect:/" + baseViewPath;
    }

    private List<FieldAwareError> processErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new FieldAwareError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());
    }


    // Template methods to be implemented by concrete controllers
    protected abstract T createNewEntity();
    protected abstract String getCreateSuccessMessage();
    protected abstract String getUpdateSuccessMessage();
    protected abstract String getDeleteSuccessMessage();
}
