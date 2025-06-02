package com.agir.maidai.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface CrudController<T, ID> {

    String index(Model model,
                 HttpServletRequest request);

    String show(@PathVariable ID id,
                Model model);

    String create(Model model, RedirectAttributes redirectAttributes);

    String store(@Validated @ModelAttribute T entity,
                 BindingResult bindingResult,
                 Model model,
                 RedirectAttributes redirectAttributes);

    String edit(@PathVariable ID id,
                Model model,
                RedirectAttributes redirectAttributes);

    String update(@PathVariable ID id,
                  @Validated @ModelAttribute T entity,
                  BindingResult bindingResult,
                  Model model,
                  RedirectAttributes redirectAttributes);

    String delete(@PathVariable ID id,
                  RedirectAttributes redirectAttributes);

}
