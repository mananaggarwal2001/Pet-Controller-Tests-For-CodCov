package com.mananluvtocode.pet_clinic.bootstrap;

import com.mananluvtocode.pet_clinic.model.*;
import com.mananluvtocode.pet_clinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

// this implements the data of the class on load for doing the work.
@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petType;
    private final SpecialityService specialityService;
    private final PetService petService;
    private final VisitService visitService;

    // hard code implementation which is
//    public DataLoader(){
//        ownerService = new OwnerServiceMap();
//        vetService = new VetServiceMap();
//    }

    // this below constructor the classes are initialized by the Spring itself.
    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petType, SpecialityService specialityService, PetService petService, VisitService visitService) {
        this.ownerService = ownerService; // the Spring will initialize this annotation for doing the work.
        this.vetService = vetService;
        this.petType = petType;
        this.specialityService = specialityService;
        this.petService = petService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petType.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petType.save(cat);
        // speciality for adding into the owner class
        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiologySpeciality = specialityService.save(radiology);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);


        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setAddress("Noida from India");
        owner1.setCity("Uttar Pradesh");
        owner1.setTelePhone("1234567890");


        // adding the couple of dogs
        Pet mikepet = new Pet();
        mikepet.setPetType(savedDogPetType);
        mikepet.setOwner(owner1);
        mikepet.setBirthDate(LocalDate.now());
        mikepet.setName("Rosco");
        owner1.getPets().add(mikepet);

        ownerService.save(owner1);
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
        fionanceCat.setName("finance");
        owner2.getPets().add(fionanceCat);


        ownerService.save(owner2);

        Owner manan= new Owner();
        manan.setFirstName("Manan");
        manan.setLastName("Aggarwal");
        manan.setAddress("USA Japan");
        manan.setCity("Japan");
        manan.setTelePhone("987654321");
        Pet personalCat= new Pet();
        personalCat.setPetType(savedCatPetType);
        personalCat.setBirthDate(LocalDate.now());
        personalCat.setName("Kaali Billi");
        personalCat.setOwner(manan);
        manan.getPets().add(personalCat);

        ownerService.save(manan);

        System.out.println("Loaded Owner!!!!!!!!!!!!!!");
        // for adding the visit service for doing the greater work.
        Visit catVisit = new Visit();
        catVisit.setPet(fionanceCat);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        visitService.save(catVisit);
        System.out.println("Loading the Visit Data for doing the personal work.");

        // for making the vets objects for doing the further work.
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedDentistry);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Bob");
        vet2.setLastName("Jack");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);


        System.out.println("Loaded Vets");
    }
}
