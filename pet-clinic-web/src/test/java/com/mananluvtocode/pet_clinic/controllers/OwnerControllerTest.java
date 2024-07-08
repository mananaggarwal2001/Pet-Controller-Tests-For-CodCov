package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// in this we will use the mockito extension for doing the work.
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Mock
    OwnerService ownerService;
    Set<Owner> ownerSet;
    @InjectMocks
    OwnerController ownerController;
    // this will be used for testing the MVC Controller based class for doing the stuffs.
    MockMvc mockMvc;
    RequestBuilder requestBuilder;

    List<Owner> ownerList;

    @BeforeEach
    void setUp() {
        ownerSet = new HashSet<>();
        ownerList = new ArrayList<>();
        ownerSet.add(Owner.builder().id(1L).lastName("Smith").build());
        ownerSet.add(Owner.builder().id(2L).lastName("Aggarwal").build());
        ownerList.add(Owner.builder().id(3L).lastName("Jones").build());
        ownerList.add(Owner.builder().id(4L).lastName("Bob").build());
        // standaloneSetup is the lightweight setup for setting up the environment for the Spring controller to run in.
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("ownersName/findOwner"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(ownerList);
        mockMvc.perform(post("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("ownersName/ownerList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).lastName("smith").build()));
        mockMvc.perform(post("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void verifyByOwnerId() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("ownersName/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

    @Test
    void intCreationOwnerForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("ownersName/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationOwnerForm() throws Exception {
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());
        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(ownerService).save(ArgumentMatchers.any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("ownersName/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());
        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService, times(1)).save(any());
    }
}