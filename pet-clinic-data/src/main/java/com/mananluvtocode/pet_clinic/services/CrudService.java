package com.mananluvtocode.pet_clinic.services;

import java.util.Set;

// we are using the java generics here
public interface CrudService<T, ID> {
    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
