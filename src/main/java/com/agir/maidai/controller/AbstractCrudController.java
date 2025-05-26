package com.agir.maidai.controller;

import com.agir.maidai.service.CrudService;
import com.agir.maidai.util.ModelAttributes;
import com.agir.maidai.util.RedirectAttributesWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<T> entityPage = service.findAll(pageable);

        new ModelAttributes(model)
                //.add(entityName + "List", service.findAll())
                //.add(entityName + "List", entityPage)
                .add("baseViewPath", baseViewPath)
                .add("entityList", entityPage)
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

        service.validateSave(entity, bindingResult);

        if(bindingResult.hasErrors()) {

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
            System.out.println("Aconteceu um erro: " + e.getMessage());
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

        service.validateSave(entity, bindingResult);

        if (bindingResult.hasErrors()) {

            new RedirectAttributesWrapper(redirectAttributes)
                    .add("errors", bindingResult.getAllErrors())
                    .add(entityName, entity)
                    .apply();
            return "redirect:/" + baseViewPath + "/"+id+"/edit";
        }
        try {

            service.update(entity);
            redirectAttributes.addFlashAttribute("success", getUpdateSuccessMessage());
            return "redirect:/" + baseViewPath;
        } catch (Exception e) {
            System.out.println("Aconteceu um erro: " + e.getMessage());
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

    // Template methods to be implemented by concrete controllers
    protected abstract T createNewEntity();
    protected abstract String getCreateSuccessMessage();
    protected abstract String getUpdateSuccessMessage();
    protected abstract String getDeleteSuccessMessage();
}
