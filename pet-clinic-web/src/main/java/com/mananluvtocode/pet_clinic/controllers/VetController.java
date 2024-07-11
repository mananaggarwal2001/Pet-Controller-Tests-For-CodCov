package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Vet;
import com.mananluvtocode.pet_clinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets/index", "/vets/index.html", "/vets.html"})
    public String listVets(Model themodel) {
        themodel.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }

    // this will parse the response body of the web which is by default is the json parsing or the XML parsing .
    // now the modern method is using the json parsing methodology for parsing the data to the web.
    // Spring Jackson will create the Class object to the JSON format for doing the things right.
    @RequestMapping("/api/vets")
    public @ResponseBody Set<Vet> getVetJson() {
        return vetService.findAll();
    }
}