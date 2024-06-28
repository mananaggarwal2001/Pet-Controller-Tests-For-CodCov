package com.mananluvtocode.pet_clinic.model;

import java.util.Set;

// It is just like the animal doctor for treating the animals.
public class Vet extends Person {
    private Set<Speciality> specialities;

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }
}