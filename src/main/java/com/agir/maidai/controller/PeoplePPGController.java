package com.agir.maidai.controller;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.entity.Person;
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
    private final PPGService ppgService;

    public PeoplePPGController(PersonService personService, PPGService ppgService) {
        this.personService = personService;
        this.ppgService = ppgService;
    }

    @PatchMapping
    public String addToPPG(@PathVariable Integer id,
                         @RequestParam("ppgId") Integer ppgId,
                         RedirectAttributes redirectAttributes) {
        try {
            Person person = personService.find(id);
            PPG ppg = ppgService.find(ppgId);

            person.getPpgList().add(ppg);
            ppg.getPersonList().add(person);

            personService.update(person);

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
            Person person = personService.find(id);
            PPG ppg = ppgService.find(ppgId);

            person.getPpgList().remove(ppg);
            ppg.getPersonList().remove(person);

            personService.update(person);

            redirectAttributes.addFlashAttribute("success", getDeleteSuccessMessage());
            return "redirect:/people/" + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getUpdateSuccessMessage() {
        return "A orientador foi cadastrado no PPG.";
    }

    protected String getDeleteSuccessMessage() {
        return "O orientador foi removido do PPG.";
    }
}
