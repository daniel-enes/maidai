package com.agir.maidai.controller;

import com.agir.maidai.service.PPGService;
import com.agir.maidai.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/people/{id}/ppgs")
public class PeoplePPGController {

    private final PersonService personService;

    public PeoplePPGController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public String addToPPG(@PathVariable Integer id,
                           @RequestParam("ppgId") Integer ppgId,
                           RedirectAttributes redirectAttributes) {
        try {
            personService.addPersonToPpg(id, ppgId);
            redirectAttributes.addFlashAttribute("success", getUpdateSuccessMessage());
            return "redirect:/people/" + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{ppgId}")
    public String removeFromPPG(@PathVariable Integer id,
                                @PathVariable("ppgId") Integer ppgId,
                                RedirectAttributes redirectAttributes) {
        try {
            personService.removePersonFromPpg(id, ppgId);
            redirectAttributes.addFlashAttribute("success", getDeleteSuccessMessage());
            return "redirect:/people/" + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getUpdateSuccessMessage() {
        return "O orientador foi cadastrado no PPG.";
    }
    protected String getDeleteSuccessMessage() {
        return "O orientador foi removido do PPG.";
    }
}
