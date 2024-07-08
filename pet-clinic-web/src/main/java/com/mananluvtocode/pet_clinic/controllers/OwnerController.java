package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Owner;
import org.springframework.ui.Model;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

//    @RequestMapping({"/", "/index", "index.html", ""})
//    public String ownerpage(Model themodel) {
//        themodel.addAttribute("owners", ownerService.findAll());
//        return "ownersName/index";
//    }

    @RequestMapping({"/find", "/oups"})
    public String findOwners(Model themodel) {
        themodel.addAttribute("owner", Owner.builder().build());
        return "ownersName/findOwner";
    }

    @PostMapping
    public String processfindOwnerFormDetails(@ModelAttribute("owner") Owner owner, BindingResult result, Model themodel) {
        System.out.println(owner.getLastName());
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }
        List<Owner> resultOwnerByLastName = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        System.out.println(resultOwnerByLastName.size());
        if (resultOwnerByLastName.isEmpty()) {
            result.rejectValue("lastName", "notfound", "Not Found");
            themodel.addAttribute("owner", Owner.builder().build());
            return "ownersName/findOwner";
        } else if (resultOwnerByLastName.size() == 1) {
            Owner originalOwner = resultOwnerByLastName.get(0);
            return "redirect:/owners/" + originalOwner.getId();
        } else {
            themodel.addAttribute("selections", resultOwnerByLastName);
            return "ownersName/ownerList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView findOwner(@PathVariable("ownerId") Long ownerId, Model themodel) {
        ModelAndView modelAndView = new ModelAndView("ownersName/ownerDetails");
        Owner owner = ownerService.findById(ownerId);
        modelAndView.addObject("owner", owner);
        return modelAndView;
    }
}