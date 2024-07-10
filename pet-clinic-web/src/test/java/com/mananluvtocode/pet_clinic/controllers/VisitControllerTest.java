package com.mananluvtocode.pet_clinic.controllers;

import com.mananluvtocode.pet_clinic.model.Pet;
import com.mananluvtocode.pet_clinic.model.Visit;
import com.mananluvtocode.pet_clinic.services.PetService;
import com.mananluvtocode.pet_clinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    private PetService petService;
    @Mock
    private VisitService visitService;
    @InjectMocks
    VisitController visitController;
    Visit visit;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        visit = Visit.builder().id(1L).build();
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void createVisitForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());
        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("visit"))
                .andExpect(model().attributeExists("pet"));
        verify(petService).findById(anyLong());
    }

    @Test
    void processVisitForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());
        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "yet another visit")
                .param("date", "2024-05-10")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(visitService).save(any());
    }
}