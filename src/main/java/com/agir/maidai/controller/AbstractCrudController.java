package com.agir.maidai.controller;

import com.agir.maidai.service.CrudService;
import com.agir.maidai.util.FilteredParams;
import com.agir.maidai.util.ModelAttributes;
import com.agir.maidai.util.RedirectAttributesWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

public abstract class AbstractCrudController<T, ID> implements CrudController<T, ID>{

    protected final CrudService<T, ID> service;
    protected final String entityName;
    protected final String baseViewPath;

    public AbstractCrudController(CrudService<T, ID> service,
                                  String entityName,
                                  String baseViewPath) {
        this.service = service;
        this.entityName = entityName;
        this.baseViewPath = baseViewPath;
    }

    @GetMapping
    @Override
    public String index(Model model,
                        HttpServletRequest request) {

        // Obtain separately the parameters from URL
        FilteredParams filteredParams = new FilteredParams();
        Pageable pageable = filteredParams.getPageable(request);
        String sort = filteredParams.getSort();
        Map<String, String> filters = filteredParams.getFilters(request);
        Map<String, String> restOfParameters = filteredParams.getParparameters(request);
        String queryParameters = filteredParams.getQueryParameters(request);

        Page<T> entityPage;

        if(!filters.isEmpty()) {
            entityPage = service.findAll(pageable, filters);
        } else if(!restOfParameters.isEmpty()) {
            entityPage = service.findAll(pageable, restOfParameters);
        }else {
            entityPage = service.findAll(pageable);
        }

        ModelAttributes modelAttributes = new ModelAttributes(model)
                .add("baseViewPath", baseViewPath)
                .add("sort", sort)
                .add("filters", filters)
                .add("restOfParameters", restOfParameters)
                .add("entityList", entityPage)
                .add("queryParameters", queryParameters);
        modelAttributes.apply();

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
