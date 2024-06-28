package com.mananluvtocode.pet_clinic.controllers;

import org.springframework.ui.Model;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}