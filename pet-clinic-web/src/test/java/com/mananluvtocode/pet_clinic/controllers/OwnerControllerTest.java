package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import jakarta.security.auth.message.callback.SecretKeyCallback;
import jakarta.servlet.ServletContext;
import org.apache.catalina.connector.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    void setUp() {
        ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).lastName("Smith").build());
        ownerSet.add(Owner.builder().id(2L).lastName("Aggarwal").build());
        // standaloneSetup is the lightweight setup for setting up the environment for the Spring controller to run in.
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

    }

    @Test
    void listOwners() throws Exception {
        when(ownerService.findAll()).thenReturn(ownerSet);
        verifyNoInteractions(ownerService);
        assertEquals(ownerSet.size(), ownerService.findAll().size());
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notImplemented"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void verifyByOwnerId() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }
}