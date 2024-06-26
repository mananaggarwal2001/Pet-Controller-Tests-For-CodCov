package com.mananluvtocode.pet_clinic.bootstrap;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.model.Vet;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import com.mananluvtocode.pet_clinic.services.VetService;
import com.mananluvtocode.pet_clinic.services.map.OwnerServiceMap;
import com.mananluvtocode.pet_clinic.services.map.VetServiceMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;

    // hard code implementation which is
//    public DataLoader(){
//        ownerService = new OwnerServiceMap();
//        vetService = new VetServiceMap();
//    }

    // this below constructor the classes are initialized by the Spring itself.
    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService; // the Spring will initialize this annotation for doing the work.
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Doe");

        ownerService.save(owner2);

        System.out.println("Loaded Owner!!!!!!!!!!!!!!");

        // for making the vets objects for doing the further work.
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Bob");
        vet2.setLastName("Jack");

        vetService.save(vet2);
        System.out.println("Loaded Vets");
    }
}