package com.mananluvtocode.pet_clinic.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
// this is telling that this is being inherited by the other class and this class is not the part of the database entity.
public class BaseEntity implements Serializable {
    // box types can be null in fact the primitive values can't be null for doing the things.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
