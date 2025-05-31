package com.agir.maidai.controller;

import com.agir.maidai.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        try {
            projectService.addCoAdvisorToProject(id, coAdvisorId);
            redirectAttributes.addFlashAttribute("success", getUpdateSuccessMessage());
            return "redirect:/projects/" + id;
        } catch (Exception e) {
            return e.getMessage();
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
