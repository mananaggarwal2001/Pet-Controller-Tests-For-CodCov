package com.mananluvtocode.pet_clinic.services.springdatajpaversion;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.repositories.OwnerRepository;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import com.mananluvtocode.pet_clinic.services.PetService;
import com.mananluvtocode.pet_clinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {
    // Mock in the Spring boot is to simulate the behaviour of the real object

    @Mock
    PetService petService;
    @Mock
    PetTypeService petTypeService;
    @Mock
    OwnerRepository ownerRepository;
    // this will instantiate the service and this service will behave and function like the real service.
    @InjectMocks
    OwnerServiceImpl service;

    Owner returnOwner;


    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName("Smith").build();
    }

    @Test
    void findByLastName() {
        String lastName = "Smith";
        Owner returnOwner = Owner.builder().id(1L).lastName(lastName).build();
        // this should be the ownerRepository as in the original code I have used the owner repository.
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
        Owner smith = service.findByLastName(lastName);
        assertEquals(lastName, smith.getLastName());
        // for testing how many times this ownerRepository or the ownerService is running for getting the data.
        // for doing the verification whether the repository is either calling or not.
        verify(ownerRepository, times(1)).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> originalSet = new HashSet<>();
        originalSet.add(Owner.builder().id(1L).lastName("Smith").build());
        originalSet.add(Owner.builder().id(2L).lastName("SecondSmith").build());
        // when findAll() is called i want to return back the owner set for doing the further stuffs.
        when(ownerRepository.findAll()).thenReturn(originalSet);
        Set<Owner> returnedSet2 = service.findAll();
        // for verifying the size of the returned is that whether the function is working properly or not.
        assertEquals(originalSet.size(), returnedSet2.size());
        // for checking whether the set of the object is null or not null
        assertNotNull(returnedSet2);
    }

    @Test
    void findById() {
    // for testing whether the owner is returned correctly using the id or not.
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner mockReturnedOwner= service.findById(returnOwner.getId());
        assertEquals(returnOwner.getId(), mockReturnedOwner.getId());
        // for checking whether the owner is not null or not.
        assertNotNull(mockReturnedOwner);
    }

    // for checking if the condition is false then the object will be returned is the null object or not.


    @Test
    void findByNotIdFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner mockReturnedOwner= service.findById(returnOwner.getId());
        // this will check whether the result will be null or not.
        assertNull(mockReturnedOwner);
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        Owner returnedOwner = service.save(returnOwner);
        assertEquals(returnOwner.getId(), returnedOwner.getId());
        assertNotNull(returnedOwner);
        // for verifying whether the repository is used or not.
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
    // for delete method
        ownerRepository.delete(returnOwner);
        // then verify that ownerRepository method is called one time
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        // for performing deletion using id.
        ownerRepository.deleteById(anyLong());
        // verify that the owner Repository method is called one time only not more than one time for performing the deletion of the method.
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }
}