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

    public DataLoader() {
        ownerService = new OwnerServiceMap();
        vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setId(1L);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Doe");
        owner2.setId(2L);

        ownerService.save(owner2);

        System.out.println("Loaded Owner!!!!!!!!!!!!!!");

        // for making the vets objects for doing the further work.
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.setId(1L);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Bob");
        vet2.setLastName("Jack");
        vet2.setId(2L);

        vetService.save(vet2);
        System.out.println("Loaded Vets");
    }
}
