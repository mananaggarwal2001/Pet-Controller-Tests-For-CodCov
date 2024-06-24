package com.mananluvtocode.pet_clinic.services;

import com.mananluvtocode.pet_clinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
