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
import static org.mockito.Mockito.*;


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
    void findOwners() {
        verifyNoInteractions(ownerService);
    }
}