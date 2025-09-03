package com.agir.maidai.controller;

import com.agir.maidai.entity.Project;
import com.agir.maidai.service.ProjectService;
import com.agir.maidai.util.RedirectAttributesWrapper;
import com.agir.maidai.validation.ProjectsPeopleRelationshipsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects/{id}/relationships/people")
public class ProjectsPeopleRelationshipsController {

    private ProjectService projectService;

    public ProjectsPeopleRelationshipsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public String addToProject(@PathVariable Integer id,
                               @RequestParam("coAdvisorId") Integer coAdvisorId,
                               RedirectAttributes redirectAttributes) {

        List<ObjectError> errors = new ArrayList<>();

        try {
            projectService.addCoAdvisorToProject(id, coAdvisorId);
            redirectAttributes.addFlashAttribute("success", getUpdateSuccessMessage());
            return "redirect:/projects/" + id;
        } catch (ProjectsPeopleRelationshipsException e) {
            errors.add(new FieldError("project", "coAdvisorId", e.getMessage()));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/projects/" + id;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{personId}")
    public String removeFromPPG(@PathVariable Integer id,
                                @PathVariable("personId") Integer personId,
                                RedirectAttributes redirectAttributes) {
        try {
            projectService.removeCoAdvisorFromProject(id, personId);
            redirectAttributes.addFlashAttribute("success", getDeleteSuccessMessage());
            return "redirect:/projects/" + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getUpdateSuccessMessage() {
        return "O coorientador foi cadastrado no projeto.";
    }
    protected String getDeleteSuccessMessage() {
        return "O coorientador foi removido do projeto.";
    }
}
