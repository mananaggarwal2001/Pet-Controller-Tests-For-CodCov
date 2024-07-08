package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Owner;
import org.springframework.ui.Model;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

    // to control the binding of the data into the objects we use the webDataBinder feature for doing this.
    // This means that the we do not want to allow the webforms to address and manipulate the ID that means everything to the database for doing the stuffs.
    // This also means that in every request spring will not allow the manipulation of the I'D when processing the request for doing the stuffs.
    //InitBinder is the Standard Spring MVC controller for injecting things into the controller.
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
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