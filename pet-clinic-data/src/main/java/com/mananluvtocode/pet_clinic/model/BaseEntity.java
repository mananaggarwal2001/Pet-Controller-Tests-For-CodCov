package com.mananluvtocode.pet_clinic.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    // box types can be null in fact the primitive values can't be null for doing the things.
    private Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
