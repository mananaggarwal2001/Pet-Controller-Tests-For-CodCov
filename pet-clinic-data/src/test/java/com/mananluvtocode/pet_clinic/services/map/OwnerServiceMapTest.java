package com.mananluvtocode.pet_clinic.services.map;

import com.mananluvtocode.pet_clinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {
    OwnerServiceMap ownerServiceMap;
    final Long ownerId = 1L;
    final String lastName= "Smith";
    @BeforeEach
    void setUp() {
        // now the ownerservice class is ready for checking the required things.
        ownerServiceMap = new OwnerServiceMap(new PetTypeMapService(), new PetServiceMap());
        // for setting the things we use this function within the test class for doing the particular stuffs.
        // this is also called as the dependency injection for building the things right.
        // basically what's it's does is it build the owner map service and puts one object into that map service.
        ownerServiceMap.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }
    // doing the unit testing for doing the things right.
    @Test
    void findAll() {
        Set<Owner> owenrSet = ownerServiceMap.findAll();
        assertEquals(ownerId, owenrSet.size());
    }

    @Test
    void delete() {
        Owner owner= ownerServiceMap.findById(ownerId);
        // for equality test we use the assertEquals()
        ownerServiceMap.delete(owner);
        assertEquals(0, ownerServiceMap.findAll().size());

    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void findById() {
        Owner owner= ownerServiceMap.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id= 2L;
        Owner owner2= Owner.builder().id(id).build();
        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner= ownerServiceMap.save(Owner.builder().build());
        // for checking it for the not null whether the id is null or not we will use assertNotNull
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findByLastName() {
        Owner owner= ownerServiceMap.findByLastName(lastName);
        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
    }
    @Test
    void findByLastNameNotFound() {
        Owner owner= ownerServiceMap.findByLastName("foo");
        assertNull(owner);
    }
}