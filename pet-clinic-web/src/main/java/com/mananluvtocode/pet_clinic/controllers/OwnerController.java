package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Owner;
import org.springframework.ui.Model;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/owners")
public class OwnerController {
    // declaring final because the object will never be change for doing the further work.
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"/", "/index", "index.html", ""})
    public String ownerpage(Model themodel) {
        themodel.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping({"/find", "/oups"})
    public String findOwners() {
        return "notImplemented";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView findOwner(@PathVariable("ownerId") Long ownerId, Model themodel) {
        ModelAndView modelAndView= new ModelAndView("owners/ownerDetails");
        Owner owner = ownerService.findById(ownerId);
        modelAndView.addObject("owner", owner);
        return modelAndView;
    }
}