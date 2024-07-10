package com.mananluvtocode.pet_clinic.controllers;
import com.mananluvtocode.pet_clinic.model.Pet;
import com.mananluvtocode.pet_clinic.model.Visit;
import com.mananluvtocode.pet_clinic.services.PetService;
import com.mananluvtocode.pet_clinic.services.VisitService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    private final static String VISIT_FORM = "pets/createOrUpdateVisitForm";

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Model themodel) {
        Pet pet = petService.findById(petId);
        themodel.addAttribute("pet", pet);
        Visit visit = new Visit();
        visit.setPet(pet);
        pet.getVisits().add(visit);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String createVisitForm(@PathVariable("petId") Long petId, Model themodel) {
        return VISIT_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processVisitForm(@Valid Visit visit, BindingResult result, @ModelAttribute("ownerId") Long ownerId) {
        System.out.println(visit.getDescription());
        if (!StringUtils.hasLength(visit.getDescription())) {
            result.rejectValue("description", "null", "Description is required");
        }
        if (result.hasErrors()) {
            return VISIT_FORM;
        } else {
            if (visit.getDate() == null) {
                visit.setDate(LocalDate.now());
            }
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }
}
