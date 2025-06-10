package com.agir.maidai.controller;

import com.agir.maidai.service.CrudService;
import com.agir.maidai.util.FilteredParams;
import com.agir.maidai.util.ModelAttributes;
import com.agir.maidai.util.RedirectAttributesWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;


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

        FilteredParams filteredParams = new FilteredParams();

        Pageable pageable = filteredParams.getPageable(request);

        String sort = filteredParams.getSort();

        Map<String, String> filters = filteredParams.getFilters(request);

        Map<String, String> restOfParameters = filteredParams.getParparameters(request);

        Page<T> entityPage;

        if(!filters.isEmpty()) {
            entityPage = service.findAll(pageable, filters);
        } else if(!restOfParameters.isEmpty()) {
            System.out.println("Chegou no ELSEIF");
            entityPage = service.findAll(pageable, restOfParameters);
        }else {
            System.out.println("Chegou no ELSE");
            entityPage = service.findAll(pageable);
        }

        ModelAttributes modelAttributes = new ModelAttributes(model)
                .add("baseViewPath", baseViewPath)
                .add("sort", sort)
                .add("entityList", entityPage);
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

    /*
    * Util functions to controller
    */

    private Pageable parseSort(String sort, int page, int size) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        return PageRequest.of(page, size, direction, sortField);
    }

    // Parse filter=status:vigente,name:John into a Map
    private Map<String, String> parseFilterGroups(String filterParam) {
        Map<String, String> filters = new HashMap<>();
        if (filterParam == null || filterParam.isEmpty()) {
            return filters;
        }

        // Split multiple filters: "status:vigente,name:John" → ["status:vigente", "name:John"]
        String[] filterPairs = filterParam.split(",");

        for (String pair : filterPairs) {
            // Split each pair: "status:vigente" → ["status", "vigente"]
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                filters.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return filters;
    }

    // Template methods to be implemented by concrete controllers
    protected abstract T createNewEntity();
    protected abstract String getCreateSuccessMessage();
    protected abstract String getUpdateSuccessMessage();
    protected abstract String getDeleteSuccessMessage();
}
