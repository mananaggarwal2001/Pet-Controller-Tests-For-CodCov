package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.model.PetType;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import com.mananluvtocode.pet_clinic.services.PetService;
import com.mananluvtocode.pet_clinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

// this is the unique way to making the controller and learning the new things for the further work.
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("owner") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder
    public void initdatabinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
}
