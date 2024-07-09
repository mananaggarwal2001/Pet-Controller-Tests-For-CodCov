package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.model.Pet;
import com.mananluvtocode.pet_clinic.model.PetType;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import com.mananluvtocode.pet_clinic.services.PetService;
import com.mananluvtocode.pet_clinic.services.PetTypeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

// this is the unique way to making the controller and learning the new things for the further work.
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
    private static final String PET_FORM = "pets/createOrUpdatePetForm";
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<String> populatePetTypes() {
        Collection<String> petTypeNameCollection = new HashSet<>();
        petTypeService.findAll().forEach(element -> petTypeNameCollection.add(element.getName()));
        return petTypeNameCollection;
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }
    // for intiating the Spring petForm

    @GetMapping("/pets/new")
    public String initCreatePetForm(Owner owner, Model themodel) {
        Pet pet = new Pet();
        pet.setOwner(owner);
        owner.getPets().add(pet);
        themodel.addAttribute("pet", pet);
        return PET_FORM;
    }

    // processing the pet details for doing the stuffs right
    @PostMapping("/pets/new")
    public String processCreatePetForm(Model themodel, Owner owner, @Valid Pet pet, BindingResult result) {
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName()) != null) {
            result.rejectValue("name", "duplicate", "Pet Already Exists!!");
        }
        owner.getPets().add(pet);
        if (result.hasErrors()) {
            themodel.addAttribute("pet", pet);
            return PET_FORM;
        } else {
            pet.setOwner(owner);
            ownerService.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initPetUpdateForm(Model themodel, @PathVariable("petId") Long petId) {
        themodel.addAttribute("pet", petService.findById(petId));
        return PET_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processPetUpdateForm(Model themodel, @Valid Pet pet, Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            pet.setOwner(owner);
            themodel.addAttribute("pet", pet);
            return PET_FORM;
        } else {
            owner.getPets().add(pet);
            pet.setOwner(owner);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }


    @InitBinder
    public void initDataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
}
