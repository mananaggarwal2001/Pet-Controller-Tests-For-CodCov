package com.mananluvtocode.pet_clinic.bootstrap;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.model.Pet;
import com.mananluvtocode.pet_clinic.model.PetType;
import com.mananluvtocode.pet_clinic.model.Vet;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import com.mananluvtocode.pet_clinic.services.PetTypeService;
import com.mananluvtocode.pet_clinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

// this implements the data of the class on load for doing the work.
@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petType;

    // hard code implementation which is
//    public DataLoader(){
//        ownerService = new OwnerServiceMap();
//        vetService = new VetServiceMap();
//    }

    // this below constructor the classes are initialized by the Spring itself.
    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petType) {
        this.ownerService = ownerService; // the Spring will initialize this annotation for doing the work.
        this.vetService = vetService;
        this.petType = petType;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petType.save(dog);


        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petType.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setAddress("Noida from India");
        owner1.setCity("Uttar Pradesh");
        owner1.setTelePhone("1234567890");

        ownerService.save(owner1);

        // adding the couple of dogs
        Pet mikepet = new Pet();
        mikepet.setPetType(savedDogPetType);
        mikepet.setOwner(owner1);
        mikepet.setBirthDate(LocalDate.now());
        mikepet.setName("Rosco");
        owner1.getPets().add(mikepet);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Doe");
        owner2.setAddress("Tokyo From Japan");
        owner2.setCity("Tokyo");
        owner2.setTelePhone("987654321");
        Pet fionanceCat = new Pet();
        fionanceCat.setPetType(savedCatPetType);
        fionanceCat.setOwner(owner2);
        fionanceCat.setBirthDate(LocalDate.now());
        fionanceCat.setName("fionance");
        owner2.getPets().add(fionanceCat);


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
